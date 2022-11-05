package com.example.gnosis.utils;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.gnosis.model.TaskModel;

public class AttachFileItemDiffUtils extends DiffUtil.ItemCallback<Uri> {

    @Override
    public boolean areItemsTheSame(@NonNull Uri oldItem, @NonNull Uri newItem) {
        return oldItem.toString().equals(newItem.toString());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Uri oldItem, @NonNull Uri newItem) {
        return false;
    }
}
