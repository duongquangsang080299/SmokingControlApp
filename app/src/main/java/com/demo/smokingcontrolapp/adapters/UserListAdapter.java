package com.demo.smokingcontrolapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.models.User;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {

    private List<User> userList;
    private IListener mListener;

    public UserListAdapter(List<User> userList, IListener iListener) {
        this.userList = userList;
        this.mListener = iListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.txvName.setText(userList.get(position).getUserName());
        holder.clLayout.setOnClickListener(v -> {
            mListener.onClickListener(userList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder {
        TextView txvName;
        ConstraintLayout clLayout;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            txvName = itemView.findViewById(R.id.txv_name);
            clLayout = itemView.findViewById(R.id.cl_layout);
        }
    }

    // click item in list User
    public interface IListener{
        void onClickListener(User user);
    }
}
