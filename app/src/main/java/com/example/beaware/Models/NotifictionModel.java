package com.example.beaware.Models;

public class NotifictionModel {
    public NotifictionModel(int day, int month, int year, boolean isAllowed, int hour, int minute) {
        Day = day;
        Month = month;
        Year = year;
        this.isAllowed = isAllowed;
        this.hour = hour;
        this.minute = minute;
    }

    public NotifictionModel() {
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    private int Day;
    private int Month;
    private int Year;
    private boolean isAllowed;
    private int hour;
    private int minute;
}
