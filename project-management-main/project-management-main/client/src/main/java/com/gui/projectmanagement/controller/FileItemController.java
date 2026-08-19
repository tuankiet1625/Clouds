package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.ClientData;
import com.gui.projectmanagement.entity.ContactObject;
import com.gui.projectmanagement.entity.FileObject;
import com.gui.projectmanagement.entity.Message;
import com.gui.projectmanagement.network.ClientStream;
import com.gui.projectmanagement.network.StreamObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class FileItemController implements Initializable {
    @FXML
    private AnchorPane a_sender ;

    @FXML
    private Label file_name_sender ;

    @FXML
    private Label file_size_sender ;

    @FXML
    private AnchorPane a_receive ;

    @FXML
    private AnchorPane a_file_receive ;

    @FXML
    private Rectangle avata ;

    @FXML
    private Label file_name_receive ;

    @FXML
    private Label file_size_receive ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));

    FileObject fo = null ;

    StreamObject so ;

    ClientStream sf = new ClientStream() ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        a_sender.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (fo != null) {
                    sf.sendRqFile(so, fo.getFile_id());
                }
            }
        });

        a_file_receive.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (fo != null) {
                    sf.sendRqFile(so, fo.getFile_id());
                }
            }
        });

    }

    public void load(ClientData client_data, ContactObject interactor, Message message, StreamObject so) {
        this.so = so ;
        fo = message.getFile_id() ;
        if (client_data.getId().equals(message.getSender_id())) { // Sender
            a_sender.setVisible(true);
            a_receive.setVisible(false);
            FileObject file = message.getFile_id() ;
            if (file != null) {
                file_name_sender.setText(file.getName_file());
                setFileSize(file_size_sender, file.getFile_size());
            }
        } else {
            a_receive.setVisible(true);
            a_sender.setVisible(false);
            FileObject file = message.getFile_id() ;
            if (file != null) {
                file_name_receive.setText(file.getName_file());
                setFileSize(file_size_receive, file.getFile_size());
            }

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

    public void setFileSize(Label file_size, int size) {
        DecimalFormat df = new DecimalFormat("#.##");
        double kb_size_file = (double) size / 1024 ;
        double mb_size_file = kb_size_file / 1024 ;
        if (size < 52428800){
            if (size < 500){
                file_size.setText(size + " Bytes");
            } else {
                if (kb_size_file < 500){
                    file_size.setText(Double.parseDouble(df.format(kb_size_file)) + " KB");
                } else {
                    file_size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                }
            }
        } else {
            file_size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
        }
    }

}
