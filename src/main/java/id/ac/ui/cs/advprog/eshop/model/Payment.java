package id.ac.ui.cs.advprog.eshop.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;

        if(paymentData == null || !PaymentStatus.contains(status) || !PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid data binding");
        }

        this.paymentData = paymentData;
        this.status = status;
        this.method = method;
    }

    public void setStatus(String status) {
        if(PaymentStatus.contains(status)) {
            this.status = status;
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}