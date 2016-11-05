/*
 * Created on 30.10.16
 *
 * Copyright (c) Zetcom AG, 2016
 *
 * $$Author$$
 * $$Revision$$
 * $$Date$$
 */
package com.waloszek.excel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author André Waloszek
 */
public class PrintMapper implements ResultSetMapper {

    @Override
    public void map(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        for (int i = 0; i < metaData.getColumnCount(); i++) {
            System.out.print(metaData.getColumnName(i+1) + "\t\t");
        }

        System.out.println();

        while (resultSet.next()) {
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.print(resultSet.getString(i+1)+ "\t\t");
            }
            System.out.println();
        }
    }
}
