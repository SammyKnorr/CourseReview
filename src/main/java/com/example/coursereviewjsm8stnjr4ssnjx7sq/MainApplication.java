package com.example.coursereviewjsm8stnjr4ssnjx7sq;

import Code.DatabaseImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;


import java.io.IOException;

public class MainApplication extends Application {

    private static Stage loginStage;
    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Course Review Login");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        loginStage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        DatabaseImpl database = new DatabaseImpl();
        if(!database.isCreated()){
            database.createDatabase();
            database.createTables();
        }
        launch();
    }
}