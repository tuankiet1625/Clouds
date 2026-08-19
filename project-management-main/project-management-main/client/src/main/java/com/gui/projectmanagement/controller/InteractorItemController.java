package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ContactObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class InteractorItemController implements Initializable, Data {

    @FXML
    private Label full_name ;

    @FXML
    private Rectangle avata ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void loadData(Object object) {
        ContactObject interactor = (ContactObject) object ;
        full_name.setText(interactor.getFullname());
        if (interactor.getAvata() == null) {
            ImagePattern ip = new ImagePattern(user_img) ;
            avata.setFill(ip);
        } else {
            Image avt = com.gui.projectmanagement.functions.Image.byteToImage(interactor.getAvata()) ;
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

}
