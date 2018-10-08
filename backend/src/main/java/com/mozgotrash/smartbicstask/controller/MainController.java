package com.mozgotrash.smartbicstask.controller;

import com.mozgotrash.smartbicstask.entity.Meeting;
import com.mozgotrash.smartbicstask.request.RequestMeetingData;
import com.mozgotrash.smartbicstask.response.ResponseMeetingData;
import com.mozgotrash.smartbicstask.service.MeetingServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MainController {

    @Autowired
    private MeetingServiceImpl meetingService;

    @RequestMapping(value = "/addMeetings")
    public @ResponseBody
    String hi(@RequestBody RequestMeetingData requestMeetingData) {
        meetingService.getMeetingsFromDTO(requestMeetingData.getMeetings());

        List<Meeting> meetings = meetingService.findAll();
        ResponseMeetingData response = new ResponseMeetingData();
        response.setMeetings(meetings);
        String jsonResponse = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonResponse = mapper.writeValueAsString(response);
        } catch (IOException ex) {

        }
        return jsonResponse;
    }

    @RequestMapping(value = "/getByDate")
    public @ResponseBody
    String getByDate(@RequestParam String date) {
        List<Meeting> meetings = meetingService.findByDate(LocalDate.parse(date));

        ResponseMeetingData response = new ResponseMeetingData();
        response.setMeetings(meetings);
        String jsonResponse = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonResponse = mapper.writeValueAsString(response);
        } catch (IOException ex) {

        }
        return jsonResponse;
    }



}
