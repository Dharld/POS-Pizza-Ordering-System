package org.example.demo.models;

import java.util.Date;

public class Order {
    private int orderId;
    private String orderStatus;
    private Date orderDate;

    public Order(String orderStatus, Date orderDate) {
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

}
