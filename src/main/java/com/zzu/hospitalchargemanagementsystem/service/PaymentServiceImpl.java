package com.zzu.hospitalchargemanagementsystem.service;

import com.zzu.hospitalchargemanagementsystem.mapper.ConsultMapper;
import com.zzu.hospitalchargemanagementsystem.mapper.PaymentMapper;
import com.zzu.hospitalchargemanagementsystem.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ConsultMapper consultMapper;

    @Override
    public Integer insertPayment(Payment payment) {
        boolean res = consultMapper.updateConsultBalance(payment.getConsult(), -1 * payment.getAmount());
        return paymentMapper.insertPayment(payment);
    }

    @Override
    public void deletePayment(Integer id) {
        Payment payment = selectPaymentById(id);
        boolean res = consultMapper.updateConsultBalance(payment.getConsult(), payment.getAmount());
        paymentMapper.deletePayment(id);
    }

    public Payment selectPaymentById(Integer id){
        return paymentMapper.selectPaymentById(id);
    }

    @Override
    public List<Payment> selectPaymentByConsult(Integer consult) {
        List<Payment> payments = paymentMapper.selectPaymentByConsult(consult);
        return payments;
    }
}
