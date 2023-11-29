package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.exceptions.ResourceNotFoundException;
import com.example.soccer_club_backend.models.Tag;
import com.example.soccer_club_backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(int tagId) {
        return tagRepository.findById(tagId);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(int tagId, String updatedTag) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new ResourceNotFoundException("Tag not found id : " + tagId)
        );
        tag.setTagName(updatedTag);
        return tagRepository.save(tag);
    }

    public boolean deleteTag(int tagId) {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
            return true;
        }
        return false; // Handle not found case as needed
    }
}

