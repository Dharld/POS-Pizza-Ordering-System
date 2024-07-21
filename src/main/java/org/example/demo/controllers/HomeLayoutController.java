package org.example.demo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.demo.HelloApplication;
import org.example.demo.models.AppCard;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeLayoutController implements Initializable {

    @FXML
    private HBox popularsHBox;

    @FXML
    private VBox scrollbarContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        List<AppCard> apps = new ArrayList<>();

        apps.add(new AppCard("Chicken Mushroom", "$4.87", "icons/samosa.png"));
        apps.add(new AppCard("Chicken Mushroom", "$4.87", "icons/samosa.png"));
        apps.add(new AppCard("Chicken Mushroom", "$4.87", "icons/samosa.png"));
        apps.add(new AppCard("Chicken Mushroom", "$4.87", "icons/samosa.png"));
        apps.add(new AppCard("Chicken Mushroom", "$4.87", "icons/samosa.png"));

        try {
            Node[] nodes = new Node[apps.size()];

            for(int i = 0; i < nodes.length; i++) {

                AppCard currentApp = apps.get(i);

                final int h = i;

                // Loading everything
                FXMLLoader cardLoader = new FXMLLoader();
                cardLoader.setLocation(HelloApplication.class.getResource("views/card.fxml"));
                nodes[h] = cardLoader.load();

                CardController cardController = cardLoader.getController();
//                cardController.setProduct(c);
//                cardController.setCardInfo(
//                        currentApp.getAppName(),
//                        4.5f,
//                        currentApp.getAppIcon()
//                );


                popularsHBox.getChildren().add(nodes[h]);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
