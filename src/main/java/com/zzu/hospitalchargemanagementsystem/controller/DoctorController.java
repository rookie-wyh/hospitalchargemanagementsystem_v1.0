package com.zzu.hospitalchargemanagementsystem.controller;

import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import com.zzu.hospitalchargemanagementsystem.service.DoctorService;
import com.zzu.hospitalchargemanagementsystem.service.DoctorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class DoctorController {

    @Autowired
    public DoctorService doctorService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        int res = doctorService.accountJudgment(id, password);
        if(res == 1){
            HttpSession session = request.getSession();
            session.setAttribute("loginUserId", id);
            log.info("用户 " + id + "登录系统");
            return "redirect:/main";
        }else{
            map.put("msg", res == 0 ? "用户不存在" : "密码错误");
            return "index";
        }
    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    public String signout(Map<String,Object> map, HttpServletRequest request){
        String id = (String)request.getSession().getAttribute("loginUserId");
        log.info("用户 " + id + "退出登录");
        request.getSession().removeAttribute("loginUserId");
        return "redirect:/";
    }

    @RequestMapping(value = "/toresetpassword", method = RequestMethod.GET)
    public String toResetPassword(Map<String,Object> map, HttpServletRequest request){
        Object loginUserId = request.getSession().getAttribute("loginUserId");
        if(loginUserId != null)map.put("id", (String)loginUserId);
        else map.put("id", "none");
        return "passwordReset";
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    public String resetPassword(Map<String,Object> map, HttpServletRequest request){
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        doctorService.resetPassword(id, password);
        log.info("用户 " + id + "重置密码为 " + password);
        return "redirect:/signout";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toregister(){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String post = request.getParameter("post");
        Doctor doctor = doctorService.seletDoctorById(id);
        if(doctor != null){
            map.put("msg", "该用户已存在");
            return "register";
        }
        Doctor doctor_new = new Doctor();
        doctor_new.setId(id);
        doctor_new.setPassword(password);
        doctor_new.setName(name);
        doctor_new.setPost(post);
        doctor_new.setGender(gender);
        Integer res = doctorService.insertDoctor(doctor_new);
        log.info("用户 " + id + "注册成功");
        return "index";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String toProfile(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        map.put("loginUser", doctorService.seletDoctorById((String) request.getSession().getAttribute("loginUserId")));
        return "doctorProfile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String profile(Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){

        String name = request.getParameter("name").trim();
        String gender = request.getParameter("gender");
        String post = request.getParameter("post");
        Doctor doctor = new Doctor();
        doctor.setId((String)request.getSession().getAttribute("loginUserId"));
        doctor.setName(name);
        doctor.setGender(gender);
        doctor.setPost(post);
        boolean res = doctorService.updateDoctor(doctor);
        log.info("用户 " + (String)request.getSession().getAttribute("loginUserId") + " 修改个人信息为 " + doctor);
        return "redirect:/profile";
    }

}
