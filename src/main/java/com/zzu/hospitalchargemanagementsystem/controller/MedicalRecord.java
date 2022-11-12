package com.zzu.hospitalchargemanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.zzu.hospitalchargemanagementsystem.model.*;
import com.zzu.hospitalchargemanagementsystem.service.*;
import com.zzu.hospitalchargemanagementsystem.utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MedicalRecord {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ConsultService consultService;

    @Autowired
    public TreatmentService treatmentService;

    @Autowired
    public PatientService patientService;

    @Autowired
    public DoctorService doctorService;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    public SelectBoxService selectBoxService;

    @Autowired
    public DoctorPatientService doctorPatientService;

    @RequestMapping(value = "/consult/{id}", method = RequestMethod.GET)
    public String selectConsults(@PathVariable("id")String id){
        return "redirect:/consult/patient=" + id + "/page=1";
    }

    @RequestMapping(value = "/consult/patient={id}/page={pageNum}", method = RequestMethod.GET)
    public String getConsultResult(@PathVariable("id")String id, @PathVariable("pageNum")int pageNum , Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        PageInfo<Consult> consults = consultService.selectConsultByPatientIdWithPages(id, pageNum);
        Patient patient = patientService.selectPatientById(id);
        map.put("consults", consults);
        map.put("patient", patient);
        return "medicalRecord";
    }

    @RequestMapping(value = "/consult/{id}", method = RequestMethod.PUT)
    public String updateConsultResult(@PathVariable("id")Integer id, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){

        String patient = request.getParameter("patient");
        String diseases_ = "";
        String diseases = request.getParameter("diseases");
        diseases = diseases.length() > Const.MAX_LENS_FIELD ? diseases.substring(0, Const.MAX_LENS_FIELD) : diseases;
        if("".equals(diseases)) {
            diseases_ = request.getParameter("diseasesSelect");;
        }else {
            diseases_ = diseases;
            List<String> diseases_list = selectBoxService.selectDiseases();
            if(!diseases_list.contains(diseases_)){
                selectBoxService.insertDiseases(diseases_);
            }
        }
        String state = request.getParameter("state");
        String result = request.getParameter("result");
        result = result.length() > Const.MAX_LENS_FIELD ? result.substring(0, Const.MAX_LENS_FIELD) : result;
        String scheme = request.getParameter("scheme");
        scheme = scheme.length() > Const.MAX_LENS_FIELD ? scheme.substring(0, Const.MAX_LENS_FIELD) : scheme;
        String cost = request.getParameter("cost");
        String remark = request.getParameter("remark");
        remark = remark.length() > Const.MAX_LENS_FIELD ? remark.substring(0, Const.MAX_LENS_FIELD) : remark;
        Consult consult = new Consult();
        consult.setId(id);
        consult.setDiseases(diseases_);
        consult.setState(state);
        consult.setScheme(scheme);
        consult.setResult(result);
        consult.setCost(Float.parseFloat(cost));
        consult.setRemark(remark);

        boolean res = consultService.updateConsult(consult);
        log.info("用户 " + (String)request.getSession().getAttribute("loginUserId") + " 修改诊断记录 " + consultService.selectConsultById(id) + " 为 " + consult);
        return "redirect:/consult/"+patient;
    }

    @RequestMapping(value = "/toupdateconsult/{id}", method = RequestMethod.GET)
    public String toUpdateConsult(@PathVariable("id")int id,Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        Consult consult = consultService.selectConsultById(id);
        map.put("consult", consult);
        List<String> diseases_list = selectBoxService.selectDiseases();
        map.put("diseases_list", diseases_list);
        return "consultUpdate";
    }


    @RequestMapping(value = "/toaddconsult/patient={id}", method = RequestMethod.GET)
    public String getRecord(@PathVariable("id")String id, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        Patient patient = patientService.selectPatientById(id);
        map.put("patient", patient);
        List<String> diseases_list = selectBoxService.selectDiseases();
        map.put("diseases_list", diseases_list);
        return "consultAdd";
    }

    @RequestMapping(value = "/consult/{id}/treatment", method = RequestMethod.GET)
    public String toInsertTreatmentRecord(@PathVariable("id")Integer id, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        String doctorId = (String) request.getSession().getAttribute("loginUserId");
        Consult consult = consultService.selectConsultById(id);
        Doctor doctor = doctorService.seletDoctorById(doctorId);
        map.put("consult", consult);
        map.put("doctor", doctor);

        List<String> projects = selectBoxService.selectProject();
        map.put("project_list", projects);
        return "treatmentAdd";
    }

    @RequestMapping(value = "/consult/{id}/treatment", method = RequestMethod.POST)
    public String insertTreatmentRecord(@PathVariable("id")Integer id, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        String project_ = "";
        String project = request.getParameter("project");
        project = project.length() > Const.MAX_LENS_FIELD ? project.substring(0, Const.MAX_LENS_FIELD) : project;
        if("".equals(project)) {
            project_ = request.getParameter("projectSelect");
        }else {
            project_ = project;
            List<String> diseases_list = selectBoxService.selectProject();
            if(!diseases_list.contains(project_)){
                selectBoxService.insertProject(project_);
            }
        }
        String cost = request.getParameter("cost").trim().equals("") ? "0":request.getParameter("cost");
        String level = request.getParameter("level");
        String timelen = request.getParameter("timelen").trim().equals("") ? "0":request.getParameter("timelen");
        String remark = request.getParameter("remark");
        remark = remark.length() > Const.MAX_LENS_FIELD ? remark.substring(0, Const.MAX_LENS_FIELD) : remark;
        Treatment treatment = new Treatment();
        String doctor = (String) request.getSession().getAttribute("loginUserId");
        treatment.setDoctor(doctor);
        treatment.setProject(project_);
        treatment.setConsult(id);
//        treatment.setTimelen(Integer.parseInt(timelen));
//        treatment.setCost(Float.parseFloat(cost));

        try{
            treatment.setCost(Float.parseFloat(cost));
        }catch (Exception e){
            treatment.setCost(0f);
            e.printStackTrace();
        }finally {

        }
        try{
            treatment.setTimelen(Integer.parseInt(timelen));
        }catch (Exception e){
            treatment.setTimelen(0);
            e.printStackTrace();
        }finally {

        }
        treatment.setLevel(level);
        treatment.setRemark(remark);
        treatment.setDate(new Timestamp(System.currentTimeMillis()));

        Integer res = treatmentService.insertTreatment(id, treatment);

        Consult consult = consultService.selectConsultById(id);
        String patient = consult.getPatient();

        DoctorPatient doctorPatient = new DoctorPatient();
        doctorPatient.setDoctor(doctor);
        doctorPatient.setPatient(patient);
        doctorPatient.setVisitedtime(consult.getDate());
        doctorPatientService.insertOrUpdateRecord(doctorPatient);

        log.info("用户 " + (String) request.getSession().getAttribute("loginUserId") + " 添加治疗记录 " + treatment);

        return "redirect:/consult/" + patient;
    }

    @RequestMapping(value = "/treatment/{id}", method = RequestMethod.GET)
    public String updateTreatmentRecord(@PathVariable("id")Integer id,Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        Treatment treatment = treatmentService.selectTreatmentById(id);
        map.put("treatment", treatment);
        return "treatmentResult";
    }

    @RequestMapping(value = "/consult/{consultId}", method = RequestMethod.DELETE)
    public String deleteConsultRecord(@PathVariable("consultId")Integer consultId, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){

        Consult consult = consultService.selectConsultById(consultId);
        String patient = consult.getPatient();
        consultService.deleteConsultById(consultId);
        log.info("用户 " + (String) request.getSession().getAttribute("loginUserId") + " 删除诊断记录 " + consult);
        return "redirect:/consult/" + patient;
    }

    @RequestMapping(value = "/consult/detail/{consultId}", method = RequestMethod.GET)
    public String getConsultRecord(@PathVariable("consultId")Integer consultId, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){

        Consult consult = consultService.selectConsultById(consultId);
        String patient = consult.getPatient();

        return "redirect:/consult/" + patient;
    }

    @RequestMapping(value = "/consult/{consultId}/treatment/{treatmentId}", method = RequestMethod.DELETE)
    public String deleteTreatmentRecord(@PathVariable("consultId")Integer consultId, @PathVariable("treatmentId")Integer treatmentId, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        Consult consult = consultService.selectConsultById(consultId);
        String patient = consult.getPatient();
        log.info("用户 " + (String) request.getSession().getAttribute("loginUserId") + " 删除治疗记录 " + treatmentService.selectTreatmentById(treatmentId));
        treatmentService.deleteTreatmentById(consultId, treatmentId);
        return "redirect:/consult/" + patient;
    }

    @RequestMapping(value = "/consult/{consultId}/treatment/{treatmentId}", method = RequestMethod.GET)
    public String getTreatmentRecord(@PathVariable("consultId")Integer consultId, @PathVariable("treatmentId")Integer treatmentId, Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        Consult consult = consultService.selectConsultById(consultId);
        String patient = consult.getPatient();
        return "redirect:/consult/" + patient;
    }


    @RequestMapping(value = "/consult", method = RequestMethod.POST)
    public String insertConsultRecord(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        String patient_id = request.getParameter("patient");
        String diseases_ = "";
        String diseases = request.getParameter("diseases");
        diseases = diseases.length() > Const.MAX_LENS_FIELD ? diseases.substring(0, Const.MAX_LENS_FIELD) : diseases;
        if("".equals(diseases)) {
            diseases_ = request.getParameter("diseasesSelect");;
        }else {
            diseases_ = diseases;
            List<String> diseases_list = selectBoxService.selectDiseases();
            if(!diseases_list.contains(diseases_)){
                selectBoxService.insertDiseases(diseases_);
            }
        }
        String state = request.getParameter("state");
        String result = request.getParameter("result");
        result = result.length() > Const.MAX_LENS_FIELD ? result.substring(0, Const.MAX_LENS_FIELD) : result;
        String scheme = request.getParameter("scheme");
        scheme = scheme.length() > Const.MAX_LENS_FIELD ? scheme.substring(0, Const.MAX_LENS_FIELD) : scheme;
        String cost = request.getParameter("cost");
        String remark = request.getParameter("remark");
        remark = remark.length() > Const.MAX_LENS_FIELD ? remark.substring(0, Const.MAX_LENS_FIELD) : remark;

        // bug
        String doctor = (String) request.getSession().getAttribute("loginUserId");

        Consult consult = new Consult();
        consult.setPatient(patient_id);
        consult.setDiseases(diseases_);
        consult.setState(state);
        consult.setScheme(scheme);
        consult.setResult(result);
        consult.setCost(Float.parseFloat(cost));
        consult.setBalance(consult.getCost());
        consult.setRemark(remark);
        consult.setDoctor(doctor);
        consult.setDate(new Timestamp(System.currentTimeMillis()));
        Integer res = consultService.insertConsult(consult);

        DoctorPatient doctorPatient = new DoctorPatient();
        doctorPatient.setDoctor(doctor);
        doctorPatient.setPatient(patient_id);
        doctorPatient.setVisitedtime(consult.getDate());
        doctorPatientService.insertOrUpdateRecord(doctorPatient);

        log.info("用户 " + (String) request.getSession().getAttribute("loginUserId") + " 新增诊断记录 " + consult);

        return "redirect:/consult/" + patient_id;
    }

    @RequestMapping(value = "/toaddpayment/{consult}",  method = RequestMethod.GET)
    public String toInsertPayment(@PathVariable("consult")Integer consultId, Map<String,Object> map, HttpServletRequest request){
        map.put("consult", consultId);
        Consult consult = consultService.selectConsultById(consultId);
        Patient patientObj = consult.getPatientObj();
        map.put("patient", patientObj);
        return "paymentAdd";
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String insertPayment(Map<String,Object> map, HttpServletRequest request){
        String consult = request.getParameter("consult");
        String amount = request.getParameter("amount").trim().equals("") ? "0":request.getParameter("amount");
        Payment payment = new Payment();
        try{
            payment.setAmount(Float.parseFloat(amount));
        }catch (Exception e){
            payment.setAmount(0f);
        }
        payment.setDate(new Timestamp(System.currentTimeMillis()));

        payment.setConsult(Integer.parseInt(consult));
        String remark = request.getParameter("remark");
        remark = remark.length() > Const.MAX_LENS_FIELD ? remark.substring(0, Const.MAX_LENS_FIELD) : remark;
        payment.setRemark(remark);
        Integer res = paymentService.insertPayment(payment);

        Consult consult_ = consultService.selectConsultById(Integer.parseInt(consult));
        String patient = consult_.getPatient();
        log.info("用户 " + (String) request.getSession().getAttribute("loginUserId") + " 新增缴费记录 " + payment);
        return "redirect:/consult/" + patient;
    }

    @RequestMapping(value = "/delpayment/{id}", method = RequestMethod.GET)
    public String delPayment(@PathVariable("id")Integer id, Map<String,Object> map, HttpServletRequest request){
        Payment payment = paymentService.selectPaymentById(id);
        Integer consultId = payment.getConsult();
        log.info("用户 " + (String) request.getSession().getAttribute("loginUserId") + " 删除缴费记录 " + payment);
        paymentService.deletePayment(id);

        Consult consult = consultService.selectConsultById(consultId);
        String patient = consult.getPatient();
        return "redirect:/consult/" + patient;
    }

    @RequestMapping(value = "/payment/{consult}", method = RequestMethod.GET)
    public String selectPayment(@PathVariable("consult")Integer consult,Map<String,Object> map, HttpServletRequest request){
        List<Payment> payments = paymentService.selectPaymentByConsult(consult);
        map.put("payments", payments);
        return "paymentResult";
    }

}
