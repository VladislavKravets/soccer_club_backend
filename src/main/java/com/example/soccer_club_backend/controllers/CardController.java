package com.example.soccer_club_backend.controllers;

import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{cardId}")
    public Card getCardById(@PathVariable Integer cardId) {
        return cardService.getCardById(cardId);
    }

    @PostMapping
    public Card createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }

    @PutMapping("/{cardId}")
    public Card updateCard(@PathVariable Integer cardId, @RequestBody Card updatedCard) {
        return cardService.updateCard(cardId, updatedCard);
    }

    @DeleteMapping("/{cardId}")
    public boolean deleteCard(@PathVariable Integer cardId) {
        return cardService.deleteCard(cardId);
    }
}

