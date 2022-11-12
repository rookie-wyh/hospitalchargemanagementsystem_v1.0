package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.model.Payment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentService {
    public Integer insertPayment(Payment payment);

    public void deletePayment(Integer id);

    public Payment selectPaymentById(Integer id);

    public List<Payment> selectPaymentByConsult(Integer consult);
}
