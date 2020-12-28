package by.brausov.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private String URL;
    private String DBNAME;
    private String USER;
    private String PASSWORD;
    private Connection connection = null;

    public Connection getConnection(String dbname, String user, String password) {
        this.DBNAME = dbname;
        this.USER = user;
        this.PASSWORD = password;
        this.URL = "jdbc:mysql://localhost:3306/" + dbname + "?serverTimezone=Europe/Minsk&useSSL=false";

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established!");
        } catch (SQLException e) {
            System.out.println("Database connection not established!");
            System.out.println("Check the data is correct!");
            e.printStackTrace();
        }

        return connection;
    }

    public Connection getConnection() {
        return getConnection("academic_group", "root", "");
    }
}
