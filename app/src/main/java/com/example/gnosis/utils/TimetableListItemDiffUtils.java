package com.example.gnosis.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.gnosis.model.TimetableListActivityModel;

public class TimetableListItemDiffUtils extends DiffUtil.ItemCallback<TimetableListActivityModel> {

    @Override
    public boolean areItemsTheSame(@NonNull TimetableListActivityModel oldItem, @NonNull TimetableListActivityModel newItem) {
        return oldItem.getDay().equals(newItem.getDay());
    }

    @Override
    public boolean areContentsTheSame(@NonNull TimetableListActivityModel oldItem, @NonNull TimetableListActivityModel newItem) {
        return oldItem.getActivities().equals(newItem.getActivities());
    }

}
