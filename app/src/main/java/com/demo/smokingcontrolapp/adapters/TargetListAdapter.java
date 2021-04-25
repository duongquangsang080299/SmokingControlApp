package com.demo.smokingcontrolapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.models.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TargetListAdapter extends RecyclerView.Adapter<TargetListAdapter.TargetViewHolder> {

    private List<Target> targetList;

    public TargetListAdapter(List<Target> targetList) {
        this.targetList = targetList;
    }

    @NonNull
    @Override
    public TargetListAdapter.TargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_target, parent, false);
        return new TargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TargetListAdapter.TargetViewHolder holder, int position) {
//        holder.bindData(targetList.get(position));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (targetList.get(position).getCountOfCigarette() < targetList.get(position).getCountOfCigaretteSmoked()){
            holder.txvCountOfCigaretteSmoked.setTextColor(Color.rgb(200, 0, 0));
        }else
            holder.txvCountOfCigaretteSmoked.setTextColor(Color.rgb(0, 200, 0));

        holder.txvTarget.setText(targetList.get(position).getTargetName());
        holder.txvStartDate.setText(dateFormat.format(targetList.get(position).getStartDate()));
        holder.txvEndDate.setText(dateFormat.format(targetList.get(position).getEndDate()));
        holder.txvCountOfCigarette.setText(targetList.get(position).getCountOfCigarette() + "");
        holder.txvCountOfCigaretteSmoked.setText(targetList.get(position).getCountOfCigaretteSmoked() + "");
    }

    @Override
    public int getItemCount() {
        return targetList.size();
    }

    public static class TargetViewHolder extends RecyclerView.ViewHolder {
        TextView txvTarget, txvStartDate, txvEndDate, txvCountOfCigarette, txvCountOfCigaretteSmoked;

        public TargetViewHolder(@NonNull View itemView) {
            super(itemView);
            txvTarget = itemView.findViewById(R.id.txv_target);
            txvCountOfCigarette = itemView.findViewById(R.id.txv_count_of_cigarette);
            txvCountOfCigaretteSmoked = itemView.findViewById(R.id.txv_count_of_cigarette_smoked);
            txvStartDate = itemView.findViewById(R.id.txv_start_date);
            txvEndDate = itemView.findViewById(R.id.txv_end_date);
        }

//        public void bindData(Target target) {
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//            txvTarget.setText(target.getNameTarget());
//            txvStartDate.setText(dateFormat.format(target.getCreateTime()));
//            txvEndDate.setText(dateFormat.format(target.getEndTime()));
//            txvCountOfCigarette.setText(target.getCountOfCigarette());
//            txvCountOfCigaretteSmoked.setText(target.getCountOfCigaretteSmoked());
//        }
    }
}
