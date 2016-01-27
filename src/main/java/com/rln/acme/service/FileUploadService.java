package com.rln.acme.service;

import java.util.List;

import com.rln.acme.exception.InvalidFileException;
import com.rln.acme.model.FileUpload;

public interface FileUploadService {

    // Retrieve file
    FileUpload findByFilename(String filename);

    /**
     * Retrieves list of uploaded files by user
     *
     * @param username
     * @return
     */
    List<FileUpload> listFilesByUserName(final String username);

    /**
     * Remove file from the database
     * @param filename
     */
    void removeFile(final String filename) throws InvalidFileException;

    // Upload the file
    void uploadFile(FileUpload doc);

}
