package com.example.gnosis.model;

import java.util.List;

public class TimetableListActivityModel {
    private String day;
    private List<TimetableActivityModel> activities;

    public TimetableListActivityModel(String day, List<TimetableActivityModel> activities) {
        this.activities = activities;
        this.day = day;
    }

    public TimetableListActivityModel() {

    }

    public List<TimetableActivityModel> getActivities() {
        return activities;
    }

    public void setActivities(List<TimetableActivityModel> activities) {
        this.activities = activities;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
