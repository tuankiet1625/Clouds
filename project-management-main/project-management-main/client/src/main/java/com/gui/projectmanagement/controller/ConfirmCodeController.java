package com.gui.projectmanagement.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gui.projectmanagement.entity.RegesterObject;
import com.gui.projectmanagement.network.GuestStream;
import com.gui.projectmanagement.network.StreamObject;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfirmCodeController implements Initializable {
    @FXML
    private TextField number1;

    @FXML
    private TextField number2;

    @FXML
    private TextField number3;

    @FXML
    private TextField number4;

    @FXML
    private Label notification;

    @FXML
    private Label nottification_email;

    @FXML
    private Label timeline_lb;

    @FXML
    private Hyperlink resend_code;

    @FXML
    private Button confirm;

    private int code = -1;

    private int countdown_seconds = 60;

    private Timeline timeline;

    private StreamObject so ;

    private RegesterObject ro ;

    LoginController lc ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirm.setDisable(true);
        setAutoFocusOnInput(number1, number2);
        setAutoFocusOnInput(number2, number3);
        setAutoFocusOnInput(number3, number4);

        applyRestrictions(number1);
        applyRestrictions(number2);
        applyRestrictions(number3);
        applyRestrictions(number4);

        number1.setAlignment(Pos.CENTER);
        number2.setAlignment(Pos.CENTER);
        number3.setAlignment(Pos.CENTER);
        number4.setAlignment(Pos.CENTER);

        ChangeListener<String> changeListener_code = new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                number1.setStyle("-fx-border-color: transparent ;");
                number2.setStyle("-fx-border-color: transparent ;");
                number3.setStyle("-fx-border-color: transparent ;");
                number4.setStyle("-fx-border-color: transparent ;");
                notification.setText("");

                if (!number1.getText().isEmpty() && !number2.getText().isEmpty() && !number3.getText().isEmpty() && !number4.getText().isEmpty()) {
                    confirm.setDisable(false);
                } else {
                    confirm.setDisable(true);
                }
            }
        };

        number1.textProperty().addListener(changeListener_code);
        number2.textProperty().addListener(changeListener_code);
        number3.textProperty().addListener(changeListener_code);
        number4.textProperty().addListener(changeListener_code);
    }

    private void setAutoFocusOnInput(TextField sourceTextField, TextField targetTextField) {
        sourceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                targetTextField.requestFocus();
            }
        });
    }

    private void applyRestrictions(TextField textfield) {
        textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                textfield.setText(newValue.substring(0, 1));
            }
            if (!newValue.matches("[0-9]*")) {
                textfield.setText("");
            }
        });
    }

    @FXML
    public void resend_code(ActionEvent event) {
        timeline_lb.setVisible(true);
        resend_code.setVisible(false);
    }

    @FXML
    public void close(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    public void confirm(ActionEvent event) {
        GuestStream gs = new GuestStream() ;

        String number1 = this.number1.getText() ;
        String number2 = this.number2.getText() ;
        String number3 = this.number3.getText() ;
        String number4 = this.number4.getText() ;
        if (number1.isEmpty() || number2.isEmpty() || number3.isEmpty() || number4.isEmpty()) {
            throw new RuntimeException("Not enough characters.") ;
        }
        String code = number1.concat(number2).concat(number3).concat(number4) ;
        String result = gs.regester_last(so, code) ;
        if (result.equals("@rgst_success")) {
            alert("Registered successfully !");
            Stage present_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            present_window.close();
            lc.regesterSuccess();
            lc.clear();
        } else if (result.equals("@code_is_wrong")){
            this.number1.setStyle("-fx-border-color: red ;" +
                    "-fx-border-radius: 3px;");
            this.number2.setStyle("-fx-border-color: red ;" +
                    "-fx-border-radius: 3px;");
            this.number3.setStyle("-fx-border-color: red ;" +
                    "-fx-border-radius: 3px;");
            this.number4.setStyle("-fx-border-color: red ;" +
                    "-fx-border-radius: 3px;");
            notification.setText("Verification code is incorrect, please try again!");
            notification.setAlignment(Pos.CENTER);
        } else if (result.equals("@rgst_unsuccessful")) {
            error("Regester Unsuccessful!");
            Stage present_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            present_window.close();
        }
    }

    public void loadLoginController(LoginController lc) {
        this.lc = lc ;
    }

    public void loadStreams(StreamObject so) {
        this.so = so ;
    }

    public void loadRegesterObject(RegesterObject ro) {
        this.ro = ro ;
    }

    private static double xOffset = 0;
    private static double yOffset = 0;

    public void error(String message) {
        URL url = LoginController.class.getResource("/com/gui/projectmanagement/view/AlertError.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        try {
            Parent root = loader.load();

            AlertErrorController aec = loader.getController() ;
            aec.load(message);

            Scene scene = new Scene(root, 350, 190);
            scene.setFill(Color.TRANSPARENT);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            });

            primaryStage.initModality(Modality.APPLICATION_MODAL);

            primaryStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void alert(String message) {
        URL url = LoginController.class.getResource("/com/gui/projectmanagement/view/Alert.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        try {
            Parent root = loader.load();

            AlertController ac = loader.getController() ;
            ac.load(message);
            Scene scene = new Scene(root, 350, 190);
            scene.setFill(Color.TRANSPARENT);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            });

            primaryStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
