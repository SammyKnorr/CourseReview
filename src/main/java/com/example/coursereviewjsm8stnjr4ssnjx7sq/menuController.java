package com.example.coursereviewjsm8stnjr4ssnjx7sq;

import Code.MenuImpl;
import Code.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.SQLException;

public class menuController {

    @FXML
    public Label mainMenuMessageLabel;
    public Button showReviewButton;
    public TextField reviewCourseField;
    public TextField reviewMessageField;
    public TextField reviewRatingField;
    public Button submitReviewButton;
    public Button seeReviewsButton;
    public TextField seeReviewsCourse;
    public Label reviewsResult;
    public Button logoutButton;
    MenuImpl menu = new MenuImpl();
//    LoginController loginController = new LoginController();

    Student student = LoginController.student;


    public void logoutButton(ActionEvent actionEvent) throws IOException {
        LoginController.loggedIn = 0;
        MainApplication mainApplication = new MainApplication();
        mainApplication.changeScene("hello-view.fxml");
    }
    public void SeeReviewsButton() throws SQLException {
//        if(seeReviewsCourse.getText().length() == 0){
//            mainMenuMessageLabel.setText("Course field must be filled out");
//            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
//            return;
//        }
//        if(!reviewCourseField.getText().contains(" ")){
//            mainMenuMessageLabel.setText("Course must have a space between the department and course number");
//            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
//            return;
//        }
        String[] split = seeReviewsCourse.getText().split(" ");
        try {
            validEntry();
            reviewsResult.setVisible(false);
            String reviews = menu.printReviews(split[0] ,Integer.parseInt(split[1]));
            reviewsResult.setText(reviews);
            reviewsResult.setVisible(true);
        }catch (IllegalArgumentException e){
            mainMenuMessageLabel.setText("Course does not follow the format CS 1234");
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
        }catch(IllegalStateException e){
            mainMenuMessageLabel.setText("No course reviews for: "+seeReviewsCourse.getText());
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
        }

    }

    private void validEntry() {
        String entry = seeReviewsCourse.getText();
        String courseIDRegex = "^[A-Z]{1,4}\\s\\d{4}$";
        if (!entry.matches(courseIDRegex)) {
            throw new IllegalArgumentException("Invalid course ID format");
        }
    }


    @FXML
    public void handleTextFieldKeyPresses(KeyEvent event) {
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            mainMenuMessageLabel.setText("Welcome to the main menu!");
        }
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                SeeReviewsButton();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void writeReviewsButton(ActionEvent actionEvent){
        reviewCourseField.setVisible(true);
        reviewMessageField.setVisible(true);
        reviewRatingField.setVisible(true);
        submitReviewButton.setVisible(true);
    }

    private void validSubmitReview() {
        String entry = reviewCourseField.getText();
        String courseIDRegex = "^[A-Z]{1,4}\\s\\d{4}$";
        if (!entry.matches(courseIDRegex)) {
            throw new IllegalArgumentException("Invalid course ID format");
        }
    }
    public void submitReviewButton(ActionEvent actionEvent){
        try{
            validSubmitReview();
        }catch (IllegalArgumentException e){
            mainMenuMessageLabel.setText("Course does not follow the format CS 1234");
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
        }


        if(reviewCourseField.getText().length() == 0 || reviewMessageField.getText().length() == 0 || reviewRatingField.getText().length() == 0){
            mainMenuMessageLabel.setText("Each field must be filled out");
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
            return;
        }
        if(!reviewCourseField.getText().contains(" ")){
            mainMenuMessageLabel.setText("Course must have a space between the department and course number");
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
            return;
        }
        String[] splitted = reviewCourseField.getText().split(" ");
        int reviewScore = Integer.parseInt(reviewRatingField.getText());
        if (!(1 <= reviewScore && reviewScore <= 5)){
            mainMenuMessageLabel.setText("Review score must be a number between 1-5");
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
            return;
        }
        try{
            menu.addReview(student.getId(), splitted[0], Integer.parseInt(splitted[1]), reviewMessageField.getText(), Integer.parseInt(reviewRatingField.getText()));
            mainMenuMessageLabel.setText("Review Added!");
            reviewCourseField.clear();
            reviewMessageField.clear();
            reviewRatingField.clear();
            reviewCourseField.setVisible(false);
            reviewMessageField.setVisible(false);
            reviewRatingField.setVisible(false);
            submitReviewButton.setVisible(false);
        }catch (IllegalArgumentException e){
            mainMenuMessageLabel.setText("Course does not follow the format CS 1234");
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
        }catch (IllegalStateException e){
            mainMenuMessageLabel.setText("You have already reviewed "+ reviewCourseField.getText());
            mainMenuMessageLabel.setStyle("-fx-alignment: TOP_CENTER");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
