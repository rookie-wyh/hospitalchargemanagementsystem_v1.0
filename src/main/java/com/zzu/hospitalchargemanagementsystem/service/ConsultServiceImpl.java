package com.zzu.hospitalchargemanagementsystem.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.hospitalchargemanagementsystem.mapper.ConsultMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.DoctorMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.PatientMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.TreatmentMapper;
import com.zzu.hospitalchargemanagementsystem.model.Consult;
import com.zzu.hospitalchargemanagementsystem.model.Patient;
import com.zzu.hospitalchargemanagementsystem.model.Treatment;
import com.zzu.hospitalchargemanagementsystem.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ConsultServiceImpl implements ConsultService {

    @Autowired
    private ConsultMapper consultMapper;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TreatmentService treatmentService;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Integer insertConsult(Consult consult) {
        Integer integer = consultMapper.insertConsult(consult);
        return integer;
    }

    @Override
    public List<Consult> selectConsultByPatientId(String patientId) {
        List<Consult> consults = consultMapper.selectConsultByPatientId(patientId);
        for(Consult consult: consults){
            consult.setPatientObj(patientService.selectPatientById(consult.getPatient()));
            consult.setDoctorObj(doctorService.seletDoctorById(consult.getDoctor()));
            consult.setTreatments(treatmentService.selectTreatmentByConsultId(consult.getId()));
        }
        return consults;
    }

    @Override
    public PageInfo<Consult> selectConsultByPatientIdWithPages(String patientId, int pageNum) {
        PageHelper.startPage(pageNum, Const.NUMS_CONSULTS_OF_ONE_PAGE);
        List<Consult> consults_list = selectConsultByPatientId(patientId);
        PageInfo<Consult> consults = new PageInfo<>(consults_list);
        return consults;
    }
    @Override
    public Consult selectConsultById(int id) {
        Consult consult = consultMapper.selectConsultById(id);
        consult.setPatientObj(patientService.selectPatientById(consult.getPatient()));
        consult.setDoctorObj(doctorService.seletDoctorById(consult.getDoctor()));
        return consult;
    }

    @Override
    public void deleteConsultById(int id) {
        consultMapper.deleteConsultById(id);
    }

    @Override
    public boolean updateConsult(Consult consult) {
        boolean res = consultMapper.updateConsult(consult);
        return res;
    }
}
