package database;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.LinkedList;

import client.*;
import product.OrganizationType;
import product.Product;
import product.UnitOfMeasure;

public class DatabaseHandler {

    private String URL;
    private String admin;
    private String password;
    private Connection connection;

    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (username, password) VALUES (?, ?)";
    private static final String LOGIN_USER_REQUEST = "SELECT * FROM USERS WHERE username = ? AND password = ?";
    private static final String CHECK_USER_REQUEST = "SELECT * FROM USERS WHERE username = ? AND password = ?";
    private static final String CHECK_USERNAME_REQUEST = "SELECT * FROM USERS WHERE username = ?";
    private static final String CHECK_PASSWORD_REQUEST = "SELECT * FROM USERS WHERE password = ?";
    private static final String COUNT_PRODUCTS_REQUEST = "SELECT COUNT(*) FROM PRODUCTS";
    private static final String PRODUCTS_REQUEST = "SELECT * FROM PRODUCTS";
    private static final String ADD_PRODUCT_REQUEST = "INSERT INTO PRODUCTS (name, x, y, creationdate, price, unitofmeasure, idoforg, nameoforg, annualturnover, type, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CLEAR_REQUEST = "DELETE FROM PRODUCTS WHERE USERNAME = ?";
    private static final String REMOVE_BY_ID_REQUEST = "DELETE FROM PRODUCTS WHERE ID = ?";

    public DatabaseHandler(String URL, String username, String password) {
        this.URL = URL;
        this.admin = username;
        this.password = password;
    }

    public boolean connectToDatabase() {
        try {
            connection = DriverManager.getConnection(URL, admin, password);
            System.out.println("Подключение к базе данных установлено.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Не удалось подключиться к базе данных.");
            return false;
        }
    }

    public boolean checkUser(User user) {
        try {
            System.out.println("we're trying to checkUser");
            PreparedStatement checkStatement = connection.prepareStatement(CHECK_USER_REQUEST);
            checkStatement.setString(1, user.getUsername());
            checkStatement.setString(2, user.getPassword());
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                checkStatement.close();
                System.out.println("we must return true");
                return true;
            }
            checkStatement.close();
            System.out.println("we must return false");
            return false;
        }catch(SQLException e) {
            System.out.println("we cant't check user but we have tryed");
            return false;
        }
    }

    public String registerUser(User user) {
        try {
            System.out.println("we're in databasehandler");
            if (checkUser(user)) {
                System.out.println("Вы жуе успешно авторизовались.");
                return "Успешная авторизация.";
            } else {
                PreparedStatement checkUsernameStatement = connection.prepareStatement(CHECK_USERNAME_REQUEST);
                checkUsernameStatement.setString(1, user.getUsername());
                ResultSet result = checkUsernameStatement.executeQuery();
                if (result.next()) {
                    checkUsernameStatement.close();
                    return "Неверный пароль";
                }
            }
            PreparedStatement addStatement = connection.prepareStatement(ADD_USER_REQUEST);
            addStatement.setString(1, user.getUsername());
            addStatement.setString(2, user.getPassword());
            addStatement.executeUpdate();
            addStatement.close();
            System.out.println("we must return true in registerUser method");
            return "Успешная регистрация";
        }catch (SQLException e) {
            return "неа";

        }
    }

    /*

    public int coutner() {
        try {
            PreparedStatement counerStatement = connection.prepareStatement(COUNT_PRODUCTS_REQUEST);
            counerStatement.executeUpdate();
        }catch (SQLException e) {

        }

    }

     */

    public LinkedList<Product> load() {
        LinkedList<Product> collection = new LinkedList<>();
        try {
            PreparedStatement loadStatement = connection.prepareStatement(PRODUCTS_REQUEST);
            ResultSet result = loadStatement.executeQuery();
            while(result.next()) {
                Product product = new Product(result.getLong(1),
                        result.getString(2),
                        result.getInt(3),
                        result.getInt(4),
                        ZonedDateTime.ofInstant(result.getTimestamp(5).toLocalDateTime().toInstant(ZoneOffset.UTC), ZoneId.of("UTC")),
                        result.getDouble(6),
                        UnitOfMeasure.valueOf(result.getString(7)),
                        result.getLong(12),
                        result.getString(8),
                        result.getDouble(9),
                        OrganizationType.valueOf(result.getString(10)),
                        result.getString(11));
                collection.add(product);
            }
            loadStatement.close();
            return collection;
        }catch (SQLException e) {
            System.out.println(e.getMessage() + "   " + e.toString());
            System.out.println("Some problems with getting collection from db");
        }
        return collection;
    }


    public void addProduct(Product product) {
        try{
            PreparedStatement addProductStatement = connection.prepareStatement(ADD_PRODUCT_REQUEST);
            addProductStatement.setString(1, product.getName());
            addProductStatement.setInt(2, product.getX());
            addProductStatement.setInt(3, product.getY());
            addProductStatement.setTimestamp(4, Timestamp.from(product.getCreationDate().toInstant()));
            addProductStatement.setDouble(5, product.getPrice());
            addProductStatement.setString(6, product.getUnitOfMeasure().toString());
            addProductStatement.setLong(7, product.getIdOfOrg());
            addProductStatement.setString(8, product.getNameOfOrg());
            addProductStatement.setDouble(9, product.getAnnualTurnover());
            addProductStatement.setString(10, product.getType().toString());
            addProductStatement.setString(11, product.getUsername());
            addProductStatement.executeUpdate();
            addProductStatement.close();
        }catch(SQLException e) {
            System.out.println("we're trying to add new product");
        }
    }

    public String clear(String username) {
        try {
            PreparedStatement clearStatement = connection.prepareStatement(CLEAR_REQUEST);
            clearStatement.setString(1, username);
            clearStatement.executeUpdate();
            clearStatement.close();
            return "Удаление объектов из бд проло успешно";
        }catch (SQLException e) {
            return "неа";
        }

    }

    public String removeById(Long id) {
        String s = " ";
        return s;
    }



    public void update() {
        //todo;
    }
}
