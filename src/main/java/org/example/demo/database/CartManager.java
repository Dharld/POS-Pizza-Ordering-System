package org.example.demo.database;

import javafx.beans.property.ReadOnlyFloatProperty;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import org.example.demo.controllers.CheckoutController;
import org.example.demo.controllers.MenuController;
import org.example.demo.models.CartItem;
import org.example.demo.models.Product;

import java.io.IOException;
import java.util.ArrayList;

public class CartManager {
    private static MenuController menuController;

    private static CheckoutController checkoutController;

    private static ArrayList<CartItem> items = new ArrayList<>();
    private static final ReadOnlyIntegerWrapper totalItems = new ReadOnlyIntegerWrapper(0);

    private static final ReadOnlyFloatWrapper totalPrice = new ReadOnlyFloatWrapper(0);


    public static float getTotalPrice() {
        return totalPrice.get();
    }

    public static ReadOnlyFloatProperty totalPriceProperty() {
        return totalPrice.getReadOnlyProperty();
    }

    public static int getTotalItems() {
        return totalItems.get();
    }

    public static ReadOnlyIntegerProperty totalItemsProperty() {
        return totalItems.getReadOnlyProperty();
    }


    public static ArrayList<CartItem> getItems() {
        return items;
    }


    public static void addToCart(CartItem c) throws IOException {
        if (c != null) {
            // Check if the product is already in the cart
            for (CartItem currItem : items) {
                if (currItem.equals(c)) {
                    currItem.setQuantity(currItem.getQuantity() + c.getQuantity());
                    totalItems.set(totalItems.get() + c.getQuantity());
                    totalPrice.set(totalPrice.get() + c.getQuantity() * c.getProduct().getPrice());
                    if (menuController != null) {
                        menuController.updateCartItems();
                    }
                    if(checkoutController != null) {
                        checkoutController.showCartItems();
                    }
                    return;
                }
            }
            // If the product is not in the cart, add a new entry
            items.add(c);
            totalItems.set(totalItems.get() + c.getQuantity());
            totalPrice.set(totalPrice.get() + c.getQuantity() * c.getProduct().getPrice());

            if (menuController != null) {
                menuController.updateCartItems();
            }
            if(checkoutController != null) {
                checkoutController.showCartItems();
            }
        }
    }

    public static void reduceItem(CartItem c) throws IOException {
        if(c != null) {
            CartItem cartItem = items.get(items.indexOf(c));
            if(cartItem.getQuantity() == 1) {
                removeFromCart(cartItem);
            } else {
                cartItem.decreaseQuantity();
                totalItems.set(totalItems.get() - 1);
                totalPrice.set(totalPrice.get() - c.getProduct().getPrice());
            }
            if(menuController != null) {
                menuController.updateCartItems();
            }
            if(checkoutController != null) {
                checkoutController.showCartItems();
            }
        }
    }
    public static void removeFromCart(CartItem c) throws IOException {
        if (c != null) {
            // Check if the product is already in the cart
            for (CartItem currItem : items) {
                if (currItem.equals(c)) {
                    items.remove(c);
                    totalItems.set(totalItems.get() - c.getQuantity());
                    totalPrice.set(totalPrice.get() - c.getQuantity() * c.getProduct().getPrice());
                    if (menuController != null) {
                        menuController.updateCartItems();
                    }
                    if(checkoutController != null) {
                        checkoutController.showCartItems();
                    }
                    return;
                }
            }
        }
    }

    public static void setMenuController(MenuController menu) {
        menuController = menu;
    }

    public static void setCheckoutController(CheckoutController checkoutCtrl) {
        checkoutController = checkoutCtrl;
    }

    public static void clearCart() {
        items = new ArrayList<>();
        totalItems.set(0);
        totalPrice.set(0);
    }
}
