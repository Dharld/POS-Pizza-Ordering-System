package org.example.demo.database;
import org.example.demo.models.Address;
import org.example.demo.models.User;

public class UserManager {
    private static User currentUser = null;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setAddress(Address address) {
        currentUser.setAddress(address);
    }
}
