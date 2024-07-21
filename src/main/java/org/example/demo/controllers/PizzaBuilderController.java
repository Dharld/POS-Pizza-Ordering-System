package org.example.demo.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleFloatProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.demo.database.CartManager;
import org.example.demo.models.CartItem;
import org.example.demo.models.Option;
import org.example.demo.models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class PizzaBuilderController implements Initializable {

    @FXML
    private ToggleGroup cheese;

    @FXML
    private ToggleGroup crust;


    @FXML
    private ToggleGroup size;

    @FXML
    private TextField textFieldName;

    @FXML
    private Label labelPizzaPrice;

    private SimpleFloatProperty pizzaPriceProperty = new SimpleFloatProperty();

    @FXML
    private VBox vboxToppings;


    @FXML
    void addToCart(ActionEvent event) throws CloneNotSupportedException, IOException {
            // Crust
            RadioButton selectedCrust = (RadioButton) crust.getSelectedToggle();
            String crustText = "";
            if(selectedCrust != null) {
                crustText = selectedCrust.getText();
            }

            // Size
            RadioButton selectedSize = (RadioButton) size.getSelectedToggle();
            String sizeText = "";
            if(selectedSize != null) {
                sizeText = selectedSize.getText();
            }

            // Cheese
            RadioButton selectedCheese = (RadioButton) cheese.getSelectedToggle();
            String cheeseText = selectedCheese.getText();
            System.out.println(cheeseText);

            // Toppings
            // Initialize a StringBuilder to store the selected toppings
            StringBuilder selectedToppings = new StringBuilder();

            // Iterate over each CheckBox in vboxToppings
            int numberToppings = 0;
            for (Node node : vboxToppings.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    // If the CheckBox is selected, append its text to the selectedToppings
                    if (checkBox.isSelected()) {
                        selectedToppings.append(checkBox.getText()).append(", ");
                        numberToppings++;
                    }
                }
            }

            if(numberToppings > 4) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You can't add more than four toppings");

                alert.showAndWait();
                return;
            }

            // Remove the trailing comma and space
            if (selectedToppings.length() > 0) {
                selectedToppings.delete(selectedToppings.length() - 2, selectedToppings.length());
            }

            // Now you can use selectedToppings as needed
            String toppings = selectedToppings.toString();

            String pizzaName = textFieldName.getText();
            String defaultSrc = "images/default.jpg";

            // Let's use all the gathered values
            Option crustOption = new Option("crust", crustText, -1);
            Option sizeOption = new Option("size", sizeText, -1);
            Option toppingsOption = new Option("toppings", toppings, -1);
            Option cheeseOption = new Option("cheese", cheeseText, -1);

            Product newProduct = new Product(-1, pizzaName, "", pizzaPriceProperty.get(), defaultSrc);

            // Add Options
            if(!crustText.isEmpty()) {
                newProduct.addOption(crustOption);
            }
            if(!sizeText.isEmpty()) {
                newProduct.addOption(sizeOption);
            }
            if(!toppings.isEmpty()) {
                newProduct.addOption(toppingsOption);
            }
            if(!cheeseText.equals("None")) {
                newProduct.addOption(cheeseOption);
            }

            CartItem c = new CartItem(newProduct, 1);
            CartManager.addToCart(c);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("The " + pizzaName + " has been successfully added to the cart.");

            alert.showAndWait();

            pizzaPriceProperty.set(0);
            textFieldName.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pizzaPriceProperty.set(10.0F);
        labelPizzaPrice.textProperty().bind(Bindings.concat("$", Bindings.convert(pizzaPriceProperty)));

        AtomicReference<Float> increment = new AtomicReference<>(0.0f);
        size.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            float prevIncrement = increment.get();

            String selectedCrust = ((RadioButton) newValue).getText();

            switch (selectedCrust) {
                case "Small":
                    increment.set(0.25f);
                    break;
                case "Medium":
                    increment.set(0.5f);
                    break;
                case "Large":
                    increment.set(0.75f);
                    break;
                case "Extra Large":
                    increment.set(1.00f);
                    break;
            }

            if(oldValue == null) {
                pizzaPriceProperty.set(pizzaPriceProperty.get() + increment.get());
            }
            else {
                pizzaPriceProperty.set(pizzaPriceProperty.get() - prevIncrement + increment.get());
            }
        });
    }
}
