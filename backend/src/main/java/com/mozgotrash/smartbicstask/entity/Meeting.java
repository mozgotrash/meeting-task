package com.mozgotrash.smartbicstask.entity;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table
@Data
public class Meeting {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate date;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalTime startTime;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalTime endTime;

    @Column
    private String employee;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private int duration;

    @Column
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime submissionDate;


}
