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

/**
 * @author André Waloszek
 */
public class Column {

    private final String name;

    public Column(String name) {
        this.name = name;
    }

    public String getNameAndType() {
        return name + " VARCHAR(256)";
    }

    public String getName() {
        return name;
    }
}
