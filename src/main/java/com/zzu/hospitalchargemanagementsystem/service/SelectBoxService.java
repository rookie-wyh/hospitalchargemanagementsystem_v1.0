package com.zzu.hospitalchargemanagementsystem.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelectBoxService {

    // 病种
    public Integer insertDiseases(String diseases);

    public List<String> selectDiseases();

    // 治疗项目
    public Integer insertProject(String project);

    public List<String> selectProject();


}
