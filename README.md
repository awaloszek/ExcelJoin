# ExcelJoin
The main purpose of this tool is to join two excel documents based on a selected key-column and write the result to a new excel document. 

It is implemented using Java 8 and Gradle. The implementation uses an in memory derby db. It generates the database table structure from the excel documents and imports the documents data. A given sql query gets evaluated and the output is written to a new excel document.

## How to build
This project uses Gradle for building. To create a distribution use the following command.
```
gradlew installDist
```
This command creates a distribution in *./build/install* with some start scripts.
## How to test
There are two Excel documents **Mappe1.xlsx** and **Mappe2.xlsx** in the *./src/test/resources/* for testing. Check out the *./build.gradle* file for the arguments applied when running
```
gradlew run
```
It creates a **Result.xlsx** file in the project directory.
