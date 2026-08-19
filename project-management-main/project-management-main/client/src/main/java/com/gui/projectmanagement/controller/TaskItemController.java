package com.gui.projectmanagement.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskItemController implements Initializable {

    @FXML
    Label name_task ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadNameTask(String name_task) {
        this.name_task.setText(name_task);
    }
}
