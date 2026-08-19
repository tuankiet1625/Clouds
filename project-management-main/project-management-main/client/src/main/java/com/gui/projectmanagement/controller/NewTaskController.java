package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.authentication.NewTaskManager;
import com.gui.projectmanagement.entity.*;
import com.gui.projectmanagement.functions.Time;
import com.gui.projectmanagement.network.ProjectStream;
import com.gui.projectmanagement.network.ClientStream;
import com.gui.projectmanagement.network.StreamObject;
import com.gui.projectmanagement.network.Processing;
import com.gui.projectmanagement.ui.ProjectViewUI;
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
import java.util.List;
import java.util.ResourceBundle;

public class NewTaskController implements Initializable , Access, Data, Network{

    @FXML
    private TextField location ;

    @FXML
    private TextField name_task ;

    @FXML
    private TextArea request ;

    @FXML
    private ComboBox<ClientObject> members ;

    @FXML
    private DatePicker deadline ;

    @FXML
    private Label request_date ;

    ClientData client_data ;

    NewTaskManager ntm = new NewTaskManager() ;

    NewTaskControl ntc ;

    StreamObject so ;

    ClientStream sf = new ClientStream();

    ProjectStream ps = new ProjectStream() ;

    ProjectViewUI pvu = new ProjectViewUI() ;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate date_now = LocalDate.now() ;
        this.request_date.setText("     Request date: " + date_now.toString());
    }

    @FXML
    public void finish(ActionEvent event) {
        String name_task = this.name_task.getText() ;
        String request = this.request.getText() ;
        LocalDate deadline = this.deadline.getValue() ;
        ClientObject undertaker = this.members.getValue() ;
        if (ntm.validatorInfor("123", name_task, request, undertaker, deadline)) {
            CreateTaskProject ctp = new CreateTaskProject("Element",this.ntc.getParent().getValue().getProject_id(), this.ntc.getParent().getValue().getTask_id(), name_task, request, undertaker.getId(), Time.timeConversion(deadline), client_data.getId(), null) ;
            ps.sendRqTask(so, ctp) ;
            closeWindow(event);
        } else {
            error("Please enter valid information!");
        }

    }

    @FXML
    public void close(ActionEvent event) {
        closeWindow(event);
    }

    public void closeWindow(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void updateTaskTree(TreeItem<TaskPreview> parent, TaskPreview tp) {
        pvu.buildTreeItem(this.ntc.getParent(), tp);
    }

    @Override
    public void loadAccess(ClientData client_data) {
        this.client_data = client_data ;

    }


    @Override
    public void loadData(Object object) {
        NewTaskControl ntc = (NewTaskControl) object ;
        this.ntc = ntc ;

        // displayMembers(members, ntc.getMembers());
        members.getItems().addAll(ntc.getMembers()) ; 
    }

    @Override
    public void loadStreams(StreamObject so) {
        this.so = so ;
    }

    @Override
    public void loadProcessing(Processing pss) {

    }

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

    public void displayMembers(ComboBox list_member, List<ClientObject> members) {
        list_member.getItems().addAll(members);
        list_member.setCellFactory(param -> new ListCell<ClientObject>() {
            @Override
            protected void updateItem(ClientObject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getFullname());
                }
            }
        });
    }
}
