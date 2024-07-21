package org.example.demo.database;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.example.demo.models.*;

import java.util.ArrayList;
import java.sql.*;
import java.util.Calendar;

public class JDBCManager {
    private static Connection conn = null;
    private static String url = "jdbc:mysql://localhost:3306/pizza_store";
    private BooleanProperty loading = new SimpleBooleanProperty();

    public static void initialize() {
        try {
            conn = DriverManager.getConnection(url, "root", "");

        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static void main(String[] args) {
        JDBCManager.initialize();
        System.out.println(getAddress(2));
    }

    public static void signup(User user, String password) {
        String sql = "INSERT INTO user (USER_FNAME, USER_LNAME, USER_EMAIL, USER_PASSWORD, USER_PHONENUM) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFname());
            pstmt.setString(2, user.getLname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, password);
            pstmt.setString(5, user.getPhoneNum());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error executing prepared statement: " + ex.getMessage());
        }
    }

    public static boolean login(String email, String password) {
        String sql = "SELECT USER_EMAIL, USER_PASSWORD, USER_FNAME, USER_LNAME, USER_PHONENUM, USER_ID from user where USER_EMAIL = '" + email + "'";
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String userEmail = rs.getString(1);
                String userPassword = rs.getString(2);
                if(userPassword.equals(password)) {

                    String userFirstName = rs.getString(3);
                    String userLastName = rs.getString(4);
                    String userPhoneNum = rs.getString(5);
                    int userId = rs.getInt(6);

                    User user = new User(userFirstName, userLastName, userEmail, userPhoneNum);
                    user.setId(userId);

                    UserManager.setCurrentUser(user);
                    return true;
                }
            }
        } catch(SQLException ex) {
            System.err.println("Error executing create statement: " + ex.getMessage());
        }
        return false;
    }

    public static Address getAddress(int userId) {
        String sql = "SELECT ADDRESS_STREET, ADDRESS_ZIP, ADDRESS_STATE, ADDRESS_CITY FROM address WHERE USER_ID = '" + userId + "'";
        Address address = null;
        if(userId > 0) {
            try(Statement stmt = conn.createStatement()) {

                ResultSet rs = stmt.executeQuery(sql);

                while(rs.next()) {
                    String street = rs.getString(1);
                    String zip = rs.getString(2);
                    String state = rs.getString(3);
                    String city = rs.getString(4);

                    address = new Address(street, city, zip, state);
                }


            } catch (SQLException e) {
                System.err.println("Error executing order item prepared statement: " + e.getMessage());
                address = null;
            }
        }
        return address;
    }
    public static ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();

        String sql = "SELECT CATEGORY_ID, CATEGORY_NAME from category";

        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                categories.add(new Category(id, name));
            }
        } catch(SQLException ex) {
            System.err.println("Error executing create statement: " + ex.getMessage());
        }

        return categories;
    }

    public static ArrayList<Product> getAllProductsOfCategory(int categoryId) {

        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT " +
                        "ITEM_ID, " +
                        "ITEM_NAME, " +
                        "ITEM_DESCRIPTION, " +
                        "ITEM_PRICE, " +
                        "ITEM_IMG_SRC " +
                    "FROM ITEM " +
                    "WHERE CATEGORY_ID =" + categoryId;

        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String desc = rs.getString(3);
                float price = rs.getFloat(4);
                String src = rs.getString(5);

                products.add(new Product(id, name, desc, price, src));
            }
        } catch(SQLException ex) {
            System.err.println("Error executing create statement: " + ex.getMessage());
        }

        return products;
     }

    public static boolean createOptionForProduct(Product p, Option o) {
        boolean res = false;
        String sql = "INSERT INTO item_option (OPTION_NAME, ITEM_ID, OPTION_VALUE) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, o.getName());
            pstmt.setInt(2, p.getId());
            pstmt.setString(3, o.getValue());
            pstmt.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            System.err.println("Error executing prepared statement: " + ex.getMessage());
        }
        return res;
    }

    public static boolean createOrder(Order o, ArrayList<CartItem> cartItems) {
        boolean res = false;
        String sqlOrder = "INSERT INTO order_ (ORDER_STATUS, ORDER_DATE) VALUES (?,?)";
        String sqlOrderItem = "INSERT INTO order_item (ITEM_ID, ORDER_ID, QTY) VALUES (?, ?, ?)";

        // Declare Connection object outside the try-catch block to ensure it's accessible for rollback
        try {
            // Start the transaction
            conn.setAutoCommit(false);

            // Create the order
            try (PreparedStatement pstmtOrder = conn.prepareStatement(sqlOrder, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmtOrder.setString(1, o.getOrderStatus());
                java.util.Date date = o.getOrderDate();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                pstmtOrder.setDate(2, sqlDate);

                // Execute the order insertion
                int orderId = 0;
                pstmtOrder.executeUpdate();

                // Get the generated key
                ResultSet generatedKeys = pstmtOrder.getGeneratedKeys();
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1); // Retrieve the generated ORDER_ID
                } else {
                    System.err.println("Failed to retrieve the auto-generated key for the order.");
                    throw new SQLException("Failed to retrieve the auto-generated key for the order.");
                }

                // Create order items
                for (CartItem c : cartItems) {
                    createOrderItem(conn, sqlOrderItem, c, orderId);
                }

                // Commit the transaction if everything succeeded
                conn.commit();
                res = true;
            } catch (SQLException ex) {
                System.err.println("Error executing order prepared statement: " + ex.getMessage());
                // Rollback the transaction in case of failure
                conn.rollback();
            } finally {
                // Restore auto-commit mode
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            System.err.println("Error establishing database connection: " + ex.getMessage());
        }

        return res;
    }

    public static boolean createOrderItem(Connection conn, String sql, CartItem c, int orderId) throws SQLException {
        boolean res = false;
        if (c != null) {
            int qty = c.getQuantity();
            int itemId = c.getProduct().getId();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, itemId);
                pstmt.setInt(2, orderId);
                pstmt.setInt(3, qty);

                // Execute the insertion
                pstmt.execute();
                res = true;
            } catch (SQLException ex) {
                System.err.println("Error executing order item prepared statement: " + ex.getMessage());
                throw ex; // Propagate the exception to the caller
            }
        }
        return res;
    }

    public static boolean createAddressForUser(int userId, Address address) {
        boolean res = false;
        String sql = "INSERT INTO address (ADDRESS_STREET, ADDRESS_CITY, ADDRESS_ZIP, ADDRESS_STATE, USER_ID) VALUES (?,?,?,?,?)";
        if(address != null && userId > 0) {
            String city = address.getCity();
            String state = address.getState();
            String street = address.getStreet();
            String zip = address.getZip();

            try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, street);
                pstmt.setString(2, city);
                pstmt.setString(3, zip);
                pstmt.setString(4, state);
                pstmt.setInt(5, userId);

                pstmt.executeUpdate();

                res = true;
            } catch (SQLException e) {
                System.err.println("Error executing order item prepared statement: " + e.getMessage());

            }
        }
        return res;
    }
 }
