

package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    private Map<String, String> bank_info = new HashMap<>();
    private Map<String, String> voucher_info = new HashMap<>();

    @BeforeEach
    void setUp() {
        bank_info.put("bankName", "BANK A");
        bank_info.put("referenceCode", "1234202893");

        voucher_info.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        this.bank_info = null;

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), bank_info);
        });
    }

    @Test
    void testCreatePaymentMethodBankStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), bank_info);
        assertSame(this.bank_info, payment.getPaymentData());
        assertEquals("BANK A", payment.getPaymentData().get("bankName"));
        assertEquals("1234202893", payment.getPaymentData().get("referenceCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentMethodVoucherStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER_CODE.getValue(), PaymentStatus.SUCCESS.getValue(), voucher_info);
        assertSame(this.voucher_info, payment.getPaymentData());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));

        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentMethodInvalidStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "MEOW", PaymentStatus.SUCCESS.getValue(), voucher_info);
        assertThrows(IllegalArgumentException.class, () -> new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "MEOW", PaymentStatus.SUCCESS.getValue(), voucher_info));
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.SUCCESS.getValue(), bank_info);
        assertEquals(PaymentStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentRejectedStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(), PaymentStatus.REJECTED.getValue(), bank_info);
        assertEquals(PaymentStatus.REJECTED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(), "MEOW", bank_info);
        assertThrows(IllegalArgumentException.class, () -> new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BANK_TRANSFER.getValue(), "MEOW", bank_info));
    }

}
