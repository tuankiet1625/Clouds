package com.gui.projectmanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class HostController implements Initializable {

    @FXML
    private TextField host ;

    @FXML
    private TextField port ;

    String imaPart = "/com/gui/projectmanagement/images/app.png" ;

    Image icon_app = new Image(getClass().getResourceAsStream(imaPart));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private static double xOffset = 0;
    private static double yOffset = 0;

    public void finish(ActionEvent event) {
        String host = this.host.getText() ;
        int port = Integer.parseInt(this.port.getText()) ;

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();

        try {
            URL url = getClass().getResource("/com/gui/projectmanagement/view/Login.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            LoginController lc = loader.getController();
            lc.loadHost(host, port);
            Scene scene = new Scene(root,902,502);
            scene.setFill(Color.TRANSPARENT);
            Stage primaryStage = new Stage() ;

            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            });

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.getIcons().add(icon_app);
            primaryStage.setTitle("Log In");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
