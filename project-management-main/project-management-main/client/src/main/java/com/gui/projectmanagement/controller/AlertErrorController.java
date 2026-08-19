package com.gui.projectmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertErrorController implements Initializable {

    @FXML
    private Label message ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void close(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void load(String message) {
        this.message.setText(message);
    }
}
