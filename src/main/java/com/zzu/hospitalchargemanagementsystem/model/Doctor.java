package com.zzu.hospitalchargemanagementsystem.model;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private String id;
    private String password;
    private String name;
    private String post;
    private String gender;

    private Statistics statistics;

}
