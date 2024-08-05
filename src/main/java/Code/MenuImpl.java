package Code;
import java.sql.*;

public class MenuImpl {
    Connection connection;
    boolean loggedIn = false;

    String sql;
    public void addReview(int id, String department, int course, String message, int rating) throws SQLException {
        String entry = department + " " + course;
        String courseIDRegex = "^[A-Z]{1,4}\\s\\d{4}$";
        if (!entry.matches(courseIDRegex)) {
            throw new IllegalArgumentException("Invalid course ID format");
        }

        if(!hasCourse(department, course)){
            addCourse(department, course);
        }
        int courseid = getCourseid(department, course);
        boolean hasReviewedTheCourse = hasReviewed(id, department, course);

        if (!hasReviewedTheCourse) {

            connect();
            sql = String.format("""
                    INSERT INTO Reviews (StudentID, CourseID, Message, Rating) values ("%d", "%d","%s", "%d");
                    """, id, courseid, message, rating);
            executeUpdateSQL(sql);
        }else{
            throw new IllegalStateException("User: "+id+" has already reviewed "+ department + course);
        }
        disconnect();
    }
    public void addCourse(String department, int Catalog_Number){
        if(validCourse(department, Catalog_Number)) {
            connect();
            String dep = department.toUpperCase();
            sql = String.format("""
                    INSERT INTO Courses (Department, Catalog_Number) values ("%s", "%d");
                    """, dep, Catalog_Number);
            executeUpdateSQL(sql);
            disconnect();
        }else{
            throw new IllegalArgumentException("Course does not follow the format CS 1234");
        }
    }

    public boolean validCourse(String department, Integer Catalog_number){
        if(Catalog_number.toString().length() != 4){
            return  false;
        }
        return department.length() <= 4;
    }

    public int getCourseid(String department, int Catalog_Number) throws SQLException {
        connect();

        String query = "SELECT * FROM Courses WHERE Catalog_Number = ? AND Department = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Catalog_Number );
            preparedStatement.setString(2, department );
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int courseID = resultSet.getInt(1);
                disconnect();
                return courseID;
            } else {
                disconnect();
                return 0;
            }
        }
    }
    public boolean hasCourse(String department, int ID) throws SQLException {
        connect();

        String query = "SELECT COUNT(*) FROM Courses WHERE Catalog_Number = ? AND Department = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, ID );
            preparedStatement.setString(2, department );
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                disconnect();
                return count > 0;
            } else {
                disconnect();
                return false;
            }
        }

    }

    public String printReviews(String department, int id) throws SQLException {

        String reviews = "Reviews:\n";
        int courseid = getCourseid(department, id);
        connect();
        try (Statement statement = connection.createStatement()) {

            // SQL query to fetch the desired data
            String sql = "SELECT StudentID, CourseID, Message, Rating FROM Reviews WHERE CourseID = " + courseid;

            // Execute the query and store the result in a ResultSet
            try (ResultSet resultSet = statement.executeQuery(sql)) {

                // Print the column headers
                // Iterate through the ResultSet and print each row

                int total = 0;
                int times = 0;
                boolean flag = false;
                while (resultSet.next()) {
//                    int studentId = resultSet.getInt("StudentID");
//                    int courseId = resultSet.getInt("CourseID");
                    String message = resultSet.getString("Message");
                    int rating = resultSet.getInt("Rating");
                    times++;
                    total+= rating;
                    String holder = message + "\n";
                    reviews += holder;
                    flag = true;
                }

                if(!flag){
                    throw new IllegalStateException("There are no reviews for this class");
                }
                reviews += "The average rating for the class was: " + total/times;
                return reviews;

            }
        }catch (SQLException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasReviewed(int id, String department, int courseID) throws SQLException {
        boolean result = false;
        PreparedStatement statement = null;

        int courseid = getCourseid(department, courseID);
        ResultSet resultSet = null;
        connect();
        try {

            statement = connection.prepareStatement("SELECT COUNT(*) FROM Reviews WHERE StudentID = ? AND CourseID = ?");
            statement.setInt(1, id);
            statement.setInt(2, courseid);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                result = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        disconnect();
        return result;


    }

    public void logout(){
        loggedIn = false;

    }





    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }
    private void executeUpdateSQL(String sql){
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }
}
