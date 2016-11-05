/*
 * Created on 01.11.16
 *
 * Copyright (c) Zetcom AG, 2016
 *
 * $$Author$$
 * $$Revision$$
 * $$Date$$
 */
package com.waloszek.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author André Waloszek
 */
public class ExcelMapper implements ResultSetMapper {

    private final File file;

    public ExcelMapper(File file) {
        this.file = file;
    }

    @Override
    public void map(ResultSet resultSet) throws SQLException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        ResultSetMetaData metaData = resultSet.getMetaData();

        int rowIndex = 0;
        XSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(metaData.getColumnName(i + 1));
        }

        while (resultSet.next()) {
            row = sheet.createRow(rowIndex++);
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(resultSet.getString(i + 1));
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot write output file [" + file.getName() + "]", e);

        } catch (IOException e) {
            throw new RuntimeException("Cannot write output file [" + file.getName() + "]", e);
        }
    }
}
