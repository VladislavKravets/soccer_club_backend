package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.dtos.PhotoDTO;
import com.example.soccer_club_backend.models.Photo;
import com.example.soccer_club_backend.repository.PhotoRepository;
import com.example.soccer_club_backend.service.FileStorageService;
import com.example.soccer_club_backend.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class PhotoController {
    private final PhotoService photoRepository;
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public Photo createPhoto(@RequestPart("file") MultipartFile file, @RequestPart("tag") String tag) {
//        String filePatch = fileStorageService.storeFile(file);
        return photoRepository.createPhoto(tag,file);
    }
}
