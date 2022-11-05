package com.example.gnosis.reclerview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.R;
import com.example.gnosis.model.TimetableActivityModel;
import com.example.gnosis.utils.TimetableActivityListItemDiffUtils;
import com.google.android.material.card.MaterialCardView;

public class TimetableActivityListAdapter extends ListAdapter<TimetableActivityModel, TimetableActivityListAdapter.ViewHolder> {
    private final int background;
    private final Listener listener;
    public TimetableActivityListAdapter(@ColorRes int background, Listener listener) {
        super(new TimetableActivityListItemDiffUtils());
        this.background = background;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_activity_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimetableActivityModel item = getItem(position);
        Context context = holder.container.getContext();
        holder.tvActivityName.setText(item.getClassName());
        holder.container.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(background)));
        holder.tvClassRoom.setText(item.getClassroom());
        holder.tvStartTime.setText(item.getStartTime());
        holder.tvEndTime.setText(item.getEndTime());
        holder.container.setOnClickListener(v -> {
            listener.onClick(item);
        });
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvActivityName;
        public TextView tvStartTime;
        public TextView tvEndTime;
        public TextView tvClassRoom;
        public MaterialCardView container;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityName = itemView.findViewById(R.id.tv_activity_name);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvEndTime = itemView.findViewById(R.id.tv_end_time);
            tvClassRoom = itemView.findViewById(R.id.tv_classroom);
            container = itemView.findViewById(R.id.container);
        }
    }

    public interface Listener {
        void onClick(TimetableActivityModel item);
    }
}
