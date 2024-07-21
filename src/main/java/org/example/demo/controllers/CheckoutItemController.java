package org.example.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.example.demo.database.CartManager;
import org.example.demo.models.CartItem;
import org.example.demo.models.Product;

import java.io.IOException;

public class CheckoutItemController {
    @FXML
    private Label labelName;

    @FXML
    private Label labelQuantity;

    @FXML
    private Label labelTotalPrice;

    private CartItem cartItem;

    @FXML
    void deleteCartItem(MouseEvent event) throws IOException {
        CartManager.removeFromCart(cartItem);
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
        Product p = this.cartItem.getProduct();
        int quantity = this.cartItem.getQuantity();
        float totalPrice = quantity * p.getPrice();

        this.labelName.setText(p.toString());
        this.labelQuantity.setText("" + this.cartItem.getQuantity());
        this.labelTotalPrice.setText("$" + totalPrice);
    }

    @FXML
    void increaseQuantity(ActionEvent event) throws IOException {
        CartItem item = new CartItem(cartItem.getProduct(), 1);
        CartManager.addToCart(item);
    }

    @FXML
    void decreaseQuantity(ActionEvent event) throws IOException {
        CartManager.reduceItem(cartItem);
    }

}
