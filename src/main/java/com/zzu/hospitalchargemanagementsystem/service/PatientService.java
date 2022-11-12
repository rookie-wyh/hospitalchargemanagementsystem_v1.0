package com.zzu.hospitalchargemanagementsystem.service;

import com.github.pagehelper.PageInfo;
import com.zzu.hospitalchargemanagementsystem.model.DoctorPatient;
import com.zzu.hospitalchargemanagementsystem.model.Patient;

import java.util.List;

public interface PatientService {

    public List<Patient> selectPatientsByDiseases(String diseases);

    public List<Patient> selectPatientsByDiseasesWithDoctor(String diseases, String doctor);

    public List<Patient> selectPatientsByDiseasesWithDoctorByPages(String diseases, String doctor);

    public Integer insertPatient(Patient patient, DoctorPatient doctorPatient);

    public boolean updatePatient(Patient patient);

    public Patient selectPatientById(String id);

    public void deletePatientById(String id, String doctor);

    public List<Patient> selectPatientByName(String name);

    public List<Patient> selectPatients();

    public List<Patient> selectPatientsByIdList(List<String> patients);

    public PageInfo<Patient> selectPatientsByPages(int pageNum);

    public PageInfo<String> selectPatientsWithDoctorByPages(int pageNum, String doctor);
}
