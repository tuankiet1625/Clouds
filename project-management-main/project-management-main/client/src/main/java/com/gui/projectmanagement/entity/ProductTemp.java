package com.gui.projectmanagement.entity;

import java.io.File;

public class ProductTemp {
    private String uploader ;
    private File file_data ;
    private String file_name ;
    private int file_size ;

    public ProductTemp(String uploader, File file_data, String file_name, int file_size) {
        this.uploader = uploader;
        this.file_data = file_data;
        this.file_name = file_name;
        this.file_size = file_size;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public File getFile_data() {
        return file_data;
    }

    public void setFile_data(File file_data) {
        this.file_data = file_data;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }
}
