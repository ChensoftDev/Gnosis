package com.example.gnosis.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.gnosis.model.TimetableActivityModel;

import java.util.Objects;

public class TimetableActivityListItemDiffUtils extends DiffUtil.ItemCallback<TimetableActivityModel> {

    @Override
    public boolean areItemsTheSame(@NonNull TimetableActivityModel oldItem, @NonNull TimetableActivityModel newItem) {
        return oldItem.getUid().equals(newItem.getUid());
    }

    @Override
    public boolean areContentsTheSame(@NonNull TimetableActivityModel oldItem, @NonNull TimetableActivityModel newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
