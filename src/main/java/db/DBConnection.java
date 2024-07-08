package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    //--Trigger the creation of connection
    private static DBConnection dbConnetion;
    private Connection connection;

    //--Run for the first time only to create the connection
    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "1234");
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbConnetion !=null ? dbConnetion: (dbConnetion = new DBConnection());
    }

    public Connection getConnection(){
        return connection;
    }


}
