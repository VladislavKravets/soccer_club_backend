package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.models.Tag;
import com.example.soccer_club_backend.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @PutMapping("/{tagId}")
    @Secured("ROLE_ADMIN")
    public Tag updateNews(@PathVariable int tagId, @RequestBody String tagName) {
        return tagService.updateTag(tagId, tagName);
    }

    @DeleteMapping("/{newsId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteNews(@PathVariable int newsId) {
        if (tagService.deleteTag(newsId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
