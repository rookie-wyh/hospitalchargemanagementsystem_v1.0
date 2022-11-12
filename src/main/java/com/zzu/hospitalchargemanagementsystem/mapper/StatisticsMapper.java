package com.zzu.hospitalchargemanagementsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface StatisticsMapper {

    // 查询最近时间的患者人数
    public int selectPatientNumInDate(@Param("from") String from, @Param("to") String to);

    // 查询最近时间的患者人次
    public int selectPatientCountInDate(@Param("from") String from, @Param("to") String to);

    // 查询最近时间的患者人数 With 状态
    public int selectPatientNumInDateAndState(@Param("state") String state, @Param("from") String from, @Param("to") String to);

    // 查询最近时间的患者人次 With 状态
    public int selectPatientCountInDateAndState(@Param("state") String state, @Param("from") String from, @Param("to") String to);

    // 查询最近时间的治疗人数
    public int selectTreatmentPatientNumInDate(@Param("from") String from, @Param("to") String to);

    // 查询最近时间的治疗人次
    public int selectTreatmentPatientCountInDate(@Param("from") String from, @Param("to") String to);

    // 查询最近时间的治疗费用
    public float selectTreatmentCostInDate(@Param("from") String from, @Param("to") String to);

    // 查询每个医生最近时间治疗的患者人数
    public int selectTreatmentPatientNumInDateWithDoctor(@Param("doctor") String doctor, @Param("from") String from, @Param("to") String to);

    // 查询每个医生最近时间治疗的患者人次
    public int selectTreatmentPatientCountInDateWithDoctor(@Param("doctor") String doctor, @Param("from") String from, @Param("to") String to);

    // 查询最近时间每个医生的治疗费用
    public float selectTreatmentCostInDateWithDoctor(@Param("doctor") String doctor, @Param("from") String from, @Param("to") String to);

    /**
     private int consultDiseasesNumInDate;
     private int consultDiseasesCountInDate;
     private int treatmentDiseasesCountInDate;
     private float costDiseasesInDate;
     */

    // 最近一个医生门诊的一个病种的人数
    public int selectConsultDiseasesNumInDatePerDoctor(@Param("doctor")String doctor, @Param("diseases")String diseases, @Param("from")String from, @Param("to")String to);

    //最近一个医生门诊的一个病种的人次
    public int selectConsultDiseasesCountInDatePerDoctor(@Param("doctor")String doctor, @Param("diseases")String diseases, @Param("from")String from, @Param("to")String to);

    // 最近一个医生门诊的一个治疗的病人
    public int selectTreatmentDiseasesCountInDatePerDoctor(@Param("doctor")String doctor, @Param("diseases")String diseases, @Param("from")String from, @Param("to")String to);

    //最近一个医生治疗的一个病种的收费
    public float selectCostDiseasesInDatePerDoctor(@Param("doctor")String doctor, @Param("diseases")String diseases, @Param("from")String from, @Param("to")String to);
}
