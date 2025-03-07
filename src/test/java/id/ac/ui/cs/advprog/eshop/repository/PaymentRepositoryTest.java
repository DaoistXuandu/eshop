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

        payments.add(payment1);
    }

    @Test
    void testSaveInvalidId() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);
        assertThrows(IllegalArgumentException.class, () -> paymentRepository.save(payment));
    }

    @Test
    void testSaveValidBank() {
        Map <String, String> currentBank = new HashMap<>();
        currentBank.put("bankName", "BANK A");
        currentBank.put("referenceCode", "1234202893");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentBank
                );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertSame(currentBank, findResult.getPaymentData());
        assertEquals("BANK A", findResult.getPaymentData().get("bankName"));
        assertEquals("1234202893", findResult.getPaymentData().get("referenceCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveInvalidBankBankName() {
        Map <String, String> currentBank = new HashMap<>();
        currentBank.put("referenceCode", "1234202893");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentBank
        );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertSame(currentBank, findResult.getPaymentData());
        assertEquals("1234202893", findResult.getPaymentData().get("referenceCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveInvalidBankReferenceCode() {
        Map <String, String> currentBank = new HashMap<>();
        currentBank.put("bankName", "BANK A");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentBank
        );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertSame(currentBank, findResult.getPaymentData());
        assertEquals("BANK A", findResult.getPaymentData().get("bankName"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveValidVoucher() {
        Map <String, String> currentVoucher = new HashMap<>();
        currentVoucher.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentVoucher
        );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");

        assertSame(currentVoucher, findResult.getPaymentData());
        assertEquals("ESHOP1234ABC5678", findResult.getPaymentData().get("voucherCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveInvalidVoucherNoData() {
        Map <String, String> currentVoucher = new HashMap<>();
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentVoucher
        );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());

        currentVoucher.put("voucherCode", null);
        Payment payment1 = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79a",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentVoucher
        );

        paymentRepository.save(payment1);
        Payment findResult1 = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79a");

        assertEquals("13652556-012a-4c07-b546-54eb1396d79a", findResult1.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), findResult1.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult1.getStatus());
    }

    @Test
    void testSaveInvalidVoucherLengthError() {
        Map <String, String> currentVoucher = new HashMap<>();
        String voucher_name = "ESHOP1234ABC567801010";

        currentVoucher.put("voucherCode", voucher_name);
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentVoucher
        );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");

        assertSame(currentVoucher, findResult.getPaymentData());
        assertEquals(voucher_name, findResult.getPaymentData().get("voucherCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveInvalidVoucherESHOPInit() {
        Map <String, String> currentVoucher = new HashMap<>();
        String voucher_name = "1234ABC567801010";

        currentVoucher.put("voucherCode", voucher_name);
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentVoucher
        );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");

        assertSame(currentVoucher, findResult.getPaymentData());
        assertEquals(voucher_name, findResult.getPaymentData().get("voucherCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveInvalidVoucherLimitNumericalDigit() {
        Map <String, String> currentVoucher = new HashMap<>();
        String voucher_name = "ESHOP1234ABC567A";

        currentVoucher.put("voucherCode", voucher_name);
        Payment payment = new Payment(
                "13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.PENDING.getValue(),
                currentVoucher
        );

        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");

        assertSame(currentVoucher, findResult.getPaymentData());
        assertEquals(voucher_name, findResult.getPaymentData().get("voucherCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", findResult.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), findResult.getMethod());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
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

    @Test
    void testGetAllExistValue() {
        paymentRepository.save(payments.get(0));
        paymentRepository.save(payments.get(1));
        List<Payment> allPayment = paymentRepository.getAll();
        System.out.println(allPayment.size());
        assertEquals(2, allPayment.size());
    }

    @Test
    void testGetAllExistWithZero() {
        PaymentRepository zero_value = new PaymentRepository();
        List<Payment> allPayment = zero_value.getAll();
        assertEquals(0, allPayment.size());
    }

}