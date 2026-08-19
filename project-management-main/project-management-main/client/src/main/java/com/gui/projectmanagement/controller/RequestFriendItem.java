package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ContactObject;
import com.gui.projectmanagement.entity.RequestAddContact;
import com.gui.projectmanagement.network.Processing;
import com.gui.projectmanagement.network.ClientStream;
import com.gui.projectmanagement.network.StreamObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestFriendItem implements Initializable, Network {

    @FXML
    private Rectangle avata ;

    @FXML
    private Label notification ;

    @FXML
    private Button delete ;

    @FXML
    private Button accept ;

    RequestAddContact rac = null ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    String client_id ;

    StreamObject so;

    ClientStream sf = new ClientStream() ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadData(RequestAddContact rac) {
        if (rac != null) {
            this.rac = rac ;
            ContactObject client = rac.getContact_send_request() ;

            notification.setText(client.getFullname() + " has sent you a friend request.");

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
        } else {

        }
    }

    public void accept(ActionEvent event) {
        sf.acceptAddContact(so, rac.getRequest_id(), client_id, rac.getContact_send_request().getId());
    }

    public void loadClientId(String client_id) {
        this.client_id = client_id ;
    }


    @Override
    public void loadStreams(StreamObject so) {
        this.so = so ;
    }

    @Override
    public void loadProcessing(Processing pss) {

    }
}
