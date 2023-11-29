package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.dtos.PhotoDTO;
import com.example.soccer_club_backend.dtos.news.NewsDTO;
import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.News;
import com.example.soccer_club_backend.models.Photo;
import com.example.soccer_club_backend.models.Tag;
import com.example.soccer_club_backend.repository.NewsRepository;
import com.example.soccer_club_backend.repository.PhotoRepository;
import com.example.soccer_club_backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;
    private final FileStorageService fileStorageService;

//    private final ModelMapper modelMapper = new ModelMapper();

    public Photo createPhoto(String tagName, MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("File is required for creating photo.");
        }
        Tag tag = tagRepository.findByTagName(tagName);
        if (tag == null) {
            throw new ResourceNotFoundException("Tag not found name : " + tagName);
        }
        String filePatch = fileStorageService.storeFile(file);
        Photo photo = new Photo();
        photo.setTagId(tag.getTagId());
        photo.setPath(filePatch);

        return photoRepository.save(photo);
    }
}
