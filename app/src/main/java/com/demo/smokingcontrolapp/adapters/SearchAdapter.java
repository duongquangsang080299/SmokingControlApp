package com.demo.smokingcontrolapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.smokingcontrolapp.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final List<String> emails;
    private IOnClickSearchItem onClickListener;

    public SearchAdapter(List<String> emails, IOnClickSearchItem onClickItem){
        this.emails = emails;
        this.onClickListener = onClickItem;
    }
    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        holder.txvResult.setText(this.emails.get(position));
        holder.txvResult.setOnClickListener(v -> {
            onClickListener.onClickItem(this.emails.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView txvResult;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            txvResult = itemView.findViewById(R.id.txv_search_result);
        }
    }


    public interface IOnClickSearchItem{
        void onClickItem(String email);
    }
}
