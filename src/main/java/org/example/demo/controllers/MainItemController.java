package org.example.demo.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.demo.HelloApplication;

import java.net.URL;
import java.util.ResourceBundle;

public class MainItemController implements Initializable {

    @FXML
    private ImageView ivIcon;

    @FXML
    private Label lblAppName;

    @FXML
    private Label lblEmail;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setAppInfo(String appName, String appEmail, String appIcon) {
        this.lblAppName.setText(appName);
        this.lblEmail.setText(appEmail);
        System.out.println(appIcon);
        try {
            String imageUrl = String.valueOf(HelloApplication.class.getResource(appIcon));
            this.ivIcon.setImage(new Image(imageUrl));
        } catch(IllegalArgumentException ex) {
            System.out.println("You passed an illegal argument");
        }

    }
}
