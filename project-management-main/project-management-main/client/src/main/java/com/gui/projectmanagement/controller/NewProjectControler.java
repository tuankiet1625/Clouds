package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.authentication.NewProjectManager;
import com.gui.projectmanagement.entity.*;
import com.gui.projectmanagement.functions.Time;
import com.gui.projectmanagement.network.ProjectStream;
import com.gui.projectmanagement.network.StreamObject;
import com.gui.projectmanagement.network.Processing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewProjectControler implements Initializable, Network, Access, Window<InterfaceClientController>{

    @FXML
    private TextField name_project ;

    @FXML
    private TextArea describe ;

    @FXML
    private DatePicker end_date ;

    @FXML
    private TextField budget ;

    @FXML
    private Label start_date ;

    NewProjectManager npm = new NewProjectManager() ;

    ClientData client_data = null ;

    InterfaceClientController icc = null ;

    StreamObject so = null ;

    ProjectStream ps = new ProjectStream() ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String date_now = Time.getCurrentDate() ;
        start_date.setText("     Start date: " + date_now);
    }

    @FXML
    public void finish(ActionEvent event) {
        String project_name = this.name_project.getText() ;
        String describe = this.describe.getText() ;
        LocalDate end_date = this.end_date.getValue() ;
        String budget = this.budget.getText() ;

        if (npm.createProjectValidator(project_name, describe, end_date, budget)) {
            CreateProjectObject cpo = new CreateProjectObject(project_name, describe, Time.timeConversion(end_date), budget, client_data.getId()) ;
            ps.createProject(so, cpo);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        } else {
            error("Information is not valid!");
        }
    }

    @FXML
    public void close(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @Override
    public void loadAccess(ClientData client_data) {
        this.client_data = client_data ;
    }

    @Override
    public void loadInterface(InterfaceClientController icc) {
        this.icc = icc ;
    }

    @Override
    public void loadStreams(StreamObject so) {
        this.so = so ;
    }

    @Override
    public void loadProcessing(Processing pss) {

    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void error(String message) {
        URL url = NewProjectControler.class.getResource("/com/gui/projectmanagement/view/AlertError.fxml");
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
}
