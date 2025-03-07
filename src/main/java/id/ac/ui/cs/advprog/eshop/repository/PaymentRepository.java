package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;
import id.ac.ui.cs.advprog.eshop.model.Order;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private List<Payment> payments = new ArrayList<>();
    public Payment save(Payment payment) {
        int i = 0;
        for (Payment paymentData : payments) {
            System.out.println("TT" + paymentData.getId() + " " + payment.getId());
            if (paymentData.getId().equals(payment.getId())) {
                payments.remove(i);
                payments.add(i, payment);
                return payment;
            }
            i += 1;
        }

        payments.add(payment);
        System.out.println("Masuk sini" + payments.size());
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
}