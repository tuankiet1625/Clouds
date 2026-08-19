package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ClientData;
import com.gui.projectmanagement.entity.ContactObject;
import com.gui.projectmanagement.entity.Message;
import com.gui.projectmanagement.functions.Image;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageItemController implements Initializable {

    @FXML
    private HBox h_sender ;

    @FXML
    private Rectangle img_sender ;

    @FXML
    private HBox h_receive ;

    @FXML
    private Rectangle avata ;

    @FXML
    private Rectangle img_receive ;

    double MAX_WIDTH = 300 ;
    double MAX_HEIGHT = 400 ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    javafx.scene.image.Image user_img = new javafx.scene.image.Image(getClass().getResourceAsStream(imaPart));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // img_sender.setOnMouseClicked();
    }

    public void load(ClientData client_data, ContactObject interactor, Message message) {
        if (client_data.getId().equals(message.getSender_id())) {
            h_sender.setVisible(true);
            h_receive.setVisible(false);
            javafx.scene.image.Image img = Image.byteToImage(message.getImage().getImage_data()) ;
            if (!img.isError()) {
                ImagePattern img_pt = new ImagePattern(img) ;
                double img_width = img.getWidth() ;
                double img_height = img.getHeight() ;
                double raito_img = img_width / img_height ;

                if (img_width <= MAX_WIDTH) {
                    img_sender.setWidth(img_width);
                    img_sender.setHeight(img_width * ( 1 / raito_img ));
                } else {
                    img_sender.setWidth(MAX_WIDTH);
                    img_sender.setHeight(MAX_WIDTH * ( 1 / raito_img ));
                }

                img_sender.setFill(img_pt);
            }
        } else {
            h_receive.setVisible(true);
            h_sender.setVisible(false);
            javafx.scene.image.Image img = Image.byteToImage(message.getImage().getImage_data()) ;
            if (!img.isError()) {
                ImagePattern img_pt = new ImagePattern(img) ;
                double img_width = img.getWidth() ;
                double img_height = img.getHeight() ;
                double raito_img = img_width / img_height ;

                if (img_width <= MAX_WIDTH) {
                    img_receive.setWidth(img_width);
                    img_receive.setHeight(img_width * ( 1 / raito_img ));
                } else {
                    img_receive.setWidth(MAX_WIDTH);
                    img_receive.setHeight(MAX_WIDTH * ( 1 / raito_img ));
                }


                img_receive.setFill(img_pt);
            }

            if (interactor.getAvata() == null) {
                ImagePattern pi = new ImagePattern(user_img) ;
                avata.setFill(pi);
            } else {
                javafx.scene.image.Image img_avt = com.gui.projectmanagement.functions.Image.byteToImage(interactor.getAvata()) ;
                if (!img_avt.isError()) {
                    ImagePattern pi = new ImagePattern(img_avt) ;
                    avata.setFill(pi);
                }
            }
        }
    }
}
