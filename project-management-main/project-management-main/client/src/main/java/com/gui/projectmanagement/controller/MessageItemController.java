package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ClientData;
import com.gui.projectmanagement.entity.ContactObject;
import com.gui.projectmanagement.entity.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageItemController implements Initializable {
    @FXML
    private AnchorPane a_receive ;

    @FXML
    private Rectangle avata ;

    @FXML
    private Label message_receive ;

    @FXML
    private HBox h_sender ;

    @FXML
    private Label message_sender ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /*
    public void load(ClientData client_data, ContactObject interactor, Message message) {
        if (message.getSender_id().equals(client_data.getId())) { // Sender
            if (message.getType_message().equals("Message")) {
                h_sender_message.setVisible(true);
                message_sender.setText(message.getMessage());
            } else if (message.getType_message().equals("Image")) {
                h_sender_img.setVisible(true);
            } else if (message.getType_message().equals("File")) {
                a_sender_file.setVisible(true);
            }
        } else { // Receiver
            if (message.getType_message().equals("Message")) {
                a_receive_message.setVisible(true);
                message_receiver.setText(message.getMessage());
            } else if (message.getType_message().equals("Image")) {
                h_receive_img.setVisible(true);
            } else if (message.getType_message().equals("File")) {
                a_receive_file.setVisible(true);
            }
        }
    }
     */

    public void load(ClientData client_data, ContactObject interactor, Message message) {
        if (message.getSender_id().equals(client_data.getId())) {
            h_sender.setVisible(true);
            message_sender.setText(message.getMessage());
        } else {
            a_receive.setVisible(true);
            message_receive.setText(message.getMessage());
            if (interactor.getAvata() == null) {
                ImagePattern pi = new ImagePattern(user_img) ;
                avata.setFill(pi);
            } else {
                Image img = com.gui.projectmanagement.functions.Image.byteToImage(interactor.getAvata()) ;
                if (!img.isError()) {
                    ImagePattern pi = new ImagePattern(img) ;
                    avata.setFill(pi);
                }
            }
        }
    }

}
