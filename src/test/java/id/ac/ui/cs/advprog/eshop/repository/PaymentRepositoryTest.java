package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private Map<String, String> bank_info = new HashMap<>();
    private Map<String, String> voucher_info = new HashMap<>();

    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        bank_info.put("bankName", "BANK A");
        bank_info.put("referenceCode", "1234202893");

        voucher_info.put("voucherCode", "ESHOP1234ABC5678");
        paymentRepository = new PaymentRepository();

        payments = new ArrayList<>();
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), bank_info);

        payments.add(payment);

        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79c",
                PaymentMethod.VOUCHER_CODE.getValue(), PaymentStatus.SUCCESS.getValue(), voucher_info);

        payments.add(payment);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payments.get(0).getId());
        assertSame(this.bank_info, payment.getPaymentData());
        assertEquals("BANK A", payment.getPaymentData().get("bankName"));
        assertEquals("1234202893", payment.getPaymentData().get("referenceCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);
        Payment newPayment = new Payment(payment.getId(),
                payment.getMethod(),
                PaymentStatus.REJECTED.getValue(),
                payment.getPaymentData());

        Payment findResult = paymentRepository.findById(payments.get(0).getId());
        assertSame(this.bank_info, payment.getPaymentData());
        assertEquals("BANK A", payment.getPaymentData().get("bankName"));
        assertEquals("1234202893", payment.getPaymentData().get("referenceCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
        assertEquals(payments.get(1).getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("zzz");
        assertNull(findResult);
    }
}