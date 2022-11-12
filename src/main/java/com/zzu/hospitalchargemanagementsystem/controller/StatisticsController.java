package com.zzu.hospitalchargemanagementsystem.controller;


import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import com.zzu.hospitalchargemanagementsystem.model.Statistics;
import com.zzu.hospitalchargemanagementsystem.model.StatisticsPerDoctor;
import com.zzu.hospitalchargemanagementsystem.service.DoctorService;
import com.zzu.hospitalchargemanagementsystem.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String toStatisticsResult(Map<String,Object> map, HttpServletRequest request){

        String from = statisticsService.getThisMonthFirstDay();
        String to = statisticsService.getNowDay();

        Statistics statisticsTotal = statisticsService.getStatisticsTotal();
        map.put("statisticsTotal", statisticsTotal);

        List<Doctor> statisticsPerDoctor = statisticsService.getStatisticsPerDoctor();
        map.put("statisticsPerDoctor", statisticsPerDoctor);

        map.put("from", from);
        map.put("to", to);

        return "statisticsResult";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public String getStatisticsResult(Map<String,Object> map, HttpServletRequest request){

        String from = request.getParameter("from");
        String to = request.getParameter("to");

        Statistics statisticsTotal = statisticsService.getStatisticsTotal(from, to);
        map.put("statisticsTotal", statisticsTotal);

        List<Doctor> statisticsPerDoctor = statisticsService.getStatisticsPerDoctor(from, to);
        map.put("statisticsPerDoctor", statisticsPerDoctor);

        map.put("from", from);
        map.put("to", to);

        return "statisticsResult";
    }

    @RequestMapping(value = "/statistics/{doctor}", method = RequestMethod.GET)
    public String toStatisticsResult(@PathVariable("doctor")String doctor, Map<String,Object> map, HttpServletRequest request){
        String from = statisticsService.getThisMonthFirstDay();
        String to = statisticsService.getNowDay();
        List<StatisticsPerDoctor> diseasesStatisticsByDoctor = statisticsService.getDiseasesStatisticsByDoctor(doctor, from, to);
        map.put("from", from);
        map.put("to", to);
        map.put("diseasesStatisticsByDoctor", diseasesStatisticsByDoctor);
        return "statisticsResultPerDoctor";
    }

    @RequestMapping(value = "/statistics/{doctor}", method = RequestMethod.POST)
    public String getStatisticsResultPerDoctor(@PathVariable("doctor")String doctor, Map<String, Object> map, HttpServletRequest request){
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        List<StatisticsPerDoctor> diseasesStatisticsByDoctor = statisticsService.getDiseasesStatisticsByDoctor(doctor, from, to);
        map.put("from", from);
        map.put("to", to);
        map.put("diseasesStatisticsByDoctor", diseasesStatisticsByDoctor);
        return "statisticsResultPerDoctor";
    }


    @RequestMapping(value = "/statistics/detail/doctor={doctor}&from={from}&to={to}", method = RequestMethod.GET)
    public String toStatisticsResultDetail(@PathVariable("doctor")String doctor, @PathVariable("from")String from,@PathVariable("to")String to,Map<String,Object> map, HttpServletRequest request){
        List<StatisticsPerDoctor> diseasesStatisticsByDoctor = statisticsService.getDiseasesStatisticsByDoctor(doctor, from, to);
        map.put("from", from);
        map.put("to", to);
        map.put("diseasesStatisticsByDoctor", diseasesStatisticsByDoctor);
        map.put("doctor", doctorService.seletDoctorById(doctor));
        return "statisticsResultPerDoctorDetail";
    }
}
