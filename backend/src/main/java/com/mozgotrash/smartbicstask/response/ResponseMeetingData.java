package com.mozgotrash.smartbicstask.response;

import com.mozgotrash.smartbicstask.entity.Meeting;
import lombok.Data;

import java.util.List;

@Data
public class ResponseMeetingData {
    private List<Meeting> meetings;
}
