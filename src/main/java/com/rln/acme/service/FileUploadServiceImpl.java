package com.rln.acme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rln.acme.exception.InvalidFileException;
import com.rln.acme.model.FileUpload;
import com.rln.acme.repository.FileUploadRepository;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    // Retrieve file
    /* (non-Javadoc)
     * @see com.rln.acme.service.FileUploadService#findByFilename(java.lang.String)
     */
    @Override
    public FileUpload findByFilename(String filename) {
        return fileUploadRepository.findByFilename(filename);
    }

    @Override
    public List<FileUpload> listFilesByUserName(String username) {
        return fileUploadRepository.findByUsernameOrderByFilenameAsc(username);
    }

    @Override
    public void removeFile(String filename) throws InvalidFileException {
       final FileUpload file = findByFilename(filename);
       if (file!=null) {
           fileUploadRepository.delete(file);
        } else {
            throw new InvalidFileException("File " + filename + " not founded.");
       }

    }

    // Upload the file
    /* (non-Javadoc)
     * @see com.rln.acme.service.FileUploadService#uploadFile(com.rln.acme.model.FileUpload)
     */
    @Override
    public void uploadFile(FileUpload doc) {
        fileUploadRepository.save(doc);
    }
}