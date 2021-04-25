package com.demo.smokingcontrolapp.activities.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.activities.chat.ChatActivity;
import com.demo.smokingcontrolapp.adapters.SearchAdapter;
import com.demo.smokingcontrolapp.adapters.TargetListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.IOnClickSearchItem {
    private RecyclerView rvSearchResult;
    private EditText edtSearchContent;
    private SearchViewModel viewModel = new SearchViewModel(getApplication());
    private SearchAdapter adapter;
    private List<String> emails = new ArrayList<>();
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();

        event();
    }

    private void event() {

        // listen edit text changed
        edtSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")){
                    viewModel.searchWithString(s.toString(), emailList -> {
                        emails.clear();
                        emails = emailList;
                        adapter = new SearchAdapter(emails, SearchActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                        adapter.notifyDataSetChanged();
                        rvSearchResult.setAdapter(adapter);
                        rvSearchResult.setLayoutManager(linearLayoutManager);
                    });
                }
            }
        });

        // back to SearchListActivity
        imgBack.setOnClickListener(v -> finish());
    }

    private void init() {
        imgBack = findViewById(R.id.imgBack);
        rvSearchResult = findViewById(R.id.rv_search_result);
        edtSearchContent = findViewById(R.id.edt_search_content);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClickItem(String email) {
        viewModel.getUidFromEmail(email, uid -> {
            Intent i = new Intent(SearchActivity.this, ChatActivity.class);
            i.putExtra(ChatActivity.USER_ID, uid);
            startActivity(i);
            finish();
        });
    }
}
