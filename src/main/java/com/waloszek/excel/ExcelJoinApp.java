/*
 * Created on 17.10.16
 *
 * Copyright (c) Zetcom AG, 2016
 *
 * $$Author$$
 * $$Revision$$
 * $$Date$$
 */
package com.waloszek.excel;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author André Waloszek
 */
public class ExcelJoinApp {

    private final DatabaseService databaseService = new DatabaseService();

    private final ExcelImporter importer = new ExcelImporter(databaseService);


    public ExcelJoinApp(String[] args) {
        Options options = createOptions();

        try {
            DefaultParser defaultParser = new DefaultParser();
            CommandLine commandLine = defaultParser.parse(options, args);

            if (commandLine.hasOption("h")) {
                printHelp(options);
                return;
            }

            importFiles(commandLine);
            executeQueryAndExport(commandLine);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            printHelp(options);
        }

    }


    private void executeQueryAndExport(CommandLine commandLine) {
        File output = new File(commandLine.getOptionValue("o"));
        ExcelMapper excelMapper = new ExcelMapper(output);
        databaseService.execQuery(commandLine.getOptionValue("c"), excelMapper);
    }

    private void importFiles(CommandLine commandLine) throws ParseException {
        if (commandLine.getOptionValues("f").length == 0)
            throw new ParseException("Missing file values");

        for (String fileName : commandLine.getOptionValues("f")) {
            File file = new File(fileName);
            if (!file.exists())
                throw new ParseException(String.format("File [%s] does not exist", fileName));
            else
                importer.read(file, ExcelImporter.DEFAULT_HEADER_ROW, 1);
        }
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ExcelJoin", options);
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "print usage");
        options.addOption(Option.builder("c").required().hasArg(true).longOpt("command").
                desc("sql query to run on the excel files").valueSeparator('=').build());
        options.addOption(Option.builder("o").required().hasArg(true).longOpt("output").
                desc("file name were the result is written to").build());
        options.addOption(Option.builder("f").required().hasArgs().longOpt("files").
                desc("comma separated list of excel files").valueSeparator(',').build());
        return options;
    }

    public static void main(String[] args) throws Exception {
        new ExcelJoinApp(args);

    }
}