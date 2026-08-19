package com.gui.projectmanagement.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileFunction {
    public static byte[] fileToByte(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            long file_size = file.length();

            byte[] file_bytes = new byte[(int) file_size];

            int bytes_read = fileInputStream.read(file_bytes);

            fileInputStream.close();

            if (bytes_read == file_size) {
                return file_bytes ;
            } else {
                return null ;
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return null ;
    }
}
