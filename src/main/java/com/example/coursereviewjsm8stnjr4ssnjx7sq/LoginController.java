package com.example.coursereviewjsm8stnjr4ssnjx7sq;

import Code.LoginImpl;
import Code.MenuImpl;
import Code.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public TextField username;

    public PasswordField password;
    public Label loginMessageLabel;
    public Button newUser;
    public Button loginButton;
    public Label confirmPassLabel;
    public PasswordField confirmPass;
    public Button addUserLogin;
    static Student student;



    LoginImpl login = new LoginImpl();

    static int loggedIn;


    public void userLogin(ActionEvent actionEvent) throws IOException {
        MainApplication mainController = new MainApplication();
        loggedIn = loginPrompt();
        if (loggedIn == 1){
            mainController.changeScene("mainMenu.fxml");

        }

    }
    private int loginPrompt(){
        try{
            student = login.login(username.getText(), password.getText());
            return 1;
        }catch (IllegalArgumentException e){
            loginMessageLabel.setText("User does not exist");
            return 0;
        }catch (IllegalStateException e){
            loginMessageLabel.setText("Password is incorrect or User does not Exist");
            return 0;
        }
    }

    public void addUserButton(ActionEvent actionEvent){
        confirmPass.setVisible(true);
        confirmPassLabel.setVisible(true);
        addUserLogin.setVisible(true);
    }

    public void submitNewUser(ActionEvent actionEvent) throws IOException {
        MainApplication mainApplication = new MainApplication();
        if(username.getText().length() == 0 || password.getText().length() == 0){
            loginMessageLabel.setText("Username and Password cannot be blank");
            return;
        }
        try{
            login.addUser(username.getText(),password.getText(),confirmPass.getText());
            loggedIn = loginPrompt();
            if (loggedIn == 1){
                mainApplication.changeScene("mainMenu.fxml");
            }
        }catch(IllegalStateException e){
            loginMessageLabel.setText("try again, passwords do not match");
        }catch (RuntimeException e){
            loginMessageLabel.setText("This user already exists");
        }

    }

}