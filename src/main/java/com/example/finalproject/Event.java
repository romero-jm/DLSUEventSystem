package com.example.finalproject;

import java.time.LocalDate;

public class Event {
    String userReq;
    String userApp;
    int status;
    String timeStart;
    String timeEnd;
    LocalDate eventDate; // Add event date

    public Event(String userReq, String userApp, int status, String timeStart, String timeEnd, LocalDate eventDate) {
        this.userReq = userReq;
        this.userApp = userApp;
        this.status = status;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.eventDate = eventDate; // Initialize date
    }

    public String getUserReq() {
        return userReq;
    }

    public void setUserReq(String userReq) {
        this.userReq = userReq;
    }

    public String getUserApp() {
        return userApp;
    }

    public void setUserApp(String userApp) {
        this.userApp = userApp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "userReq='" + userReq + '\'' +
                ", userApp='" + userApp + '\'' +
                ", status=" + status +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
