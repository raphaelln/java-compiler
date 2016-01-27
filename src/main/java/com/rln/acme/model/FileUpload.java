package com.rln.acme.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FileUpload {

    @Id
    private String filename;

    private byte[] file;

    private String mimeType;

    private String username;

    public FileUpload() {
        // Default Constructor
    }

    public FileUpload(String filename, byte[] file, String mimeType, String username) {

        this.file = file;
        this.filename = filename;
        this.mimeType = mimeType;
        this.username = username;
    }

    public byte[] getFile() {
        return file;
    }

    public String getFilename() {
        return filename;
    }


    public String getMimeType() {
        return mimeType;
    }

    public String getUsername() {
        return username;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}