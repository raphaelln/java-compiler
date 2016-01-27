package com.rln.acme.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rln.acme.dto.UploadedFile;
import com.rln.acme.exception.InvalidFileException;
import com.rln.acme.model.FileUpload;
import com.rln.acme.service.FileUploadService;


@RestController()
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/delete/{filename:.+}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("filename") String filename) {

        try {
            fileUploadService.removeFile(filename);
        } catch (final InvalidFileException e) {
            return new ResponseEntity<>("{error: " + e.getMessage() + "}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity downloadFile(@RequestParam("filename") String filename) {

        final FileUpload fileUpload = fileUploadService.findByFilename(filename);

        // No file found based on the supplied filename
        if (fileUpload == null) {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }

        // Generate the http headers with the file properties
        final HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment; filename=" + fileUpload.getFilename());

        // Split the mimeType into primary and sub types
        String primaryType, subType;
        try {
            primaryType = fileUpload.getMimeType().split("/")[0];
            subType = fileUpload.getMimeType().split("/")[1];
        } catch (IndexOutOfBoundsException | NullPointerException ex) {
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType(new MediaType(primaryType, subType));

        return new ResponseEntity<>(fileUpload.getFile(), headers, HttpStatus.OK);
    }

    private String getCurrentUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String name = auth.getName(); // get logged in username
        return name;
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public @ResponseBody List<UploadedFile> listFiles() {

        final List<UploadedFile> list = new ArrayList<UploadedFile>();
        final List<FileUpload> uploadList = fileUploadService.listFilesByUserName(getCurrentUser());
        if (CollectionUtils.isNotEmpty(uploadList)) {
            uploadList.stream().forEach(p -> list.add(new UploadedFile(p.getFilename(), p.getFile().length, p.getMimeType())));
        }
        return list;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity uploadFile(MultipartHttpServletRequest request) {

        try {
            final Iterator<String> itr = request.getFileNames();

            while (itr.hasNext()) {
                final String uploadedFile = itr.next();
                final MultipartFile file = request.getFile(uploadedFile);
                final String mimeType = file.getContentType();
                final String filename = file.getOriginalFilename();
                final byte[] bytes = file.getBytes();

                final FileUpload newFile = new FileUpload(filename, bytes, mimeType, getCurrentUser());

                fileUploadService.uploadFile(newFile);
            }
        } catch (final Exception e) {
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
