package com.demo.smokingcontrolapp.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.demo.smokingcontrolapp.models.Chat;

import com.demo.smokingcontrolapp.models.Target;
import com.demo.smokingcontrolapp.models.User;
import com.demo.smokingcontrolapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// get value in firebase database
public class Database {
    private final FirebaseAuth mAuth;

    public Database() {
        mAuth = FirebaseAuth.getInstance();
    }

    //search Email by text

    public void searchEmailByText(String value, IFirebaseGetAllUser result) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Users");
        reference.orderByChild("email")
                .startAt(value)
                .endAt(value + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> emails = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            emails.add(user.getEmail());
                        }
                        result.onResult(emails);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // add new target
    public void addNewTarget(String uid, String nameTarget, Long startDate,
                             Long endDate, int countOfCigarette, IFirebaseAddNew firebaseCallback) {
        Target target = new Target(nameTarget, startDate, endDate, countOfCigarette, 0);
        FirebaseDatabase.getInstance().getReference("Targets")
                .child(uid).push().setValue(target).addOnCompleteListener(task -> firebaseCallback.onResult(task.isSuccessful()));
    }

    // get history in profile screen
    public void getAllTarget(String uid, IFirebaseGetAllTarget iFirebaseGetAllTarget) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Targets").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Target> targetList = new ArrayList<>();
                int countOfComplete = 0;
                int countOfNotComplete = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Target target = dataSnapshot.getValue(Target.class);
                    if (target.getCountOfCigarette() >= target.getCountOfCigaretteSmoked()) {
                        countOfComplete++;
                    } else countOfNotComplete++;
                    targetList.add(target);
                }
                iFirebaseGetAllTarget.onResult(targetList, countOfComplete, countOfNotComplete);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // get all achievement
    public void getAllAchievement(String uid, IFirebaseGetAllAchievement iFirebaseGetAllAchievement) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Targets").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Target> targetList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Target target = dataSnapshot.getValue(Target.class);
                    if (target.getCountOfCigarette() >= target.getCountOfCigaretteSmoked()) {
                        targetList.add(target);
                    }
                }
                iFirebaseGetAllAchievement.onResult(targetList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // sendMessage
    public void sendMessage(Chat chat, IFirebaseAddNew iFirebaseAddNew) {
        FirebaseDatabase.getInstance().getReference("Chats")
                .push().setValue(chat).addOnCompleteListener(task -> {
            iFirebaseAddNew.onResult(task.isSuccessful());
        });
    }

    //real message
    public void readMessage(String uid, IFirebaseGetMessage iFirebaseGetMessage) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Chat> chatList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getSender().equals(uid) || chat.getReceiver().equals(uid)) {
                        chatList.add(chat);
                    }
                }
                iFirebaseGetMessage.onResult(chatList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // read message With AdminUid
    public void readMessageOfAdmin(String sender, String receiver, IFirebaseGetMessage iFirebaseGetMessage) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Chat> chatList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getSender().equals(receiver) && chat.getReceiver().equals(sender)) {
                        chatList.add(chat);
                    } else if (chat.getSender().equals(sender) && chat.getReceiver().equals(receiver)) {
                        chatList.add(chat);
                    }
                }
                iFirebaseGetMessage.onResult(chatList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Create new User on Firebase
    public void createNewUser(String t_userName, String t_email, String t_phoneNumber) {
        User user = new User(mAuth.getCurrentUser().getUid(), t_userName, t_email, Integer.parseInt(t_phoneNumber));
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user);
    }

    // check target of user
    public void checkTargetOfUser(String uid, IFirebaseCheckTargetProgress iFirebaseCheckTargetProgress) {
        checkIsHaveTarget(uid, count -> {
            if (count == 1) {
                long currentTime = System.currentTimeMillis();
                Date currentDate = new Date(currentTime);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference reference = db.getReference("Targets").child(uid);
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getKey().equals(uid)) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Target target = dataSnapshot.getValue(Target.class);
                                // if return true : have no target progress
                                assert target != null;
                                if (!Utils.compareBetweenTwoDate(new Date(target.getEndDate()), currentDate)) {
                                    iFirebaseCheckTargetProgress.onResult(target, dataSnapshot.getKey());
                                } else if (Utils.compareBetweenTwoDate(new Date(target.getEndDate()), currentDate)) {
                                    iFirebaseCheckTargetProgress.onResult(null, null);
                                } else iFirebaseCheckTargetProgress.onResult(null, null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } else iFirebaseCheckTargetProgress.onResult(null, null);
        });

    }

    // check tart with endDate
    private void checkIsHaveTarget(String uid, ICheckKey iCheckKey) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Targets");
        final int[] temp = {0};
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("THIEN", "onDataChange: " + snapshot.getValue());
                if (!snapshot.child(uid).exists()) {
                    iCheckKey.onResult(0);
                } else iCheckKey.onResult(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface ICheckKey {
        void onResult(int count);
    }

    //    Send mail reset password
    public void sendMailRestPass(String email, ISendMailRestPass restPass) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> restPass.onResult(task.isSuccessful()));
    }

    // get All user sending message
    public void getAllUserSendMess(String uid, IFirebaseGetAllUser iFirebaseGetAllUser) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> ids = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    assert chat != null;
                    if (chat.getReceiver().equals(uid)) {
                        ids.add(chat.getSender());
                    } else if (chat.getSender().equals(uid)) {
                        ids.add(chat.getReceiver());
                    }
                }
                iFirebaseGetAllUser.onResult(ids);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // get user from uid
    public void getUsers(List<String> listUIDS, IFirebaseGetUserFromUid iFirebaseGetUserFromUid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Users");
        List<User> users = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    for (String id : listUIDS) {
                        if (user.getUID().equals(id)) {
                            users.add(user);
                            break;
                        }
                    }
                }
                iFirebaseGetUserFromUid.onResult(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUser(String uid, IFirebaseGetUser iFirebaseGetUser) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user.getUID().equals(uid)) {
                        iFirebaseGetUser.onResult(user);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //update target
    public void updateTarget(String uid, String postID, Target target, int count, ISendMailRestPass iSendMailRestPass) {
        FirebaseDatabase.getInstance().getReference("Targets")
                .child(uid).child(postID).child("countOfCigaretteSmoked").setValue(target.getCountOfCigaretteSmoked() + count).addOnCompleteListener(task -> {
            iSendMailRestPass.onResult(task.isSuccessful());
        });
    }

    //get uid from email
    public void getUidFromEmail(String email, IFirebaseGetUid result) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Users");
        reference.orderByChild("email")
                .startAt(email)
                .endAt(email + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String uid = "";
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            uid = user.getUID();
                        }
                        result.onResult(uid);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public interface IFirebaseGetAllUser {
        void onResult(List<String> uid);
    }

    public interface IFirebaseGetUserFromUid {
        void onResult(List<User> user);
    }

    public interface ISendMailRestPass {
        void onResult(Boolean bool);
    }


    public interface IFirebaseGetMessage {
        void onResult(List<Chat> chats);
    }

    public interface IFireBaseCreateUser {
        void onResult(Boolean bool);
    }

    public interface IFirebaseGetAllAchievement {
        void onResult(List<Target> targetList);
    }

    public interface IFirebaseCheckTargetProgress {
        void onResult(Target target, String postId);
    }

    public interface IFirebaseAddNew {
        void onResult(Boolean bool);
    }

    public interface IFirebaseGetAllTarget {
        void onResult(List<Target> targetList, int complete, int notCompleted);
    }

    public interface IFirebaseGetUser {
        void onResult(User user);
    }

    public interface IFirebaseGetUid {
        void onResult(String uid);
    }

}

