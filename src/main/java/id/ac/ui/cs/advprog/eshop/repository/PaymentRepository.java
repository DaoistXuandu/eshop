package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;
import id.ac.ui.cs.advprog.eshop.model.Order;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private List<Payment> payments = new ArrayList<>();

    public boolean verify(String voucher) {
        if(voucher.length() != 16)
            return false;

        String first_char = voucher.substring(0, 5);
        if(!first_char.equals("ESHOP"))
            return false;

        int cnt = 0;
        for(int i = 0; i < 16; i++){
            if(voucher.charAt(i) >= '0' && voucher.charAt(i) <= '9')
                cnt++;
        }

        if(cnt != 8)
            return false;

        return true;
    }

    public Payment save(Payment payment) {
        int i = 0;
        for (Payment paymentData : payments) {
            if (paymentData.getId().equals(payment.getId())) {
                throw new IllegalArgumentException("Payment already exists");
            }
            i += 1;
        }

        if(payment.getMethod().equals(PaymentMethod.BANK_TRANSFER.getValue())){
            if(payment.getPaymentData().containsKey("bankName")
                    && payment.getPaymentData().containsKey("referenceCode")
                    && payment.getPaymentData().get("bankName") != null
                    && payment.getPaymentData().get("referenceCode") != null
            ){
                payment.setStatus(PaymentStatus.SUCCESS.getValue());
            }
            else{
                payment.setStatus(PaymentStatus.REJECTED.getValue());
            }
        }
        else if(payment.getMethod().equals(PaymentMethod.VOUCHER_CODE.getValue())){
            if(payment.getPaymentData().containsKey("voucherCode")
                    && payment.getPaymentData().get("voucherCode") != null
                    && verify(payment.getPaymentData().get("voucherCode"))
            ){
                payment.setStatus(PaymentStatus.SUCCESS.getValue());
            }
            else {
                payment.setStatus(PaymentStatus.REJECTED.getValue());
            }
        }


        payments.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment savedPayment : payments) {
            if (savedPayment.getId().equals(id)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> getAll() {
        return payments;
    }
}