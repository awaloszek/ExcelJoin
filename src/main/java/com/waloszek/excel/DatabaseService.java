/*
 * Created on 18.10.16
 *
 * Copyright (c) Zetcom AG, 2016
 *
 * $$Author$$
 * $$Revision$$
 * $$Date$$
 */
package com.waloszek.excel;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author André Waloszek
 */
public class DatabaseService {

    private static final String URL = "jdbc:derby:memory:exceljoin;create=true;user=dbuser;password=dbuserpwd";

    public static final OutputStream NULL_STREAM = new OutputStream() {
        public void write(int b) {}
    };

    {
        // suppress output with com.waloszek.excel.DatabaseService.NULL_STREAM
        System.setProperty("derby.stream.error.field", "java.lang.System.err");

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Database driver not found", ex);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            throw new RuntimeException("Error retrieving database connection", ex);
        }
    }


    public int execUpdate(String statement) {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {

            return stmt.executeUpdate(statement);

        } catch (SQLException e) {
            throw new RuntimeException("Statement: " + statement, e);
        }
    }

    public void execQuery(String query, ResultSetMapper resultSetMapper) {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(query);
            resultSetMapper.map(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException("Query: " + query, e);
        }

    }
}
