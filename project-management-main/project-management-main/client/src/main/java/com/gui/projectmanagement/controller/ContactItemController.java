package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ContactObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactItemController implements Initializable {

    @FXML
    private Rectangle avata ;

    @FXML
    private Label fullname ;

    @FXML
    private Button accept ;

    @FXML
    private Button delete ;

    @FXML
    private Button message ;

    @FXML
    private HBox hb_1 ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    InterfaceClientController icc ;

    ContactObject contact ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadContact(ContactObject contact) {
        this.contact = contact ;
        hb_1.setVisible(false);
        message.setVisible(true);
        fullname.setText(contact.getFullname());
        if (contact.getAvata() == null) {
            setAvata(this.avata, user_img);
        } else {
            Image avt = com.gui.projectmanagement.functions.Image.byteToImage(contact.getAvata()) ;
            if (!avt.isError()) {
                setAvata(this.avata, avt);
            } else {
                setAvata(this.avata, user_img);
            }
        }
    }

    public void loadICC(InterfaceClientController icc) {
        this.icc = icc ;
    }

    public void loadRequest(ContactObject contact) {
        message.setVisible(false);
        hb_1.setVisible(true);
        fullname.setText(contact.getFullname());
        if (contact.getAvata() == null) {
            setAvata(this.avata, user_img);
        } else {
            Image avt = com.gui.projectmanagement.functions.Image.byteToImage(contact.getAvata()) ;
            if (!avt.isError()) {
                setAvata(this.avata, avt);
            } else {
                setAvata(this.avata, user_img);
            }
        }
    }

    public void setAvata(Rectangle rectangle, Image image) {
        ImagePattern pattern = new ImagePattern(image);
        rectangle.setFill(pattern);
    }

    @FXML
    public void messageWithContact(ActionEvent event) {
        if (contact != null) {
            icc.updateMessage(contact);
        }
    }
}
