package com.gui.projectmanagement.functions;

import java.io.ByteArrayInputStream;

public class Image {
    public static javafx.scene.image.Image byteToImage(byte[] image_byte) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(image_byte);
        javafx.scene.image.Image image = new javafx.scene.image.Image(inputStream);
        return image ;
    }
}
