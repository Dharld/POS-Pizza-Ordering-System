package org.example.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.example.demo.HelloApplication;
import org.example.demo.database.CartManager;
import org.example.demo.database.JDBCManager;

import java.io.IOException;

public class HomeController  {

    @FXML
    private AnchorPane outlet;

    HelloApplication mainApp;

    @FXML
    private HBox popularsHBox;

    public void initialize() {
        JDBCManager.initialize();
        showMenu();
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        JDBC.initialize();
//        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/menu.fxml"));
//
//        try {
//            Parent root = loader.load();
//            MenuController menuController = loader.getController();
//            System.out.println(this.mainApp);
//            menuController.setMainApp(this.mainApp);
//            outlet.getChildren().add(root);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @FXML
    void goToMenu(MouseEvent event) {
        showMenu();
    }

    @FXML
    void goToBuilder(MouseEvent event) {
        showBuilder();
    }

    public void showMenu() {

        outlet.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/menu.fxml"));

        try {
            Parent root = loader.load();
            MenuController menuController = loader.getController();
            menuController.setHomeController(this);

            outlet.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showBuilder() {
        outlet.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/pizza_builder.fxml"));

        try {
            Parent root = loader.load();
            outlet.getChildren().add(root);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCheckout() {
        outlet.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/checkout.fxml"));

        try {
            Parent root = loader.load();
            outlet.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMainApp(HelloApplication mainApp) {
        this.mainApp = mainApp;
        this.initialize();
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        if(this.mainApp != null) {
            CartManager.clearCart();
            this.mainApp.showLoginPage();
        }
    }
}
