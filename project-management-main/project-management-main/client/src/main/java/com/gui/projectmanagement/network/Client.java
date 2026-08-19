package com.gui.projectmanagement.network;

import com.gui.projectmanagement.entity.TypeData;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.List;

public class Client {
    public StreamObject start(String host, int port) {
        try {
            Socket socket = new Socket(host, port) ;
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            StreamObject so = new StreamObject(socket, dis, dos) ;
            return so ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
