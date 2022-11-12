package com.zzu.hospitalchargemanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class DoctorPatient {
    private String doctor;
    private String patient;
    private Date visitedtime;
}
