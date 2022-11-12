package com.zzu.hospitalchargemanagementsystem.mapper;

import com.zzu.hospitalchargemanagementsystem.model.Treatment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TreatmentMapper {

    public Integer insertTreatment(Treatment treatment);

    public void deleteTreatmentById(int id);

    public boolean updateTreatment(Treatment treatment);

    public List<Treatment> selectTreatmentByConsultId(int consultId);

    public Treatment selectTreatmentById(int id);

}
