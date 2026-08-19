package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ContactObject;
import com.gui.projectmanagement.entity.FeedBackObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class FeedBackItem implements Initializable {
    @FXML
    private Rectangle avata ;

    @FXML
    private Label notification ;

    @FXML
    private Label project_name ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    FeedBackObject fbo ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void load(FeedBackObject fbo) {
        this.fbo = fbo ;
        ContactObject client = fbo.getSender_feedback() ;
        notification.setText(client.getFullname() + " has responded to your product.");
        project_name.setText(fbo.getProject_name());

        if (fbo != null) {
            if (client.getAvata() == null) {
                ImagePattern pi = new ImagePattern(user_img) ;
                avata.setFill(pi);
            } else {
                Image img = com.gui.projectmanagement.functions.Image.byteToImage(client.getAvata()) ;
                if (!img.isError()) {
                    ImagePattern pi = new ImagePattern(img) ;
                    avata.setFill(pi);
                }
            }
        }
    }
}
