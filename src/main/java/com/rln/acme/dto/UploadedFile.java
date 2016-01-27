package com.rln.acme.dto;

import java.io.Serializable;


public class UploadedFile implements Serializable {

    private String name;

    private int size;

    private String type;

    public UploadedFile(final String name, final int size, final String type) {

        this.name = name;
        this.size = size;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }

}
