package com.zzu.hospitalchargemanagementsystem.mapper;

import com.zzu.hospitalchargemanagementsystem.model.Consult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConsultMapper {

    public Integer insertConsult(Consult consult);

    public List<Consult> selectConsultByPatientId(String patientId);

    public Consult selectConsultById(int id);

    public void deleteConsultById(int id);

    public boolean updateConsultBalance(@Param("consultId") int consultId, @Param("cost") float cost);

    public boolean updateConsult(Consult consult);

}
