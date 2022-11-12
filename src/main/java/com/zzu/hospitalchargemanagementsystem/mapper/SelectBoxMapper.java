package com.zzu.hospitalchargemanagementsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SelectBoxMapper {

    // 病种
    public Integer insertDiseases(@Param("diseases") String diseases);

    public List<String> selectDiseases();

    // 治疗项目
    public Integer insertProject(@Param("project") String project);

    public List<String> selectProject();

}
