module com.gui.managerwork {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires com.google.gson;
    requires java.xml;

    exports com.gui.projectmanagement.controller;
    exports com.gui.projectmanagement.authentication;
    opens com.gui.projectmanagement.controller to javafx.fxml, javafx.graphics ;
    opens com.gui.projectmanagement.main to javafx.fxml, javafx.graphics ;
    opens com.gui.projectmanagement.test to javafx.fxml, javafx.graphics ;
    opens com.gui.projectmanagement.view to javafx.fxml, javafx.graphics ;
    opens com.gui.projectmanagement.authentication to javafx.fxml, javafx.graphics ;
    exports com.gui.projectmanagement.entity;
    opens com.gui.projectmanagement.entity to com.google.gson, javafx.fxml, javafx.graphics;
    //  opens javafx.scene.control to com.google.gson;
}