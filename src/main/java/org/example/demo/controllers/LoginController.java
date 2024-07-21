package org.example.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.demo.HelloApplication;
import org.example.demo.database.JDBCManager;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button buttonLogin;

    @FXML
    private Hyperlink link;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordFieldPassword;
    HelloApplication mainApp = null;

    @FXML
    void showSignup(ActionEvent event) throws IOException {
        if(this.mainApp != null) {
            this.mainApp.showSignupPage();
        }
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String email = textFieldEmail.getText();
        String password = passwordFieldPassword.getText();

        if(email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All fields required");
            alert.setHeaderText(null);
            alert.setContentText("You should enter all the fields before submitting the form.");

            alert.showAndWait();
            return;
        }

        boolean result = JDBCManager.login(email, password);

        if(result) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("You are successfully login");

            alert.showAndWait();
            if(this.mainApp != null) {
                this.mainApp.showHomePage();
            }
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Email or Password is incorrect.");

            alert.showAndWait();
            return;
        }
     }

     public void setMainApp(HelloApplication mainApp) {
        this.mainApp = mainApp;
     }
}
