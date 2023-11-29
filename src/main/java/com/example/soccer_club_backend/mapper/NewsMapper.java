package com.example.soccer_club_backend.mapper;

import com.example.soccer_club_backend.dtos.news.NewsGetAllDTO;
import com.example.soccer_club_backend.models.News;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(source = "tag.tagName", target = "tagName")
    @Mapping(source = "photo.path", target = "urlPhoto")
    NewsGetAllDTO toNewsDTO(News news);

    @IterableMapping(elementTargetType = NewsGetAllDTO.class)
    List<NewsGetAllDTO> toNewsDTOList(List<News> news);
}
