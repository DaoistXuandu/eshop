package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository = new PaymentRepository();
    private Map<String, Order> orderMap = new HashMap<>();

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        // Create a new Payment object
        UUID paymentId = UUID.randomUUID();
        Payment newPayment = new Payment(paymentId.toString(), method, PaymentStatus.PENDING.getValue(), paymentData);
        Payment currentPayment = paymentRepository.save(newPayment);

        if(currentPayment.getStatus().equals(PaymentStatus.SUCCESS.getValue())) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
        }
        else{
            order.setStatus(OrderStatus.FAILED.getValue());
        }

        orderMap.put(currentPayment.getId(), order);

        return currentPayment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        if(status.equals(PaymentStatus.SUCCESS.getValue())) {
            orderMap.get(payment.getId()).setStatus(OrderStatus.SUCCESS.getValue());
        }
        else {
            orderMap.get(payment.getId()).setStatus(OrderStatus.FAILED.getValue());
        }
        return payment;  // Update the payment in the repository
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAll();
    }
}
