package com.mozgotrash.smartbicstask.controller;

import com.mozgotrash.smartbicstask.entity.RequestMeetingData;
import com.mozgotrash.smartbicstask.service.MeetingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MainController {

    @Autowired
    private MeetingServiceImpl meetingService;

    @RequestMapping(value = "/addMeetings")
    public @ResponseBody
    void hi(@RequestBody RequestMeetingData requestMeetingData) {
        meetingService.getMeetingsFromDTO(requestMeetingData.getMeetings());
    }
}
