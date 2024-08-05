package Code;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        DatabaseImpl database = new DatabaseImpl();
        if(!database.isCreated()){
            database.createDatabase();
            database.createTables();
        }
        LoginImpl login = new LoginImpl();
        MenuImpl menu = new MenuImpl();
//        menu.addReview(1, "ECE", 2630, "Hard", 2);
//        menu.getCourseid("CS", 3140);

//        SystemInterface sys = new SystemInterface();
//        sys.runCourseReview();
//        menu.addReview(2, "CS", 3140, "hello", 5);




    }
}