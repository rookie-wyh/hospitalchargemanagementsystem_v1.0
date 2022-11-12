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
public class Payment {

    private Integer id;
    private Integer consult;
    private String patient;
    private Date date;
    private float amount;
    private String remark;
}
