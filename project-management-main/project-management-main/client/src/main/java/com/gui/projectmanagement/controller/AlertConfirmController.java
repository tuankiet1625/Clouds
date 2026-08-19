package com.gui.projectmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertConfirmController implements Initializable {

    @FXML
    private Label message ;

    @FXML
    private Button btn_yes ;

    @FXML
    private Button btn_cancel ;

    public static boolean result = false ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_yes.setOnAction(event -> {
            result = true ;
            Stage window_present = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window_present.close();
        });

        btn_cancel.setOnAction(event -> {
            result = false ;
            Stage window_present = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window_present.close();
        });
    }

    public void loadInformation(String message) {
        this.message.setText(message);
    }
}
