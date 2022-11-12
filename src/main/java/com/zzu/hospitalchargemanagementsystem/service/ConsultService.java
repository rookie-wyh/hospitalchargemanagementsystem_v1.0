package com.zzu.hospitalchargemanagementsystem.service;

import com.github.pagehelper.PageInfo;
import com.zzu.hospitalchargemanagementsystem.model.Consult;
import com.zzu.hospitalchargemanagementsystem.model.Patient;

import java.util.List;

public interface ConsultService {

    public Integer insertConsult(Consult consult);

    public List<Consult> selectConsultByPatientId(String patientId);

    public PageInfo<Consult> selectConsultByPatientIdWithPages(String patientId, int pageNum);

    public Consult selectConsultById(int id);

    public void deleteConsultById(int id);

    public boolean updateConsult(Consult consult);
}
