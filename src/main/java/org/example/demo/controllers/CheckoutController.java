package org.example.demo.controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.demo.HelloApplication;
import org.example.demo.database.CartManager;
import org.example.demo.database.JDBCManager;
import org.example.demo.database.UserManager;
import org.example.demo.models.Address;
import org.example.demo.models.CartItem;
import org.example.demo.models.User;
import org.example.demo.utils.Mail;
import org.example.demo.utils.OrderReceipt;
import org.example.demo.utils.PaymentService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CheckoutController implements Initializable {
    @FXML
    private Button buttonConfirm;

    @FXML
    private ToggleGroup paymentMean;

    @FXML
    private Label priceDue;

    @FXML
    private RadioButton radioButtonCard;

    @FXML
    private RadioButton radioButtonCash;

    @FXML
    private RadioButton radioButtonCheque;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane stackPaneAddress;

    @FXML
    private StackPane stackPanePayment;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldCardNumber;

    @FXML
    private TextField textFieldCity;

    @FXML
    private TextField textFieldExpMonth;

    @FXML
    private TextField textFieldExpYear;

    @FXML
    private TextField textFieldSecurityCode;

    @FXML
    private TextField textFieldSignature;

    @FXML
    private TextField textFieldState;

    @FXML
    private TextField textFieldZip;

    @FXML
    private TextField textFieldZipCode;

    @FXML
    private Label totalPrice;

    @FXML
    private VBox vboxCartItems;

    PaymentService paymentService = PaymentService.getInstance();

    @FXML
    void buttonPlaceOrder(ActionEvent event) {
        openPopupAddress();
    }

    public void showCartItems() throws IOException {

        ArrayList<CartItem> cartItems = CartManager.getItems();

        vboxCartItems.getChildren().clear();

        Node[] nodes = new Node[cartItems.size()];

        for(int i = 0; i < cartItems.size(); i++) {

            CartItem cartItem = cartItems.get(i);

            final int h = i;

            // Loading everything
            FXMLLoader checkoutItemLoader = new FXMLLoader();
            checkoutItemLoader.setLocation(HelloApplication.class.getResource("views/checkout_item.fxml"));
            nodes[h] = checkoutItemLoader.load();

            CheckoutItemController checkoutItemController = checkoutItemLoader.getController();
            checkoutItemController.setCartItem(cartItem);

//            cardController.setProduct(currProduct);
//            cardController.setStackPane(stackPane);

            vboxCartItems.getChildren().add(nodes[h]);
        }
    }

    void openPopupAddress() {
        stackPaneAddress.setOpacity(1);
        stackPaneAddress.setDisable(false);
    }

    void closePopupAddress() {
        stackPaneAddress.setOpacity(0);
        stackPaneAddress.setDisable(true);
    }

    void openPopupPayment() {
        stackPanePayment.setOpacity(1);
        stackPanePayment.setDisable(false);
    }

    void closePopupPayment() {
        stackPanePayment.setOpacity(0);
        stackPanePayment.setDisable(true);
    }

    @FXML
    void closePopup(MouseEvent event) {
        stackPaneAddress.setOpacity(0);
        stackPaneAddress.setDisable(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showCartItems();
            CartManager.setCheckoutController(this);
            totalPrice.textProperty().bind(Bindings.concat("$", Bindings.convert(CartManager.totalPriceProperty())));
            priceDue.textProperty().bind(Bindings.concat("$", Bindings.convert(CartManager.totalPriceProperty())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addAddress(ActionEvent event) {
        String street = textFieldAddress.getText();
        String city = textFieldCity.getText();
        String zip = textFieldZip.getText();
        String state = textFieldState.getText();
        if(street.isEmpty() || city.isEmpty() || zip.isEmpty() || state.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All fields required");
            alert.setHeaderText(null);
            alert.setContentText("You should enter all the fields before submitting the form.");

            alert.showAndWait();
            return;
        } else {
            User currentUser = UserManager.getCurrentUser();
            int currentUserId = currentUser.getId();
            if(currentUserId != -1) {
                Address address = new Address(street, city, zip, state);
                JDBCManager.createAddressForUser(currentUserId, address);
                UserManager.setAddress(address);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Your address has been successfully added");

                alert.showAndWait();

                closePopupAddress();
                openPopupPayment();
            }
        }
    }

    public void confirmPayment(ActionEvent event) throws IOException {
        RadioButton paymentSelected = (RadioButton) paymentMean.getSelectedToggle();
        String textSelected = paymentSelected.getText();
        OrderReceipt orderReceiptInstance = new OrderReceipt(CartManager.getItems());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String billString = "";
        String email = UserManager.getCurrentUser().getEmail();

        switch (textSelected) {
            case "Pay With Cash At Delivery":
                paymentService.setPaymentType("CASH");
                billString = orderReceiptInstance.generateReceipt("123456789");

                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(billString);

                alert.showAndWait();
                break;

            case "Pay With Credit/Debit Card":
                paymentService.setPaymentType("Credit/Debit Card");

                String cardNumber = textFieldCardNumber.getText();
                String expMonth = textFieldExpMonth.getText();
                String expYear = textFieldExpYear.getText();
                String securityCode = textFieldSecurityCode.getText();
                String billingCode = textFieldZipCode.getText();
                String signature = textFieldSignature.getText();

                if(cardNumber.isEmpty() || expMonth.isEmpty() || expYear.isEmpty() ||  securityCode.isEmpty() || billingCode.isEmpty() || signature.isEmpty()) {
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("All fields are required.");

                    errorAlert.showAndWait();
                    return;
                } else {

                    billString = orderReceiptInstance.generateReceipt(cardNumber);

                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText(billString);

                    alert.showAndWait();
                    break;
                }
        }

        closePopupPayment();

        System.out.println(email);

        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);

        alertConfirmation.setTitle("Confirmation Payment");
        alertConfirmation.setHeaderText(null);
        alertConfirmation.setContentText("Your order has been placed successfully ! Check your email for notification.");

        textFieldAddress.setText("");
        textFieldCity.setText("");
        textFieldCardNumber.setText("");
        textFieldExpMonth.setText("");
        textFieldExpYear.setText("");
        textFieldSecurityCode.setText("");
        textFieldSignature.setText("");
        textFieldState.setText("");
        textFieldZip.setText("");
        textFieldZip.setText("");

        CartManager.clearCart();
        showCartItems();

        alertConfirmation.showAndWait();

        Mail.sendEmail(email, "Order Confirmation", billString);
    }
}
