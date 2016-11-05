# ExcelJoin
The main purpose of this tool is to join two excel documents based on a selected key-column and write the result to a new excel document. 

It is implemented using Java 8 and Gradle. The implementation uses an in memory derby db. It generates the database table structure from the excel documents and imports the documents data. A given sql query gets evaluated and the output is written to a new excel document.
