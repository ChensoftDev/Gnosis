package com.example.gnosis.reclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.R;
import com.example.gnosis.model.TimetableListActivityModel;
import com.example.gnosis.utils.TimetableListItemDiffUtils;

public class TimetableListAdapter extends ListAdapter<TimetableListActivityModel, TimetableListAdapter.ViewHolder> {
    private final TimetableActivityListAdapter.Listener listener;
    public TimetableListAdapter(TimetableActivityListAdapter.Listener listener) {
        super(new TimetableListItemDiffUtils());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimetableListActivityModel item = getItem(position);
        holder.tvDateName.setText(item.getDay());
        int background = (position % 2 == 0) ? R.drawable.bg_timetable_row : R.drawable.bg_timetable_row_2;
        int backgroundColor = (position % 2 == 0) ? R.color.timetable_row : R.color.timetable_row_2;
        holder.tvDateName.setBackgroundResource(background);
        TimetableActivityListAdapter adapter = new TimetableActivityListAdapter(backgroundColor, listener);
        holder.rvActivity.setAdapter(adapter);
        adapter.submitList(item.getActivities());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDateName;
        public RecyclerView rvActivity;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateName = itemView.findViewById(R.id.tv_date_name);
            rvActivity = itemView.findViewById(R.id.rv_timetable_activity);
        }
    }
}
