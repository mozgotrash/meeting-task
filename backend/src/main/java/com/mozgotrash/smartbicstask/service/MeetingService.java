package com.mozgotrash.smartbicstask.service;

import com.mozgotrash.smartbicstask.entity.Meeting;
import com.mozgotrash.smartbicstask.entity.MeetingDTO;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService {

    void saveNeetings(List<MeetingDTO> meetings);

    List<Meeting> findAll();

    List<Meeting> findByDate(LocalDate date);
}
