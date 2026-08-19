package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.FileObject;
import com.gui.projectmanagement.entity.ImageObject;

public class MessageObject {
    private String message_id ;
    private String sender_id ;
    private String receive_id ;
    private String type_message ;
    private String message ;
    private ImageObject image;
    private FileObject file ;
    private String day_send ;

    public MessageObject(String message_id, String sender_id, String receive_id, String type_message, String message, ImageObject image, FileObject file, String day_send) {
        this.message_id = message_id;
        this.sender_id = sender_id;
        this.receive_id = receive_id;
        this.type_message = type_message;
        this.message = message;
        this.image = image;
        this.file = file;
        this.day_send = day_send;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(String receive_id) {
        this.receive_id = receive_id;
    }

    public String getType_message() {
        return type_message;
    }

    public void setType_message(String type_message) {
        this.type_message = type_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ImageObject getImage() {
        return image;
    }

    public void setImage(ImageObject image) {
        this.image = image;
    }

    public FileObject getFile() {
        return file;
    }

    public void setFile(FileObject file) {
        this.file = file;
    }

    public String getDay_send() {
        return day_send;
    }

    public void setDay_send(String day_send) {
        this.day_send = day_send;
    }
}
