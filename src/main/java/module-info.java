module com.example.coursereviewjsm8stnjr4ssnjx7sq {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.coursereviewjsm8stnjr4ssnjx7sq to javafx.fxml;
    exports com.example.coursereviewjsm8stnjr4ssnjx7sq;
}