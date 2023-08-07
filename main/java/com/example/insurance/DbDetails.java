package com.example.insurance;

public class DbDetails {
    public static String username = System.getenv("MYSQL_USER");
    public static String password= System.getenv("MYSQL_PASS");
    public static String query="jdbc:mysql://localhost:3306/insurance";
}
