package com.DriverPackage;
/**
 * The DBConnection class contains the methods that 
 * connects the application to the database
 * @author Dharun K (Expleo)
 * @since 22 Feb 2024
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection doDBConnection() throws SQLException {
        final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String password = "oracle12";
        Connection con = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con;
    }    
}
