package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ContactObject;
import com.gui.projectmanagement.network.Processing;
import com.gui.projectmanagement.network.ClientStream;
import com.gui.projectmanagement.network.StreamObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactProjectController implements Initializable, Data, Network {

    @FXML
    private Rectangle avata  ;

    @FXML
    private Label name_client  ;

    @FXML
    private Button btn_add  ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    StreamObject so = null ;

    ContactObject contact = null ;

    ClientStream sf = new ClientStream() ;

    String project_id ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_add.setOnAction(event -> {
            sf.sendRqAddMember(so, contact.getId(), project_id);
            System.out.println("Aa1");
        });
    }


    @Override
    public void loadData(Object object) {
        ContactObject contact = (ContactObject) object ;
        this.contact = contact ;

        if (contact != null) {
            name_client.setText(contact.getFullname());

            if (contact.getAvata() != null) {
                Image img = com.gui.projectmanagement.functions.Image.byteToImage(contact.getAvata()) ;
                if (!img.isError()) {
                    ImagePattern pi = new ImagePattern(img) ;
                    avata.setFill(pi);
                } else {
                    ImagePattern pi = new ImagePattern(user_img) ;
                    avata.setFill(pi);
                }
            } else {
                ImagePattern pi = new ImagePattern(user_img) ;
                avata.setFill(pi);
            }
        }
    }


    @Override
    public void loadStreams(StreamObject so) {
        this.so = so ;
    }

    @Override
    public void loadProcessing(Processing pss) {

    }

    public void loadProjectId(String project_id) {
        this.project_id = project_id ;
    }
}
