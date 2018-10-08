package com.mozgotrash.smartbicstask.repository;

import com.mozgotrash.smartbicstask.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query(
            value = "SELECT * FROM Meeting m WHERE m.date = :date",
            nativeQuery = true)
    public List<Meeting> findMeetingsByDate(@Param("date") LocalDate date);

    public List<Meeting> findByDate(LocalDate date);

    @Query(
            value = "SELECT * FROM Meeting m ORDER BY m.date",
            nativeQuery = true)
    public List<Meeting> findAllMeetings();
}
