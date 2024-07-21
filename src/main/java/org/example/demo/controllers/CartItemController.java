package org.example.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.demo.HelloApplication;
import org.example.demo.database.CartManager;
import org.example.demo.models.CartItem;
import org.example.demo.models.Product;

import java.io.IOException;


public class CartItemController  {

    private CartItem cartItem;
    @FXML
    private ImageView ivSrc;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPrice;

    @FXML
    private Label labelQuantity;

    @FXML
    private VBox vboxCartContainer;

    @FXML
    void increaseQuantity(ActionEvent event) throws IOException {
        CartItem item = new CartItem(cartItem.getProduct(), 1);
        CartManager.addToCart(item);
    }

    @FXML
    void decreaseQuantity(ActionEvent event) throws IOException {
        CartManager.reduceItem(cartItem);
    }

    @FXML
    void deleteCartItem(ActionEvent event) throws IOException {
        CartManager.removeFromCart(cartItem);
    }

    public void setCartItem(CartItem cartItem) {
        System.out.println(cartItem);
        this.cartItem = cartItem;
        initialize(cartItem);
    }

    public void initialize(CartItem item) {

        Product p = this.cartItem.getProduct();
        int quantity = this.cartItem.getQuantity();

        // Image
        String imageUrl = String.valueOf(HelloApplication.class.getResource(p.getImageSrc()));
        ivSrc.setImage(new Image(imageUrl));

        // Name
        labelName.setText(p.toString());

        // Price
        float totalPrice = quantity * p.getPrice();
        labelPrice.setText("$" + totalPrice);

        // Quantity
        labelQuantity.setText(""+quantity);
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Product p = cartItem.getProduct();
//        int quantity = cartItem.getQuantity();
//
//        // Image
//        String imageUrl = String.valueOf(HelloApplication.class.getResource(p.getImageSrc()));
//        ivSrc.setImage(new Image(imageUrl));
//
//        // Name
//        labelName.setText(p.toString());
//
//        // Price
//        float totalPrice = quantity * p.getPrice();
//        labelPrice.setText("$" + totalPrice);
//
//        // Quantity
//        labelQuantity.setText(""+quantity);
//    }
}
