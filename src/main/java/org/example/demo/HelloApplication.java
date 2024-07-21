package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.demo.controllers.HomeController;
import org.example.demo.controllers.LoginController;
import org.example.demo.controllers.SignupController;
import org.example.demo.database.JDBCManager;

import java.io.IOException;

public class HelloApplication extends Application {
    double x = 0, y = 0;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        JDBCManager.initialize();

        // Load the signup view
        FXMLLoader signupLoader = new FXMLLoader(HelloApplication.class.getResource("views/signup.fxml"));
        Parent root = signupLoader.load();
        SignupController signupController = signupLoader.getController();
        signupController.setMainApp(this);

        Scene scene = new Scene(root);

        String imageUrl = String.valueOf(HelloApplication.class.getResource("icons/logo.jpeg"));

        stage.setTitle("Mom's and Pop Pizza");
        stage.getIcons().add(new Image(imageUrl));
        stage.setScene(scene);
        // stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void showLoginPage() throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("views/login.fxml"));
        Parent loginRoot = loginLoader.load();

        LoginController loginController = loginLoader.getController();

        // Pass the app to the login page;
        loginController.setMainApp(this);

        if(this.primaryStage != null) {
            this.primaryStage.setScene(new Scene(loginRoot));
            this.primaryStage.show();
        }
    }

    public void showSignupPage() throws IOException {



        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("views/signup.fxml"));
        Parent signupRoot = loginLoader.load();
        SignupController signupController = loginLoader.getController();

        // Pass the app to the signup page;
        signupController.setMainApp(this);

        if(this.primaryStage != null) {
            this.primaryStage.setScene(new Scene(signupRoot));
            this.primaryStage.show();
        }
    }

    public void showHomePage() throws IOException {
        FXMLLoader homeLoader = new FXMLLoader(HelloApplication.class.getResource("views/home.fxml"));
        Parent homeRoot = homeLoader.load();
        HomeController homeController = homeLoader.getController();
        homeController.setMainApp(this);

        if(this.primaryStage != null) {
            this.primaryStage.setScene(new Scene(homeRoot));
            this.primaryStage.show();
        }
    }

}