package com.zzu.hospitalchargemanagementsystem.mapper;

import com.zzu.hospitalchargemanagementsystem.model.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorMapper {

    public List<Doctor> selectAllDoctor();

    public boolean resetPassword(@Param("id") String id, @Param("password")String password);

    public Doctor seletDoctorById(String id);

    public Integer insertDoctor(Doctor doctor);

    public boolean updateDoctor(Doctor doctor);

}
