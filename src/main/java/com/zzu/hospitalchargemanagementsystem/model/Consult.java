package com.zzu.hospitalchargemanagementsystem.model;
import java.util.Date;
import java.util.List;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Consult {

    private int id;
    private String patient;
    private String doctor;
    private String diseases;
    private String state;
    private String result;
    private String scheme;
    private float cost;
    private float balance;
    private Date date;
    private String remark;
    private List<Treatment> treatments;

    private Patient patientObj;
    private Doctor doctorObj;

}
