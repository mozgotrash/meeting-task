package com.mozgotrash.smartbicstask.entity;

import java.util.List;

public class RequestMeetingData {
    private String officeWorkTime;
    private List<MeetingDTO> meetings;

    public String getOfficeWorkTime() {
        return officeWorkTime;
    }

    public void setOfficeWorkTime(String officeWorkTime) {
        this.officeWorkTime = officeWorkTime;
    }

    public List<MeetingDTO> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<MeetingDTO> meetings) {
        this.meetings = meetings;
    }
}
