package com.example.gnosis.utils;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;

import com.example.gnosis.model.TaskModel;

public class TaskItemDiffUtils extends DiffUtil.ItemCallback<TaskModel> {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean areItemsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
        return oldItem.getUid().equals(newItem.getUid());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean areContentsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
        return oldItem.getDate().equals(newItem.getDate())
                && oldItem.getName().equals(newItem.getName())
                && oldItem.getCategory().equals(newItem.getCategory());
    }
}
