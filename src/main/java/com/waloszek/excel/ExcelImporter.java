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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author André Waloszek
 */
public class ExcelImporter {

    public static int DEFAULT_HEADER_ROW = -1;

    public final DatabaseService databaseService;

    public ExcelImporter(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }


    public Table read(File excelFile, int headerRow, int skipRows) {

        try (FileInputStream in = new FileInputStream(excelFile)) {

            XSSFWorkbook workbook = new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);

            String tableName = excelFile.getName().replace(".xlsx", "");
            Table table = importStructure(tableName, sheet, headerRow);
            importData(table, sheet, skipRows);
            return table;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importData(Table table, XSSFSheet sheet, int skipRows) {

        int rowIndex = 0;
        for (Row row : sheet) {
            if (skipRows < rowIndex + 1) {
                List data = new ArrayList();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.add(Boolean.toString(cell.getBooleanCellValue()));
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.add(Double.toString(cell.getNumericCellValue()));
                            break;
                        case Cell.CELL_TYPE_STRING:
                            data.add(cell.getStringCellValue());
                            break;
                    }
                }
                String insert = table.getInsert(data);
                databaseService.execUpdate(insert);
            }

            rowIndex++;
        }

    }

    private Table importStructure(String tableName, XSSFSheet sheet, int headerRow) {
        Table table = new Table(tableName);

        if (headerRow == DEFAULT_HEADER_ROW) {
            XSSFRow row = sheet.getRow(0);

            for (Cell cell : row) {
                Column column = new Column(cell.getStringCellValue());

                table.addColumn(column);

            }

        } else {
            // TODO
        }

        databaseService.execUpdate(table.getCreate());
        return table;
    }


}
