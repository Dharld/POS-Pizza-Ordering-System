package org.example.demo.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.demo.HelloApplication;
import org.example.demo.database.JDBCManager;
import org.example.demo.models.User;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    private Hyperlink link;

    @FXML
    private Button signupButton;

    @FXML
    private AnchorPane signupPane;

    @FXML
    private PasswordField textFieldConfirmPassword;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldFname;

    @FXML
    private TextField textFieldLname;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldPhone;

    HelloApplication mainApp;



    @FXML
    void showLogin(ActionEvent event) throws IOException {
        if(this.mainApp != null) {
            this.mainApp.showLoginPage();
        }
    }

    @FXML
    void signup(ActionEvent event) throws IOException {
        String fname = textFieldFname.getText();
        String lname = textFieldLname.getText();
        String phoneNumber = textFieldPhone.getText();
        String password = textFieldPassword.getText();
        String confirmPassword = textFieldConfirmPassword.getText();
        String email = textFieldEmail.getText();

        System.out.println(password);
        System.out.println(confirmPassword);

        if(fname.isEmpty() || lname.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            Alert  alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All fields required");
            alert.setHeaderText(null);
            alert.setContentText("You should enter all the fields before submitting the form.");

            alert.showAndWait();
            return;
        }

        if(confirmPassword.equals(password)) {
            User newUser = new User(fname, lname, email, phoneNumber);
            JDBCManager.signup(newUser, password);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Signup Successful");
            alert.setHeaderText(null);
            alert.setContentText(fname + " has been successfully registered to the system.");
            alert.showAndWait();
            this.showLogin(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Signup Error");
            alert.setHeaderText(null);
            alert.setContentText("The passwords don't match.");

            alert.showAndWait();
        }

    }

    public void setMainApp(HelloApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}