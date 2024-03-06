package com.madcoke.mortageapplication.model;

import jakarta.persistence.Entity;

public class FileEntity {
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;



}
