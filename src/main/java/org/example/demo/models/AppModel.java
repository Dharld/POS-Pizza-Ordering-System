package org.example.demo.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppModel {
    private StringProperty appName = new SimpleStringProperty();
    private StringProperty appEmail = new SimpleStringProperty();
    private StringProperty appIcon = new SimpleStringProperty();


    public AppModel(String appName, String appEmail, String appIcon) {
        this.appName.set(appName);
        this.appEmail.set(appEmail);
        this.appIcon.set(appIcon);
    }

    public String getAppName() {
        return appName.get();
    }

    public StringProperty appNameProperty() {
        return appName;
    }

    public String getAppEmail() {
        return appEmail.get();
    }

    public StringProperty appEmailProperty() {
        return appEmail;
    }

    public String getAppIcon() {
        return appIcon.get();
    }

    public StringProperty appIconProperty() {
        return appIcon;
    }
}
