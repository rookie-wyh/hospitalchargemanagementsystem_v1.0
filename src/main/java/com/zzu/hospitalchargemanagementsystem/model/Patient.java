package com.zzu.hospitalchargemanagementsystem.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Patient {

    private String id;
    private String name;
    private Integer year;
    private Integer age;
    private String gender;

    private String remark;
    private Date visitedtime;

}
