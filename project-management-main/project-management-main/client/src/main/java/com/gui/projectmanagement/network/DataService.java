package com.gui.projectmanagement.network;

import com.gui.projectmanagement.converter.JsonConverter;
import com.gui.projectmanagement.security.AESEncryption;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;

public class DataService {
    public void send(DataOutputStream dos, Object object) {
        try {
            String object_json = JsonConverter.convertObjectToJson(object);
            String object_json_crypto = AESEncryption.encrypt(object_json);
            byte[] object_json_crypto_byte = object_json_crypto.getBytes();
            int size = object_json_crypto_byte.length;
            dos.writeInt(size);
            dos.flush();
            dos.write(object_json_crypto_byte, 0, size);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T receive(DataInputStream dis, Type type) {
        try {
            int size = dis.readInt() ;
            byte[] object_json_crypto_byte = new byte[size];
            dis.readFully(object_json_crypto_byte, 0, size);
            String object_json_crypto = new String(object_json_crypto_byte);
            String object_json = AESEncryption.decrypt(object_json_crypto);
            T object = JsonConverter.convertJsonToObject(object_json, type);
            return object;
            // showAlert(Alert.AlertType.ERROR, "Connection errors","Connection dropped!"); : Socket exception
        } catch (IOException i) {
            throw new RuntimeException(i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
