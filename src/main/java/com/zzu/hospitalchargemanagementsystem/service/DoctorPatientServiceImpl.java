package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.mapper.DoctorPatientMapper;
import com.zzu.hospitalchargemanagementsystem.model.DoctorPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DoctorPatientServiceImpl implements DoctorPatientService {

    @Autowired
    public DoctorPatientMapper doctorPatientMapper;

    @Override
    public Integer insertOrUpdateRecord(DoctorPatient doctorPatient) {
        DoctorPatient exist = doctorPatientMapper.selectRecord(doctorPatient);
        if(exist == null) doctorPatientMapper.insertRecord(doctorPatient);
        else doctorPatientMapper.updateRecord(doctorPatient);
        return 1;
    }
}
