package id.ac.ui.cs.advprog.eshop.productService;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.service.OrderServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Order> orders;
    private Map<String, String> bank_info = new HashMap<>();
    private Map<String, String> voucher_info = new HashMap<>();

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);

        bank_info.put("bankName", "BANK A");
        bank_info.put("referenceCode", "1234202893");

        voucher_info.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentValid() {
        Payment newPayment = paymentService.addPayment(orders.get(0), PaymentMethod.BANK_TRANSFER.getValue(), bank_info);
        assertNotNull(newPayment);
        assertEquals(newPayment.getStatus(), PaymentStatus.SUCCESS.getValue());
    }

    @Test
    void testCreatePaymentInvalid() {
        Payment newPayment = paymentService.addPayment(orders.get(0), PaymentMethod.VOUCHER_CODE.getValue(), bank_info);
        assertNotNull(newPayment);
        assertEquals(newPayment.getStatus(), PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testFindPaymentById() {
        Payment newPayment = paymentService.addPayment(orders.get(0), PaymentMethod.BANK_TRANSFER.getValue(), bank_info);

        Payment findedPayment = paymentService.getPayment(newPayment.getId());
        assertNotNull(findedPayment);
        assertEquals(findedPayment.getId(), newPayment.getId());
    }

    @Test
    void testFindPaymentById() {
        Payment newPayment = paymentService.addPayment(orders.get(0), PaymentMethod.BANK_TRANSFER.getValue(), bank_info);
        Payment findedPayment = paymentService.getPayment("lbz");
        assertNull(findedPayment);
    }

    @Test
    void testFindPaymentById() {
        Payment newPayment1 = paymentService.addPayment(orders.get(0), PaymentMethod.BANK_TRANSFER.getValue(), bank_info);
        Payment newPayment2 = paymentService.addPayment(orders.get(1), PaymentMethod.BANK_TRANSFER.getValue(), bank_info);

        List<Payment> getAll = paymentService.getAllPayments();
        assertNotNull(getAll);
        assertEquals(2, getAll.size());
    }

    @Test
    void testSetStatusSuccessValid() {
        Payment newPayment = paymentService.addPayment(orders.get(0), PaymentMethod.VOUCHER_CODE.getValue(), bank_info);
        Payment findedPayment = paymentService.getPayment(newPayment.getId());
        assertEquals(findedPayment.getStatus(), PaymentStatus.REJECTED.getValue());
        assertEquals(orders.get(0).getStatus(), OrderStatus.FAILED.getValue());

        Payment current_payment = paymentService.setStatus(findedPayment, PaymentStatus.SUCCESS.getValue());
        Payment findedPayment2 = paymentService.getPayment(current_payment.getId());
        assertEquals(findedPayment2.getStatus(), PaymentStatus.SUCCESS.getValue());
        assertEquals(orders.get(0).getStatus(), OrderStatus.SUCCESS.getValue());
    }

    @Test
    void testSetStatusRejectedValid() {
        Payment newPayment = paymentService.addPayment(orders.get(0), PaymentMethod.BANK_TRANSFER.getValue(), bank_info);
        Payment findedPayment = paymentService.getPayment(newPayment.getId());
        assertEquals(findedPayment.getStatus(), PaymentStatus.SUCCESS.getValue());
        assertEquals(orders.get(0).getStatus(), OrderStatus.SUCCESS.getValue());

        Payment current_payment = paymentService.setStatus(findedPayment, PaymentStatus.REJECTED.getValue());
        Payment findedPayment2 = paymentService.getPayment(current_payment.getId());
        assertEquals(findedPayment2.getStatus(), PaymentStatus.REJECTED.getValue());
        assertEquals(orders.get(0).getStatus(), OrderStatus.FAILED.getValue());
    }
}