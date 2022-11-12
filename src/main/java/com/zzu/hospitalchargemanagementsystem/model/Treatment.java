package com.zzu.hospitalchargemanagementsystem.model;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Treatment {

    private int id;
    private int consult;
    private String patient;
    private String doctor;
    private String project;
    private float cost;
    private String level;
    private Date date;
    private int timelen;
    private String remark;

    private Doctor doctorObj;

}
