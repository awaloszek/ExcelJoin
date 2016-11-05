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

import com.waloszek.excel.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author André Waloszek
 */
public class Table {

    private final String name;

    private final List<Column> columns = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public String getCreate() {
        return new StringBuilder("CREATE TABLE ").append(name).append("(")
                .append(columns.stream().map(col -> col.getNameAndType()).collect(Collectors.joining(", ")))
                .append(")")
                .toString();
    }

    public String getInsert(List<String> data) {
        return new StringBuilder("INSERT INTO ").append(name).append(" (")
                .append(columns.stream().map(col -> col.getName()).collect(Collectors.joining(", ")))
                .append(") VALUES (")
                .append(data.stream().map(value -> "'" + value + "'").collect(Collectors.joining(", ")))
                .append(")")
                .toString();
    }
}
