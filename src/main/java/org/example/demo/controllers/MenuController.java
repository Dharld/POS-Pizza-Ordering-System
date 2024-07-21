package org.example.demo.controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.demo.HelloApplication;
import org.example.demo.database.CartManager;
import org.example.demo.database.JDBCManager;
import org.example.demo.models.CartItem;
import org.example.demo.models.Category;
import org.example.demo.models.Product;

import java.io.IOException;
import java.util.ArrayList;

public class MenuController  {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox parentContainer;

    @FXML
    private VBox vboxCategories;

    @FXML
    private TilePane menuContainer;

    private ArrayList<Category> categories;

    private Category activeCategory;

    private ArrayList<Product> products;

    @FXML
    private ProgressIndicator loader;

    @FXML
    private Label cartItemCount;

    @FXML
    private VBox vboxCart;

    @FXML
    private VBox vboxCartItems;

    private boolean cartToggled = false;

    private HelloApplication mainApp;

    private HomeController homeController;

    public void initialize() {

        vboxCategories.getChildren().clear();

        try {
            // Bind the cartItem Count to the number of Items
            cartItemCount.textProperty().bind(Bindings.convert(CartManager.totalItemsProperty()));

            JDBCManager.initialize();
            this.categories = JDBCManager.getCategories();
            this.activeCategory = categories.get(0);
            this.products = JDBCManager.getAllProductsOfCategory(this.activeCategory.getId());
            showProducts();

            for (Category curr : this.categories) {
                Insets chipPadding = new Insets(8,12,8,12);
                String chipStylesheet = HelloApplication.class.getResource("css/menu.css").toExternalForm();

                // Constructing the chip
                Button chip = new Button(curr.getName());

                chip.setOnAction(e -> {
                    loader.setVisible(true);
                    products.clear();
                    products = JDBCManager.getAllProductsOfCategory(curr.getId());

                    vboxCategories.getChildren().forEach((Node c) -> {
                        c.getStyleClass().remove("active");
                    });

                    chip.getStyleClass().add("active");

                    try {
                        showProducts();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    loader.setVisible(false);
                });

                chip.getStylesheets().add(chipStylesheet);
                chip.getStyleClass().add("chip");

                chip.setPadding(chipPadding);

                // Add to parent
                vboxCategories.setSpacing(8.0);
                vboxCategories.getChildren().add(chip);

                CartManager.setMenuController(this);
            }

            updateCartItems();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   public void showProducts() throws IOException {

        menuContainer.getChildren().clear();
       Node[] nodes = new Node[products.size()];

       for(int i = 0; i < products.size(); i++) {

           Product currProduct = products.get(i);

           final int h = i;

           // Loading everything
           FXMLLoader cardLoader = new FXMLLoader();
           cardLoader.setLocation(HelloApplication.class.getResource("views/card.fxml"));
           nodes[h] = cardLoader.load();

           CardController cardController = cardLoader.getController();

           cardController.setProduct(currProduct);
           cardController.setStackPane(stackPane);

           menuContainer.getChildren().add(nodes[h]);
       }
   }

    @FXML
    void toggleCart(MouseEvent event) {
        if(!cartToggled) {
            openCart();
        } else {
            closeCart();
        }
        cartToggled = !cartToggled;
    }

    void openCart() {
        vboxCart.setDisable(false);
        vboxCart.setOpacity(1);
    }

    void closeCart() {
        vboxCart.setDisable(true);
        vboxCart.setOpacity(0);
    }

    public void updateCartItems() throws IOException {
        vboxCartItems.getChildren().clear();
        ArrayList<CartItem> items = CartManager.getItems();
        Node[] nodes = new Node[items.size()];

        for(int i = 0; i < items.size(); i++) {
            final int h = i;
            CartItem cartItem = items.get(i);

            FXMLLoader cartItemLoader = new FXMLLoader();
            cartItemLoader.setLocation(HelloApplication.class.getResource("views/cart_item.fxml"));

            Node vboxCartItem = cartItemLoader.load();

            CartItemController cartItemController = cartItemLoader.getController();
            cartItemController.setCartItem(cartItem);


            vboxCartItems.getChildren().add(vboxCartItem);
        }
    }

    @FXML
    void goToCheckout(ActionEvent event) throws IOException {
        if(homeController != null) {
            this.homeController.showCheckout();
        }
    }

    void setMainApp(HelloApplication app) {
        this.mainApp = app;;
        this.initialize();
    }

    void setHomeController(HomeController homeController) {
        this.homeController = homeController;
        this.initialize();
    }
}
