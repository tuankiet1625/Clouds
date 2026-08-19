package com.gui.projectmanagement.functions;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Notification {
    public static void showAlert(Alert.AlertType alertType, String headear_text, String message) {
        // Image icon = new Image(String.valueOf(getClass().getResource("/com/gui/chat/images/logo2.png")));
        Alert alert = new Alert(alertType);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow() ;
        // stage.getIcons().add(icon) ;
        alert.setTitle("MW") ;
        alert.setHeaderText(headear_text) ;
        alert.setContentText(message) ;
        alert.showAndWait() ;
    }
}
