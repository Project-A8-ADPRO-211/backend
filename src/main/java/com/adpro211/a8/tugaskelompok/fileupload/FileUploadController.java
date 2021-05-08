package com.adpro211.a8.tugaskelompok.fileupload;

import java.io.IOException;
import java.util.stream.Collectors;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.fileupload.storage.StorageFileNotFoundException;
import com.adpro211.a8.tugaskelompok.fileupload.storage.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/upload")
public class FileUploadController {

    private final StorageService storageService;

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    static class UrlStaticStorage {
        public String url;

        public UrlStaticStorage(String url) {
            this.url = url;
        }
    }

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(path ="/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(path = "/", produces = {"application/json"})
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String url = storageService.store(file);
            UrlStaticStorage toBeReturn = new UrlStaticStorage(url);
            String returned = mapToJson(toBeReturn);
            return ResponseEntity.ok(returned);
        } catch (Error | JsonProcessingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
