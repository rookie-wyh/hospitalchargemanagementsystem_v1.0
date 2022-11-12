package com.zzu.hospitalchargemanagementsystem.mapper;

import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import com.zzu.hospitalchargemanagementsystem.model.DoctorPatient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorPatientMapper {

    public Integer insertRecord(DoctorPatient doctorPatient);

    public DoctorPatient selectRecord(DoctorPatient doctorPatient);

    public void updateRecord(DoctorPatient doctorPatient);

    public List<String> selectRecordByDoctor(@Param("doctor") String doctor);

    public void deleteRecord(@Param("doctor") String doctor, @Param("patient") String patient);

}
