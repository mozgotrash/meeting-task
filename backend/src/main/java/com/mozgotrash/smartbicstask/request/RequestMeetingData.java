package com.mozgotrash.smartbicstask.request;

import com.mozgotrash.smartbicstask.entity.MeetingDTO;
import lombok.Data;

import java.util.List;

@Data
public class RequestMeetingData {
    private List<MeetingDTO> meetings;

}
