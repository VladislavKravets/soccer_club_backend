package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.models.Photo;
import com.example.soccer_club_backend.service.FileStorageService;
import com.example.soccer_club_backend.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class PhotoController {
    private final PhotoService photoRepository;
//    private final FileStorageService fileStorageService;

    @PostMapping("/api/photos/upload")
//    @Secured("ROLE_ADMIN")
    public Photo createPhoto(@RequestPart("file") MultipartFile file, @RequestPart("tag") String tag) {
        return photoRepository.createPhoto(tag,file);
    }

//    @RequestMapping(value = "/upload2", method = RequestMethod.POST,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
////    @Secured("ROLE_ADMIN")
//    public String fileUpload(@RequestParam("file") MultipartFile file) {
//        return fileStorageService.storeFile(file);
//    }


//    @GetMapping("/res/{filename}")
//    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
//        return fileStorageService.readFile(filename);
//    }
}
