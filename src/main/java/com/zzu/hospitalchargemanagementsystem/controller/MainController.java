package com.zzu.hospitalchargemanagementsystem.controller;

import com.zzu.hospitalchargemanagementsystem.HospitalchargemanagementsystemApplication;
import com.zzu.hospitalchargemanagementsystem.service.DoctorService;
import com.zzu.hospitalchargemanagementsystem.service.PatientService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HospitalchargemanagementsystemApplication.class)
@Controller
public class MainController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Map<String,Object> map, HttpServletRequest request){
        String id = (String) request.getSession().getAttribute("loginUserId");
        map.put("doctor", doctorService.seletDoctorById(id));
        return "main";
    }
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "home";
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Index(){
        return "index";
    }
}
