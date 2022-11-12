package com.zzu.hospitalchargemanagementsystem.mapper;

import com.zzu.hospitalchargemanagementsystem.model.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {

    public Integer insertPayment(Payment payment);

    public void deletePayment(Integer id);

    public Payment selectPaymentById(Integer id);

    public List<Payment> selectPaymentByConsult(@Param("consult") Integer consult);

}
