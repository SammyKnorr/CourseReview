package Code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseImpl implements Database {
    private Connection connection = null;

    String rootPath = System.getProperty("user.dir");
    Path dbPath = Paths.get(rootPath, "Reviews.sqlite3");;

    @Override
    public void createDatabase(){
        rootPath = System.getProperty("user.dir");
        dbPath = Paths.get(rootPath, "Reviews.sqlite3");

        if (Files.exists(dbPath)) {
            System.out.println("Database already exists: " + dbPath);
            return;
        }

        String url = "jdbc:sqlite:" + dbPath.toString();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Database created: " + dbPath);
            }
        } catch (SQLException e) {
            try {
                throw new SQLException("Error creating the database: " + e.getMessage());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public boolean isCreated(){
        return Files.exists(dbPath);
    }


    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    @Override
    public void close(){
        try{
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    @Override
    public void createTables() {
        connect();
        try {
            Statement statement = connection.createStatement();
            String studentTable = "CREATE TABLE Students(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Username VARCHAR(50) NOT NULL UNIQUE, " +
                    "Password VARCHAR(50) NOT NULL)";
            statement.executeUpdate(studentTable);
            String courseTable = "CREATE TABLE Courses(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Department VARCHAR(50) NOT NULL, " +
                    "Catalog_Number INTEGER NOT NULL)";
            statement.executeUpdate(courseTable);
            String reviewsTable = "CREATE TABLE Reviews(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "StudentID INTEGER NOT NULL, " +
                    "CourseID INTEGER NOT NULL, " +
                    "Message VARCHAR(255) NOT NULL, " +
                    "Rating INTEGER NOT NULL," +
                    "FOREIGN KEY (StudentID) REFERENCES Students(ID) ON DELETE CASCADE, " +
                    "FOREIGN KEY (CourseID) REFERENCES Courses(ID) ON DELETE CASCADE) ";
            statement.executeUpdate(reviewsTable);
            close();
        } catch (SQLException e) {
            System.out.println("Tables already created");
        }
    }
}
