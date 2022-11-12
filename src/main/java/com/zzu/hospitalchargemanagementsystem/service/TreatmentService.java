package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.model.Treatment;

import java.util.List;

public interface TreatmentService {

    public Integer insertTreatment(int consultId, Treatment treatment);

    public Treatment selectTreatmentById(int id);

    public void deleteTreatmentById(int consultId, int treatmentId);

    public boolean updateTreatment(Treatment treatment);

    public List<Treatment> selectTreatmentByConsultId(int consultId);
}
