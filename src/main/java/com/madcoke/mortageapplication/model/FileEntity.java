package com.madcoke.mortageapplication.model;

import jakarta.persistence.Entity;

public class FileEntity {
    public String getFileName() {
        return csvFile;
    }

    public void setFileName(String fileName) {
        this.csvFile = csvFile;
    }

    private String csvFile;




}
