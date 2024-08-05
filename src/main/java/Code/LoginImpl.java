package Code;

import java.sql.*;

public class LoginImpl implements Login{

    Connection connection;
    Student student;
    boolean loggedIn = false;
    String sql;
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Reviews.sqlite3");
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public Student login(String username, String password){
        String pass = getPassWord(username);

        if(password.equals(pass)){
            loggedIn = true;
            return student;
        }else{
            throw new IllegalStateException("Password is incorrect");
        }
    }
    @Override
    public void logout(){
        loggedIn = false;
        student = null;
    }

    @Override
    public String getPassWord(String username){
        connect();
        String password = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Students WHERE Username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                password = resultSet.getString("Password");

                int id = resultSet.getInt("ID");

                student = new Student(id, username, password);
            }


        } catch (SQLException e) {
            disconnect();
            throw new IllegalArgumentException("user does not exist");
        }
        disconnect();
        return password;
    }

    @Override
    public void addUser(String name, String password, String confirm) {
        if (!password.equals(confirm)){
            throw new IllegalStateException("Passwords do not match!");
        }
        connect();

        sql = String.format("""
            INSERT INTO Students (Username, Password) values ("%s", "%s");
            """, name, password);
        executeUpdateSQL(sql);


        disconnect();


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

}
