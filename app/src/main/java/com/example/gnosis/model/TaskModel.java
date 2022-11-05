package com.example.gnosis.model;


import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskModel implements Parcelable {

    @Exclude
    public static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    private String uid;
    private String name;
    private String date;
    private String category;
    private String description;
    private String ownerId;
    private List<String> attachFiles;

    public TaskModel(String uid, String name, String date, String type) {
        this.uid = uid;
        this.name = name;
        this.date = date;
        this.category = type;
    }

    public TaskModel() {

    }


    protected TaskModel(Parcel in) {
        uid = in.readString();
        ownerId = in.readString();
        name = in.readString();
        date = in.readString();
        category = in.readString();
        description = in.readString();
        attachFiles = in.createStringArrayList();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };

    @PropertyName("uid")
    public String getUid() {
        return uid;
    }

    @PropertyName("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    @PropertyName("date")
    public void setDate(String date) {
        this.date = date;
    }

    @PropertyName("category")
    public String getCategory() {
        return category;
    }

    @PropertyName("category")
    public void setCategory(String type) {
        this.category = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(ownerId);
        parcel.writeString(name);
        parcel.writeString(date);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeStringList(attachFiles);
    }

    @PropertyName("attach_files")
    public List<String> getAttachFiles() {
        return attachFiles;
    }

    @PropertyName("attach_files")
    public void setAttachFiles(List<String> attachFiles) {
        this.attachFiles = attachFiles;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("owner_id")
    public String getOwnerId() {
        return ownerId;
    }

    @PropertyName("owner_id")
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Exclude
    public List<Uri> getAttachFileUri() {
        List<Uri> items = new ArrayList<>();
        for (String file : attachFiles
        ) {
            items.add(Uri.parse(file));
        }

        return items;
    }
}
