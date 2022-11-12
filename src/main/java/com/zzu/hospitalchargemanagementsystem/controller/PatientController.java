package com.zzu.hospitalchargemanagementsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.hospitalchargemanagementsystem.model.DoctorPatient;
import com.zzu.hospitalchargemanagementsystem.model.Patient;
import com.zzu.hospitalchargemanagementsystem.service.DoctorService;
import com.zzu.hospitalchargemanagementsystem.service.PatientService;
import com.zzu.hospitalchargemanagementsystem.service.SelectBoxService;
import com.zzu.hospitalchargemanagementsystem.utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private SelectBoxService selectBoxService;

    private Calendar calendar = Calendar.getInstance();

    private ObjectMapper objectMapper = new ObjectMapper();

    @ResponseBody
    @RequestMapping(value = "/patient_exist/{id}", method = RequestMethod.GET)
    public String selectPatientById(@PathVariable("id")String id, Map<String,Object> map, HttpServletRequest request) throws JsonProcessingException {
        Patient patient = patientService.selectPatientById(id);
        if(patient != null){
            map.put("code", 1);
            map.put("id", id);
        }else{
            map.put("code", 0);
        }
        return objectMapper.writeValueAsString(map);
    }

    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public String selectPatients(){
        return "redirect:/patient/1";
    }

    @RequestMapping(value = "/patient/{pageNum}", method = RequestMethod.GET)
    public String selectPatients(@PathVariable("pageNum")int pageNum, Map<String,Object> map, HttpServletRequest request){
//        PageInfo<Patient> patients = patientService.selectPatientsByPages(pageNum);
        String doctor = (String)request.getSession().getAttribute("loginUserId");
        PageInfo<String> patientPageInfo = patientService.selectPatientsWithDoctorByPages(pageNum, doctor);
        ArrayList<Patient> patients = new ArrayList<>();
        for(String pid : patientPageInfo.getList())patients.add(patientService.selectPatientById(pid));
        map.put("patients", patients);
        map.put("patientPageInfo", patientPageInfo);
        List<String> diseases_list = selectBoxService.selectDiseases();
        map.put("diseases_list", diseases_list);
        return "patients";
    }

    @RequestMapping(value = "/patient/search", method = RequestMethod.POST)
    public String searchPatient(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        List<Patient> searched_patients = new ArrayList<>();
        if(!"".equals(id)){
            Patient patient = patientService.selectPatientById(id);
            map.put("condition_id", id);
            if(patient!=null)searched_patients.add(patient);
        }else{
            map.put("condition_name", name);
            searched_patients = patientService.selectPatientByName(name);
        }
        map.put("searched_patients", searched_patients);
        String doctor = (String)request.getSession().getAttribute("loginUserId");
        PageInfo<String> patientPageInfo = patientService.selectPatientsWithDoctorByPages(1, doctor);
        ArrayList<Patient> patients = new ArrayList<>();
        for(String pid : patientPageInfo.getList())patients.add(patientService.selectPatientById(pid));
        map.put("patients", patients);
        map.put("patientPageInfo", patientPageInfo);
        List<String> diseases_list = selectBoxService.selectDiseases();
        map.put("diseases_list", diseases_list);

        return "patients";
    }

    @RequestMapping(value = "/patient/searchdiseases", method = RequestMethod.POST)
    public String searchDiseasesPatient(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        String doctor = (String)request.getSession().getAttribute("loginUserId");
        String diseases_ = "";
        String diseasesSelect = request.getParameter("diseasesSelect");
        String diseases = request.getParameter("diseases");
        if(!"".equals(diseases)){
            diseases_ = diseases;
            map.put("condition_diseases", diseases_);
        }else{
            diseases_ = diseasesSelect;
            map.put("condition_diseases_select", diseases_);
        }
        List<Patient> searched_patients = patientService.selectPatientsByDiseasesWithDoctor(diseases_, doctor);
        map.put("searched_patients", searched_patients);
        PageInfo<String> patientPageInfo = patientService.selectPatientsWithDoctorByPages(1, doctor);
        ArrayList<Patient> patients = new ArrayList<>();
        for(String pid : patientPageInfo.getList())patients.add(patientService.selectPatientById(pid));
        map.put("patients", patients);
        map.put("patientPageInfo", patientPageInfo);
        List<String> diseases_list = selectBoxService.selectDiseases();
        map.put("diseases_list", diseases_list);
        return "patients";
    }

    @RequestMapping(value = "/toaddpatient", method = RequestMethod.GET)
    public String toInsertPatient(){
        return "patientAdd";
    }

    @RequestMapping(value = "/toupdatepatient/{id}", method = RequestMethod.GET)
    public String toUpdatePatient(@PathVariable("id")String id, Map<String,Object> map){
        Patient patient = patientService.selectPatientById(id);
        map.put("patient", patient);
        return "patientUpdate";
    }

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.DELETE)
    public String deletePatient(@PathVariable("id")String id,  HttpServletRequest request){
        String loginUserId = (String) request.getSession().getAttribute("loginUserId");
        log.info("用户 " + loginUserId + " 删除患者信息 " + patientService.selectPatientById(id));
        patientService.deletePatientById(id, loginUserId);
        return "redirect:/patient/1";
    }

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.PUT)
    public String updatePatient(@PathVariable("id")String id, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        Patient pre = patientService.selectPatientById(id);
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        Patient patient = new Patient();
        patient.setId(id);
        patient.setName(name);
        patient.setGender(gender);
        String remark = request.getParameter("remark");
        remark = remark.length() > Const.MAX_LENS_FIELD ? remark.substring(0, Const.MAX_LENS_FIELD) : remark;;
        patient.setVisitedtime(new Timestamp(System.currentTimeMillis()));
        patient.setRemark(remark);
        // 可能有 bug 年龄必须为数字
        patient.setYear(calendar.get(Calendar.YEAR) - Integer.parseInt(age));

        boolean res = patientService.updatePatient(patient);
        log.info("用户 " + (String)request.getSession().getAttribute("loginUserId") + " 修改患者信息 " + pre + " 为 " + patient);
        return "redirect:/patient/1";
    }

    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public String insertPatient(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){

        String name = request.getParameter("name").trim();
        String id = request.getParameter("id");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        Patient patient = new Patient();
        patient.setId(id);
        String remark = request.getParameter("remark");
        remark = remark.length() > Const.MAX_LENS_FIELD ? remark.substring(0, Const.MAX_LENS_FIELD) : remark;
        patient.setName(name);
        patient.setGender(gender);
        patient.setVisitedtime(new Timestamp(System.currentTimeMillis()));
        patient.setRemark(remark);
        // 可能有 bug 年龄必须为数字
        patient.setYear(calendar.get(Calendar.YEAR) - Integer.parseInt(age));
        String loginUserId = (String) request.getSession().getAttribute("loginUserId");
        DoctorPatient doctorPatient = new DoctorPatient();
        doctorPatient.setDoctor(loginUserId);
        doctorPatient.setPatient(patient.getId());
        doctorPatient.setVisitedtime(patient.getVisitedtime());
        Integer res = patientService.insertPatient(patient, doctorPatient);
        log.info("用户 " + loginUserId + " 新增患者信息 " + patient);
        return "redirect:/patient/1";
    }


}
