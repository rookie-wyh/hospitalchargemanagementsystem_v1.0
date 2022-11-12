package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.mapper.StatisticsMapper;
import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import com.zzu.hospitalchargemanagementsystem.model.Statistics;
import com.zzu.hospitalchargemanagementsystem.model.StatisticsPerDoctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService{

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SelectBoxService selectBoxService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String getThisMonthFirstDay(){
        Calendar thisMonthFirstDateCal = Calendar.getInstance();
        thisMonthFirstDateCal.set(Calendar.DAY_OF_MONTH, 1);
        String thisMonthFirstTime = format.format(thisMonthFirstDateCal.getTime());
//        System.out.println("本月起始:" + thisMonthFirstTime);
        return thisMonthFirstTime;
    }

    @Override
    public String getNowDay(){
        Date date = new Date(System.currentTimeMillis());
        String format = this.format.format(date);
        return format;
    }

    @Override
    public Statistics getStatisticsTotal() {
        String from = getThisMonthFirstDay();
        String to = getNowDay();
        Statistics statistics = getStatisticsTotal(from, to);
        return statistics;
    }


    @Override
    public List<Doctor> getStatisticsPerDoctor() {
        String from = getThisMonthFirstDay();
        String to = getNowDay();
        List<Doctor> doctors = getStatisticsPerDoctor(from, to);
        return doctors;
    }

    @Override
    public Statistics getStatisticsTotal(String from, String to) {
        Statistics statistics = new Statistics();
        statistics.setConsultPatientNum(statisticsMapper.selectPatientNumInDate(from, to));
        statistics.setConsultPatientCount(statisticsMapper.selectPatientCountInDate(from, to));
        statistics.setTreatmentPatientNum(statisticsMapper.selectTreatmentPatientNumInDate(from, to));
        statistics.setTreatmentPatientCount(statisticsMapper.selectTreatmentPatientCountInDate(from, to));
        statistics.setTreatmentTotalCost(statisticsMapper.selectTreatmentCostInDate(from, to));
        return statistics;
    }

    @Override
    public List<Doctor> getStatisticsPerDoctor(String from, String to) {
        List<Doctor> doctors = doctorService.selectAllDoctor();
        for(Doctor doctor : doctors){
            doctor.setStatistics(getStatisticsByDoctor(doctor.getId(), from, to));
        }
        return doctors;
    }


    @Override
    public Statistics getStatisticsByDoctor(String doctor, String from, String to) {
        Statistics statistics = new Statistics();
        statistics.setTreatmentPatientNum(statisticsMapper.selectTreatmentPatientNumInDateWithDoctor(doctor, from, to));
        statistics.setTreatmentPatientCount(statisticsMapper.selectTreatmentPatientCountInDateWithDoctor(doctor, from, to));
        Float total = statisticsMapper.selectTreatmentCostInDateWithDoctor(doctor, from, to);
        statistics.setTreatmentTotalCost(total==null ? 0.0f :  total);
        return statistics;
    }

    @Override
    public List<StatisticsPerDoctor> getDiseasesStatisticsByDoctor(String doctor, String from, String to) {
        List<String> diseases_list = selectBoxService.selectDiseases();
        List<StatisticsPerDoctor> res = new ArrayList<>();
        for(String diseases : diseases_list){
            StatisticsPerDoctor statisticsPerDoctor = new StatisticsPerDoctor();
            statisticsPerDoctor.setDiseases(diseases);
            statisticsPerDoctor.setConsultDiseasesNumInDate(statisticsMapper.selectConsultDiseasesNumInDatePerDoctor(doctor, diseases, from, to));
            statisticsPerDoctor.setConsultDiseasesCountInDate(statisticsMapper.selectConsultDiseasesCountInDatePerDoctor(doctor, diseases, from, to));
            statisticsPerDoctor.setTreatmentDiseasesCountInDate(statisticsMapper.selectTreatmentDiseasesCountInDatePerDoctor(doctor, diseases, from, to));
            statisticsPerDoctor.setCostDiseasesInDate(statisticsMapper.selectCostDiseasesInDatePerDoctor(doctor, diseases, from, to));
            res.add(statisticsPerDoctor);
        }
        return res;
    }

}
