package org.example.demo.utils;

public class PaymentService {

    private static PaymentService instance;
    private String paymentType;

    public PaymentService(String paymentType) {
        this.paymentType = paymentType;
    }



    public static PaymentService getInstance() {
        if (instance == null) {
            instance =  new PaymentService("CASH");
        }
        return instance;
    }

    public static void setInstance(PaymentService instance) {
        PaymentService.instance = instance;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        getInstance().paymentType = paymentType;
    }
}
