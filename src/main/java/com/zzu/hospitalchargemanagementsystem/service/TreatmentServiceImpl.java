package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.mapper.ConsultMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.DoctorMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.TreatmentMapper;
import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import com.zzu.hospitalchargemanagementsystem.model.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TreatmentServiceImpl implements TreatmentService{

    @Autowired
    private TreatmentMapper treatmentMapper;

    @Autowired
    private ConsultMapper consultMapper;

    @Autowired
    public DoctorService doctorService;

    @Autowired
    public PatientService patientService;

    @Override
    public Integer insertTreatment(int consultId, Treatment treatment) {
        consultMapper.updateConsultBalance(consultId, treatment.getCost());
        Integer res = treatmentMapper.insertTreatment(treatment);
        return res;
    }

    @Override
    public Treatment selectTreatmentById(int id) {
        Treatment treatment = treatmentMapper.selectTreatmentById(id);
        if(treatment!=null){
            treatment.setDoctorObj(doctorService.seletDoctorById(treatment.getDoctor()));
        }
        return treatment;
    }

    @Override
    public void deleteTreatmentById(int consultId, int treatmentId) {
        Treatment treatment = treatmentMapper.selectTreatmentById(treatmentId);
        boolean res = consultMapper.updateConsultBalance(consultId, -1 * treatment.getCost());
        treatmentMapper.deleteTreatmentById(treatmentId);
    }

    @Override
    public boolean updateTreatment(Treatment treatment) {
        boolean res = treatmentMapper.updateTreatment(treatment);
        return res;
    }

    @Override
    public List<Treatment> selectTreatmentByConsultId(int consultId) {
        List<Treatment> treatments = treatmentMapper.selectTreatmentByConsultId(consultId);
        for(Treatment treatment : treatments){
            treatment.setDoctorObj(doctorService.seletDoctorById(treatment.getDoctor()));
        }
        return treatments;
    }
}
