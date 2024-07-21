package org.example.demo.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.demo.HelloApplication;
import org.example.demo.models.AppModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnAll;

    @FXML
    private Button btnCard;

    @FXML
    private Button btnFavorite;

    @FXML
    private Button btnIdentity;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnPersonal;

    @FXML
    private Button btnSecure;

    @FXML
    private Button btnSocial;

    @FXML
    private Button btnTrash;

    @FXML
    private Button btnWork;

    @FXML
    private ImageView ivLogo;

    @FXML
    private Label lblCompanyName;

    @FXML
    private Label lblWebsite;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    private VBox vItems;

    @FXML
    void handleClicks(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<AppModel> apps = new ArrayList<>();
        apps.add(new AppModel("Google", "mailto.google.com", "icons/google.png"));
        apps.add(new AppModel("Pinterest", "mailto.pinterest.com", "icons/pinterest.png"));
        apps.add(new AppModel("Adobe", "mailto.adobe.com", "icons/adobe.png"));
        apps.add(new AppModel("Whatsapp", "mailto.whatsapp.com", "icons/whatsapp.png"));
        apps.add(new AppModel("Twitter", "mailto.twitter.com", "icons/twitter.png"));

        try {
            Node[] nodes = new Node[5];

            for(int i = 0; i < nodes.length; i++) {

                AppModel currentApp = apps.get(i);

                final int h = i;

                // Loading everything
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(HelloApplication.class.getResource("views/mainitem.fxml"));
                nodes[h] = loader.load();

                MainItemController mainItemController = loader.getController();

                mainItemController.setAppInfo(
                        currentApp.getAppName(),
                        currentApp.getAppEmail(),
                        currentApp.getAppIcon()
                );

                HBox.setHgrow(nodes[h], Priority.ALWAYS);

                nodes[h].setOnMouseEntered(evt -> {
                    nodes[h].setStyle("-fx-background-color: #165ddb");
                });

                nodes[h].setOnMouseExited(evt -> {
                    nodes[h].setStyle("-fx-background-color: #1e1e1e");
                });

                vItems.getChildren().add(nodes[h]);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
