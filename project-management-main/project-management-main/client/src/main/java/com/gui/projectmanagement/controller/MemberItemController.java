package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ClientObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberItemController implements Initializable {
    @FXML
    private Rectangle avata ;

    @FXML
    private Label fullname ;

    @FXML
    private Label role ;

    ClientObject member = null ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadMemberData(ClientObject member) {
        this.member = member ;

        // UI
        if (member.getAvata() != null) {
            Image avt = com.gui.projectmanagement.functions.Image.byteToImage(member.getAvata()) ;
            if (!avt.isError()) {
                setAvata(this.avata, avt);
            } else {
                setAvata(this.avata, user_img);
            }
        } else {
            setAvata(this.avata, user_img);
        }

        this.fullname.setText(member.getFullname());
        this.role.setText(member.getRole());
    }

    public void setAvata(Rectangle rectangle, Image image) {
        ImagePattern pattern = new ImagePattern(image);
        rectangle.setFill(pattern);
    }
}
