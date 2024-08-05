package Code;

import java.sql.SQLException;
import java.util.Scanner;

public class SystemInterface {

    Scanner scan = new Scanner(System.in);
    Login log = new LoginImpl();
    MenuImpl menu = new MenuImpl();
    boolean run = true;
    Student student = null;


    int loggedIn;
    public void runCourseReview(){

        do{
            int welcome;
            student = null;

            welcome = welcomePrompt();


            if(welcome == 1){
                loggedIn = loginPrompt();
            }
            if(welcome == 2){
                loggedIn =  newAccountPrompt();
            }
            while (loggedIn == 1){
                mainMenuPrompt();
            }


        }while(run);




    }
    private int welcomePrompt(){
        System.out.println("Welcome to Andy's Course Review System!\n" +
                "1) Login to Existing Account\n" +
                "2) Create New Account\n");
        String input = scan.nextLine();
        if (input.equals("1")){
            return 1;
        }
        if (input.equals("2")){
            return 2;
        }
        return 3;
    }
    private int newAccountPrompt(){

        System.out.println("Enter Username: \n");
        String user = scan.nextLine();
        System.out.println("Enter Password: \n");
        String pass = scan.nextLine();
        System.out.println("Confirm Password: \n");
        String confirm = scan.nextLine();
        try{
            log.addUser(user,pass,confirm);
            student = log.login(user, pass);
            return 1;
        }catch(IllegalStateException e){
            System.out.println("try again");
            return 0;
        }catch (RuntimeException e){
            System.out.println("This user already exists");
            return 0;
        }
    }

    private int loginPrompt(){
        System.out.println("Enter Username: \n");
        String user = scan.nextLine();
        System.out.println("Enter Password: \n");
        String pass = scan.nextLine();
        try{
            student = log.login(user, pass);
            return 1;
        }catch (IllegalArgumentException e){
            System.out.println("User does not exist");
            return 0;
        }catch (IllegalStateException e){
            System.out.println("Password is incorrect");
            return 0;
        }
    }

    private int mainMenuPrompt(){
        System.out.println("Welcome to Main Menu of the Course Review System!\n" +
                "1) Submit Course Review\n" +
                "2) See Reviews for Course\n" +
                "3) Logout\n");
        String input = scan.nextLine();
        if (input.equals("1")){
            courseReviewPrompt();
        } else if (input.equals("2")) {
            seeReviewsPrompt();
        } else if (input.equals("3")) {
            loggedIn = 0;
        }

        return 0;
    }

    private void courseReviewPrompt(){
        System.out.println("What is the course? ex.(CS 3140)\n");
        String course = scan.nextLine();
        System.out.println("What is your message?\n");
        String message = scan.nextLine();
        System.out.println("What rating do you give the class? 1-5\n");
        int rating = scan.nextInt();
        String[] splitted = course.split(" ");
        try{
            menu.addReview(student.getId(), splitted[0], Integer.parseInt(splitted[1]), message, rating);
        }catch (IllegalArgumentException e){
            System.out.println("Course does not follow the format CS 1234");
        }catch (IllegalStateException e){
            System.out.println("You have already reviewed "+course);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void seeReviewsPrompt(){
        System.out.println("What course would you like to see reviews for? ex.(CS 3140)\n");
        String course = scan.nextLine();
        String[] splitted = course.split(" ");
        if(!menu.validCourse(splitted[0], Integer.valueOf(splitted[1]))){
            System.out.println("Please enter a valid course.");
        }else {
            try {
                System.out.println(menu.printReviews(splitted[0] ,Integer.parseInt(splitted[1])));
            } catch (IllegalArgumentException e) {
                System.out.println("There are no reviews for this course");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
