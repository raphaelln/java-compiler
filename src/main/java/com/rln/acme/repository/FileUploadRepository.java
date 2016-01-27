package com.rln.acme.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rln.acme.model.FileUpload;

public interface FileUploadRepository extends MongoRepository<FileUpload, Long> {

    FileUpload findByFilename(String filename);

    List<FileUpload> findByUsernameOrderByFilenameAsc(String username);
}