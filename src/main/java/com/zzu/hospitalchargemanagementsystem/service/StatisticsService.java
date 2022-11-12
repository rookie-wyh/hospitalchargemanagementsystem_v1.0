package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import com.zzu.hospitalchargemanagementsystem.model.Statistics;
import com.zzu.hospitalchargemanagementsystem.model.StatisticsPerDoctor;

import java.util.List;

public interface StatisticsService {

    public String getThisMonthFirstDay();

    public String getNowDay();

    public Statistics getStatisticsTotal();

    public List<Doctor> getStatisticsPerDoctor();

    public Statistics getStatisticsByDoctor(String doctor, String from, String to);

    public Statistics getStatisticsTotal(String from, String to);

    public List<Doctor> getStatisticsPerDoctor(String from, String to);

    public List<StatisticsPerDoctor> getDiseasesStatisticsByDoctor(String doctor, String from, String to);

}
