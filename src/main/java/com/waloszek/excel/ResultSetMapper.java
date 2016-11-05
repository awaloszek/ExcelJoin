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
import java.sql.SQLException;

/**
 * @author André Waloszek
 */
public interface ResultSetMapper {

    void map(ResultSet resultSet) throws SQLException;
}
