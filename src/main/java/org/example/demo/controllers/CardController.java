package org.example.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.demo.HelloApplication;
import org.example.demo.database.CartManager;
import org.example.demo.models.CartItem;
import org.example.demo.models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private ImageView ivIcon;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    private Product product;

    private StackPane sp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setProduct(Product p) {
        if(p != null) {
            this.product = p;
            this.lblName.setText(p.getName());
            this.lblPrice.setText("$" + p.getPrice());
            try {
                String imageUrl = String.valueOf(HelloApplication.class.getResource(p.getImageSrc()));
                this.ivIcon.setImage(new Image(imageUrl));
            } catch(IllegalArgumentException ex) {
                System.out.println("You passed an illegal argument");
            }
        }
    }


    @FXML
    void addToCart(ActionEvent event) throws IOException {
        CartItem c = new CartItem(this.product, 1);
        CartManager.addToCart(c);
    }

    @FXML
    void addTopping(ActionEvent event) throws IOException {
        showBuilder();
    }

    void setStackPane(StackPane sp) {
        this.sp = sp;
    }

    public void showBuilder() throws IOException {
        FXMLLoader pizzaBuilderLoader = new FXMLLoader(HelloApplication.class.getResource("views/pizza_builder.fxml"));
        Parent root = pizzaBuilderLoader.load();
        PizzaBuilderController pizzaBuilderController = pizzaBuilderLoader.getController();

        if(sp != null) {
            sp.getChildren().add(root);
        }
    }
}
