package com.zzu.hospitalchargemanagementsystem.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.hospitalchargemanagementsystem.mapper.DoctorMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.DoctorPatientMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.PatientMapper;
import com.zzu.hospitalchargemanagementsystem.model.DoctorPatient;
import com.zzu.hospitalchargemanagementsystem.model.Patient;
import com.zzu.hospitalchargemanagementsystem.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private DoctorPatientMapper doctorPatientMapper;

    private Calendar calendar = Calendar.getInstance();

    @Override
    public List<Patient> selectPatientsByDiseases(String diseases) {
        List<Patient> patients = patientMapper.selectPatientsByDiseases(diseases);
        for(Patient patient : patients){
            patient.setAge(calendar.get(Calendar.YEAR) - patient.getYear());
        }
        return patients;
    }

    @Override
    public List<Patient> selectPatientsByDiseasesWithDoctor(String diseases, String doctor) {
        List<Patient> patients = patientMapper.selectPatientsByDiseases(diseases);
        List<String> pid_consult = patientMapper.selectPatientIdWithConsultDoctor(doctor);
        List<String> pid_treatment = patientMapper.selectPatientIdWithTreatmentDoctor(doctor);
        HashSet<String> pids = new HashSet<>();
        pids.addAll(pid_consult);
        pids.addAll(pid_treatment);
        ArrayList<Patient> patients_ = new ArrayList<Patient>();
        for(Patient patient : patients){
            if(pids.contains(patient.getId())){
                patient.setAge(calendar.get(Calendar.YEAR) - patient.getYear());
                patients_.add(patient);
            }
        }
        return patients_;
    }

    @Override
    public List<Patient> selectPatientsByDiseasesWithDoctorByPages(String diseases, String doctor) {
        return null;
    }

    @Override
    public Integer insertPatient(Patient patient, DoctorPatient doctorPatient) {
        Integer integer = patientMapper.insertPatient(patient);
        Integer res = doctorPatientMapper.insertRecord(doctorPatient);
        return res;
    }

    @Override
    public boolean updatePatient(Patient patient) {
        return patientMapper.updatePatient(patient);
    }

    @Override
    public Patient selectPatientById(String id) {
        Patient patient = patientMapper.selectPatientById(id);
        if(patient == null)return null;
        patient.setAge(calendar.get(Calendar.YEAR) - patient.getYear());
        patient.setVisitedtime(new Timestamp(System.currentTimeMillis()));
        boolean res = updatePatient(patient);
        return patient;
    }

    @Override
    public void deletePatientById(String id, String doctor) {
        patientMapper.deletePatientById(id);
        doctorPatientMapper.deleteRecord(doctor, id);
    }

    @Override
    public List<Patient> selectPatientByName(String name) {
        List<Patient> patients = patientMapper.selectPatientByName(name);
        for(Patient patient : patients){
            patient.setAge(calendar.get(Calendar.YEAR) - patient.getYear());
            patient.setVisitedtime(new Timestamp(System.currentTimeMillis()));
            boolean res = updatePatient(patient);
        }
        return patients;
    }

    @Override
    public List<Patient> selectPatients() {
        List<Patient> patients = patientMapper.selectPatients();
        for(Patient patient : patients)patient.setAge(calendar.get(Calendar.YEAR) - patient.getYear());
        return patients;
    }

    @Override
    public List<Patient> selectPatientsByIdList(List<String> patients) {
        if(patients.isEmpty())return new ArrayList<>();
        List<Patient> patients_list = patientMapper.selectPatientsByIdList(patients);
        for(Patient patient : patients_list)patient.setAge(calendar.get(Calendar.YEAR) - patient.getYear());
        return patients_list;
    }

    @Override
    public PageInfo<Patient> selectPatientsByPages(int pageNum) {
        PageHelper.startPage(pageNum, Const.NUMS_PATIENTS_OF_ONE_PAGE);
        List<Patient> patients_list = selectPatients();
        PageInfo<Patient> patients = new PageInfo<>(patients_list);
        return patients;
    }

    @Override
    public PageInfo<String> selectPatientsWithDoctorByPages(int pageNum, String doctor) {

//        List<String> pid_consult = patientMapper.selectPatientIdWithConsultDoctor(doctor);
//        List<String> pid_treatment = patientMapper.selectPatientIdWithTreatmentDoctor(doctor);
//        Set<String> pid_ = new HashSet<>();
//        pid_.addAll(pid_consult);
//        pid_.addAll(pid_treatment);
//        PageHelper.startPage(pageNum, Const.NUMS_PATIENTS_OF_ONE_PAGE);
//        List<Patient> patients_list = selectPatientsByIdList(new ArrayList<>(pid_));
//        PageInfo<Patient> patients_ = new PageInfo<>(patients_list);
//        return patients_;

        PageHelper.startPage(pageNum, Const.NUMS_PATIENTS_OF_ONE_PAGE);
        List<String> patient_id_list = doctorPatientMapper.selectRecordByDoctor(doctor);
        PageInfo<String> patientIdPageInfo = new PageInfo<>(patient_id_list);
        return patientIdPageInfo;
    }


}
