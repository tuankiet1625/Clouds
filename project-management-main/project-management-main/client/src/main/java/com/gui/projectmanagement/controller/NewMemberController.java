package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ContactObject;
import com.gui.projectmanagement.entity.ProjectControl;
import com.gui.projectmanagement.network.Processing;
import com.gui.projectmanagement.network.StreamObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewMemberController implements Initializable, Data, Network {

    @FXML
    private AnchorPane a_search ;

    @FXML
    private AnchorPane a_contact ;

    @FXML
    private Button btn_contact ;

    @FXML
    private Button btn_search ;

    @FXML
    private ListView list ;

    StreamObject so = null ;

    ProjectControl pc ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_contact.setOnAction(event -> {
            btn_contact.setStyle("-fx-background-color:  #d3d3d3;");
            btn_search.setStyle("-fx-background-color: transparent;");
            a_search.setVisible(false);
            a_contact.setVisible(true);
        });

        btn_search.setOnAction(event -> {
            btn_search.setStyle("-fx-background-color:  #d3d3d3;");
            btn_contact.setStyle("-fx-background-color: transparent;");
            a_contact.setVisible(false);
            a_search.setVisible(true);
        });

    }

    @FXML
    public void close(ActionEvent event) {
        Stage present_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        present_window.close();
    }

    @Override
    public void loadData(Object object) {
        List<ContactObject> list_contact = (List<ContactObject>) object ;
        displayListContact(list_contact) ;
    }

    public void displayListContact(List<ContactObject> list_contact) {
        ObservableList<ContactObject> contacts_obs = FXCollections.observableArrayList(list_contact);
        list.setItems(contacts_obs);
        list.setCellFactory(param -> new ListCell<ContactObject>() {

            @Override
            protected void updateItem(ContactObject contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/ContactProject.fxml"));
                        AnchorPane anchorpane = loader.load();
                        anchorpane.getStyleClass().add("contact_item" );
                        ContactProjectController cpc = loader.getController() ;
                        cpc.loadData(contact);
                        cpc.loadStreams(so);
                        cpc.loadProjectId(pc.getPp().getId());
                        setGraphic(anchorpane);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    public void loadStreams(StreamObject so) {
        this.so = so ;
    }

    @Override
    public void loadProcessing(Processing pss) {

    }

    public void loadProjectControl(ProjectControl pc) {
        this.pc = pc ;
    }
}
