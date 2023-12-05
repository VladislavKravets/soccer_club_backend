package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.dtos.PhotoDTO;
import com.example.soccer_club_backend.models.Photo;
import com.example.soccer_club_backend.repository.PhotoRepository;
import com.example.soccer_club_backend.service.FileStorageService;
import com.example.soccer_club_backend.service.PhotoService;
import io.github.classgraph.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
//@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class PhotoController {
    private final PhotoService photoRepository;
    private final FileStorageService fileStorageService;


    @PostMapping("/api/photos/upload")
    public Photo createPhoto(@RequestPart("file") MultipartFile file, @RequestPart("tag") String tag) {
        return photoRepository.createPhoto(tag,file);
    }

    @RequestMapping(value = "/upload2", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        return fileStorageService.storeFile(file);
    }


    @GetMapping("/res/{filename}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        return fileStorageService.readFile(filename);
    }
}
