package com.gui.projectmanagement.functions;

import com.gui.projectmanagement.controller.AlertErrorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AlertLib {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public void error(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(AlertLib.class.getResource("/com/gui/projectmanagement/view/AlertError.fxml"));
            Parent root = loader.load() ;
            AlertErrorController aec = loader.getController() ;
            aec.load(message);
            Scene scene = new Scene(root) ;
            scene.setFill(Color.TRANSPARENT);
            Stage window = new Stage() ;

            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                window.setX(mouseEvent.getScreenX() - xOffset);
                window.setY(mouseEvent.getScreenY() - yOffset);
            });

            window.initStyle(StageStyle.TRANSPARENT);
            window.initModality(Modality.APPLICATION_MODAL);
            window.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
