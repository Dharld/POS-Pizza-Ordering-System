package org.example.demo.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AppCard {
    private StringProperty appName = new SimpleStringProperty();
    private StringProperty appPrice = new SimpleStringProperty();
    private StringProperty appIcon = new SimpleStringProperty();

    public AppCard(String appName, String appPrice, String appIcon) {
        this.appName.set(appName);
        this.appPrice.set(appPrice);
        this.appIcon.set(appIcon);
    }

    public String getAppName() {
        return appName.get();
    }

    public StringProperty appNameProperty() {
        return appName;
    }

    public String getAppPrice() {
        return appPrice.get();
    }

    public StringProperty appPriceProperty() {
        return appPrice;
    }

    public String getAppIcon() {
        return appIcon.get();
    }

    public StringProperty appIconProperty() {
        return appIcon;
    }
}
