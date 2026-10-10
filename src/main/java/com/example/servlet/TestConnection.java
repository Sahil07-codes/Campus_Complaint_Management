package com.example.servlet;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()) {
            System.out.println("PostgreSQL connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}