package com.example.soccer_club_backend.service;

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
public class NewsService {
    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
    private final PhotoRepository photoRepository;
//    private final FileStorageService fileStorageService;
    private final CloudinaryService cloudinaryService;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNewsById(int newsId) {
        return newsRepository.findById(newsId).orElseThrow(
                () -> new ResourceNotFoundException("News not found id : " + newsId)
        );
    }

    public News createNews(NewsDTO newsDTO, MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("File is required for creating news.");
        }

        Tag tag = tagRepository.findByTagName(newsDTO.getTagName());
        if (tag == null) {
            tag = tagRepository.save(new Tag().setTagName(newsDTO.getTagName()));
        }

        News news = new News();
        modelMapper.map(newsDTO, news);
        news.setTag(tag);
        News savedNews = newsRepository.save(news);
        Long newsId = Long.valueOf(savedNews.getNewsId());

        Photo photo = new Photo();
//        photo.setPhotoId(Math.toIntExact(newsId));
//        String filePatch = fileStorageService.storeFile(file);
        photo.setPath(cloudinaryService.uploadFile(file, "folder_1"));
        photo.setTagId(tag.getTagId());
        photoRepository.save(photo);

        savedNews.setPhoto(photo);
        return newsRepository.save(savedNews);
    }

    public News updateNews(int id, NewsDTO updatedNews, MultipartFile file) {
        News news = newsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("News not found id : " + id)
        );

        Tag tag = tagRepository.findByTagName(updatedNews.getTagName());
        if (tag == null) {
            tag = tagRepository.save(new Tag().setTagName(updatedNews.getTagName()));
        }

        if (file != null) {
            Photo photo = new Photo();
            photo.setPhotoId(Math.toIntExact(news.getNewsId()));
            photo.setPath(cloudinaryService.uploadFile(file, "folder_1"));
            photo.setTagId(tag.getTagId());
            photoRepository.save(photo);
            news.setPhoto(photo);
        }

        modelMapper.map(updatedNews, news); // copy field
        news.setTag(tag);

        return newsRepository.save(news);
    }

    public boolean deleteNews(int newsId) {
        if (newsRepository.existsById(newsId)) {
            newsRepository.deleteById(newsId);
            return true;
        }
        return false; // Handle not found case as needed
    }
}

