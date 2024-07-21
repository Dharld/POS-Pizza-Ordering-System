package org.example.demo.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.example.demo.HelloApplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class CartController implements Initializable {


    public class Product {
        String name;
        int price;
        int qty;
        String category;
        String imageSrc;

        public Product() {
        }

        public Product(String name, int price, int qty, String category) {
            this.name = name;
            this.price = price;
            this.qty = qty;
            this.category = category;
            this.imageSrc = HelloApplication.class.getResource("icons/samosa.png").toString();
        }

        public String getName() {
            return name;
        }

        public SimpleStringProperty nameProperty() {
            return new SimpleStringProperty(this.name);
        }

        public int getPrice() {
            return price;
        }
        public IntegerProperty priceProperty() {
            return new SimpleIntegerProperty(price);
        }
        public int getQty() {
            return qty;
        }

        public IntegerProperty qtyProperty() {
            return new SimpleIntegerProperty(qty);
        }
        public String getCategory() {
            return category;
        }

        public SimpleStringProperty categoryProperty() {
            return new SimpleStringProperty(category);
        }

        public String getImageSrc() {
            return this.imageSrc;
        }

        public SimpleStringProperty imageSrcProperty() {
            return new SimpleStringProperty(imageSrc);
        }
    }

    @FXML
    private TableView<Product> cartItems;

    TableColumn<Product, Image> productColumn = new TableColumn<>("Product");
    TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
    TableColumn<Product, Void> quantityColumn = new TableColumn<>("Quantity");

    public class ProductCell extends TableCell<Product, Void> {
        private final HBox hbox = new HBox();
        private final Button button = new Button("Click");

        public ProductCell() {

            hbox.getChildren().addAll(new Label("Label"), button);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            System.out.println(item);
            System.out.println(empty);
            super.updateItem(item, empty);
            if (item == null) {

                setGraphic(null);
            } else {
                setGraphic(hbox);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asString());

        productColumn.setCellFactory(column -> new TableCell<Product, Image>() {
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView(item);
                    imageView.setFitWidth(50);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });

        cartItems.getColumns().addAll(productColumn, priceColumn);

        List<Product> productList = new ArrayList<>();

        // Create Product instances and add them to the list
        productList.add(new Product("Product1", 100, 5, "Category1"));
        productList.add(new Product("Product2", 200, 10, "Category2"));
        productList.add(new Product("Product3", 150, 8, "Category1"));

        System.out.println(cartItems.getItems());
        cartItems.getItems().addAll(productList);
        System.out.println(cartItems.getItems());
    }

}
