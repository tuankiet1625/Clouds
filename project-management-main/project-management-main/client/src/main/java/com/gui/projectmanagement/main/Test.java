package com.gui.projectmanagement.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Log In");
        try {
            URL url = getClass().getResource("/com/gui/projectmanagement/view/ConfirmCode.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene scene = new Scene(root,290,350);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            // Image icon_window = new Image(String.valueOf(getClass().getResource("/com/gui/chat/images/logo2.png")));
            // primaryStage.getIcons().add(icon_window);
            primaryStage.setResizable(false);
            // primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
