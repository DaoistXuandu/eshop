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

        if(paymentData == null) {
            throw new IllegalArgumentException();
        }
        else {
            this.paymentData = paymentData;
        }

        if(PaymentStatus.contains(status)) {
            this.status = status;
        }
        else {
            throw new IllegalArgumentException("Status is not valid");
        }

        if(PaymentMethod.contains(method)) {
            this.method = method;
        }
        else{
            throw new IllegalArgumentException("Method is not valid");
        }
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