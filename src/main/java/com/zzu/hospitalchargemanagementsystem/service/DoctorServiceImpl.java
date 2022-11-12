package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.mapper.DoctorMapper;
import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{

    @Autowired
    public DoctorMapper doctorMapper;

    @Override
    public int accountJudgment(String id, String password) {
        Doctor doctor = doctorMapper.seletDoctorById(id);
        if(doctor != null && doctor.getPassword().equals(password))return 1;
        if(doctor != null && !doctor.getPassword().equals(password))return 2;
        return 0;
    }

    @Override
    public boolean resetPassword(String id, String password) {
        return doctorMapper.resetPassword(id, password);
    }

    @Override
    public Doctor seletDoctorById(String id) {
        return doctorMapper.seletDoctorById(id);
    }

    @Override
    public List<Doctor> selectAllDoctor() {
        List<Doctor> doctors = doctorMapper.selectAllDoctor();
        return doctors;
    }

    @Override
    public Integer insertDoctor(Doctor doctor) {
        return doctorMapper.insertDoctor(doctor);
    }

    @Override
    public boolean updateDoctor(Doctor doctor) {
        return doctorMapper.updateDoctor(doctor);
    }
}
