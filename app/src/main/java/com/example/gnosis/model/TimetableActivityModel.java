package com.example.gnosis.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.util.HashMap;
import java.util.UUID;

public class TimetableActivityModel implements Parcelable {

    private String startTime;
    private String endTime;
    private String className;
    private String day;
    private String classroom;
    private String uid;

    public TimetableActivityModel(String uid, String className,
                                  String startTime,
                                  String endTime,
                                  String day,
                                  String classRoom
    ) {
        this.className = className;
        this.uid = uid;
        this.startTime = startTime;
        this.day = day;
        this.endTime = endTime;
        this.classroom = classRoom;
    }

    public TimetableActivityModel() {
        this.uid = UUID.randomUUID().toString();
    }

    protected TimetableActivityModel(Parcel in) {
        startTime = in.readString();
        endTime = in.readString();
        className = in.readString();
        day = in.readString();
        classroom = in.readString();
        uid = in.readString();
    }

    public static final Creator<TimetableActivityModel> CREATOR = new Creator<TimetableActivityModel>() {
        @Override
        public TimetableActivityModel createFromParcel(Parcel in) {
            return new TimetableActivityModel(in);
        }

        @Override
        public TimetableActivityModel[] newArray(int size) {
            return new TimetableActivityModel[size];
        }
    };

    @PropertyName("start_time")
    public String getStartTime() {
        return startTime;
    }

    @PropertyName("start_time")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @PropertyName("end_time")
    public String getEndTime() {
        return endTime;
    }

    @PropertyName("end_time")
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @PropertyName("class_name")
    public String getClassName() {
        return className;
    }

    @PropertyName("class_name")
    public void setClassName(String className) {
        this.className = className;
    }

    @PropertyName("day")
    public String getDay() {
        return day;
    }

    @PropertyName("day")
    public void setDay(String day) {
        this.day = day;
    }

    @PropertyName("classroom")
    public String getClassroom() {
        return classroom;
    }

    @PropertyName("classroom")
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    @PropertyName("uid")
    public String getUid() {
        return uid;
    }

    @PropertyName("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeString(className);
        parcel.writeString(day);
        parcel.writeString(classroom);
        parcel.writeString(uid);
    }

    // convert model to hashMap for update
    @Exclude
    public HashMap<String, Object> toHashMapUpdate() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("class_name", className);
        map.put("class_room", className);
        map.put("start_date", className);
        map.put("end_date", className);
        return map;
    }
}
