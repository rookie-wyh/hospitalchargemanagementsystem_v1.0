package com.zzu.hospitalchargemanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Statistics {

    private int consultPatientNum;
    private int consultPatientCount;
    private int treatmentPatientNum;
    private int treatmentPatientCount;
    private float treatmentTotalCost;

}
