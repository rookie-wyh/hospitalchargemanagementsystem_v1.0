package com.zzu.hospitalchargemanagementsystem.mapper;

import com.zzu.hospitalchargemanagementsystem.model.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientMapper {

    public Integer insertPatient(Patient patient);

    public void deletePatientById(String id);
    
    public boolean updatePatient(Patient patient);
    
    public Patient selectPatientById(String id);
    
    public List<Patient> selectPatientByName(String name);

    public List<Patient> selectPatients();

    public List<String> selectPatientIdWithConsultDoctor(@Param("doctor")String doctor);

    public List<String> selectPatientIdWithTreatmentDoctor(@Param("doctor")String doctor);

    public List<Patient> selectPatientsByIdList(@Param("patients")List<String> patients);

    public List<Patient> selectPatientsByDiseases(@Param("diseases") String diseases);
}
