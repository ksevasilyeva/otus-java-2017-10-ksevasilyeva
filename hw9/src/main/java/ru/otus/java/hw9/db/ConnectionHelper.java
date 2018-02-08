package ru.otus.java.hw9.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://" +       //db type
                "localhost:" +               //host name
                "3306/" +                    //port
                "otus_java?" +              //db name
                "user=ksenia&" +              //login
                "password=ksenia&" +          //password
                "useSSL=false";             //do not use Secure Sockets Layer

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
