
package org.example.demo.utils;

import org.example.demo.models.CartItem;
import org.example.demo.models.Product;

import org.example.demo.utils.PaymentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderReceipt {
    private static int receiptCounter = 0;

    private final String receiptNumber;
    private final Date date;
    // private final List<Product> products;

    private final List<CartItem> items;

    private final PaymentService paymentService = PaymentService.getInstance();

    public OrderReceipt(List<CartItem> items) {
        this.receiptNumber = "#001" + String.valueOf(++receiptCounter);
        // this.products = products;
        this.items = items;
        date = new Date();
    }

    public String generateReceipt(String cardNumber) {
        StringBuilder bill = new StringBuilder();
        bill.append("-------------------------------\n");
        bill.append("Mom and Pops Pizza\n");
        bill.append("-------------------------------\n");
        bill.append("Receipt #: ").append(String.format("%s", receiptNumber)).append("\n");
        bill.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)).append("\n");
        bill.append("Item            Quantity       Price\n");
        bill.append("--------------------------------\n");

        double subtotal = 0;
        for (CartItem item : items) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            bill.append(String.format("%-15s   %s   $%.2f%n", p.getName(), qty, p.getPrice()));
            subtotal += p.getPrice() * qty;
        }

        double taxRate = 0.07;
        double tax = subtotal * taxRate;
        double total = subtotal + tax;

        bill.append("--------------------------------\n");
        bill.append(String.format("Subtotal:                   $%.2f%n", subtotal));
        bill.append(String.format("Tax (%.0f%%):                  +$%.2f%n", taxRate * 100, tax));
        bill.append(String.format("Total:                      $%.2f%n", total));
        bill.append(String.format("Payment Method: %s\n", paymentService.getPaymentType()));
        if(!cardNumber.isEmpty())
            bill.append("Card Number: **** **** **** ").append(getLastFourDigits(cardNumber)).append("\n");
        bill.append("Authorization Code: 987654\n");
        bill.append("--------------------------------------------\n");
        bill.append("Thank you for dining with us!\n");
        bill.append("--------------------------------------------\n");

        return bill.toString();
    }

    private String getLastFourDigits(String cardNumber) {
        if (cardNumber.isEmpty()) {
            return "";
        }
        return cardNumber.substring(cardNumber.length() - 4);
    }

}

