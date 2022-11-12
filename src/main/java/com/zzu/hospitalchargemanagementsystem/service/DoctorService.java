package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.model.Doctor;

import javax.print.Doc;
import java.util.List;

public interface DoctorService {

    public int accountJudgment(String id, String password);

    public boolean resetPassword(String id, String password);

    public Doctor seletDoctorById(String id);

    public List<Doctor> selectAllDoctor();

    public Integer insertDoctor(Doctor doctor);

    public boolean updateDoctor(Doctor doctor);

}
