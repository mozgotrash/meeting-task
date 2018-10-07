package com.mozgotrash.smartbicstask.service;

import com.mozgotrash.smartbicstask.entity.Meeting;
import com.mozgotrash.smartbicstask.entity.MeetingDTO;
import com.mozgotrash.smartbicstask.repository.MeetingRepository;
import com.mozgotrash.smartbicstask.utils.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public List<Meeting> getMeetings() {
        List<Meeting> meetingsAll = meetingRepository.findAll();
        return meetingsAll;
    }

    public void getMeetingsFromDTO(List<MeetingDTO> meetingsDTO) {
        meetingsDTO.stream().map(meetingDTO -> {
            int meetingDuration = Integer.parseInt(meetingDTO.getDuration());
            Meeting meeting = new Meeting();

            meeting.setStartTime(LocalTime.parse(meetingDTO.getTimeStart()));
            meeting.setEndTime(meeting.getStartTime().plus(meetingDuration, ChronoUnit.HOURS));
            meeting.setDate(LocalDate.parse(meetingDTO.getDate()));
            meeting.setEmployee(meetingDTO.getEmployee());
            meeting.setSubmissionDate(LocalDate.now());

            return meeting;
        }).filter(this::isThisMeetingInOfficeTime)
                .filter(this::IsThisMeetingInTimeOfAnotherMeeting)
                .forEach(meetingRepository::save);
    }

    private boolean isThisMeetingInOfficeTime(final Meeting meeting) {
        final LocalTime startMeeting = meeting.getStartTime();
        final LocalTime finishMeeting = meeting.getEndTime();

        boolean b = ((startMeeting.isAfter(Office.START_WORK_TIME) || startMeeting.equals(Office.START_WORK_TIME))
                && (finishMeeting.isBefore(Office.FINISH_WORK_TIME) || finishMeeting.equals(Office.FINISH_WORK_TIME)));

        return ((startMeeting.isAfter(Office.START_WORK_TIME) || startMeeting.equals(Office.START_WORK_TIME))
                && (finishMeeting.isBefore(Office.FINISH_WORK_TIME) || finishMeeting.equals(Office.FINISH_WORK_TIME)));

    }

    /**
     * @param meeting
     * @return true - ...//TODO
     */
    private boolean IsThisMeetingInTimeOfAnotherMeeting(final Meeting meeting) {
        final LocalTime startMeeting = meeting.getStartTime();
        final LocalTime finishMeeting = meeting.getEndTime();

        return meetingRepository.findMeetingsByDate(meeting.getDate()).stream().allMatch(existingMeeting -> {
            LocalTime startAddedMeeting = existingMeeting.getStartTime();
            LocalTime finishAddedMeeting = existingMeeting.getEndTime();

            //true - если митинг не пересекается
            return (startMeeting.isBefore(startAddedMeeting) || startMeeting.equals(finishAddedMeeting)
                    || startMeeting.isAfter(finishAddedMeeting)) &&
                    (finishMeeting.isBefore(startAddedMeeting) || finishMeeting.equals(startAddedMeeting))
                    || finishMeeting.isAfter(finishAddedMeeting);
        });
    }
}
