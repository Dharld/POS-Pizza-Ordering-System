package org.example.demo.models;

import java.util.Objects;

public class CartItem {
    private Product p;
    private int quantity;

    public CartItem(Product p, int quantity) {
        this.p = p;
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        if(quantity > 0)
            quantity--;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem that = (CartItem) o;
        return (this.p.toString()).equals(that.p.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }

    @Override
    public String toString() {
        return p.toString() + " - " + quantity;
    }
}
