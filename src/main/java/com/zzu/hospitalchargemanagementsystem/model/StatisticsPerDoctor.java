package com.zzu.hospitalchargemanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatisticsPerDoctor {

    private String diseases;
    private int consultDiseasesNumInDate;
    private int consultDiseasesCountInDate;
    private int treatmentDiseasesCountInDate;
    private float costDiseasesInDate;

}
