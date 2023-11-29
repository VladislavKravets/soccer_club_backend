package com.example.soccer_club_backend.service;

import com.example.soccer_club_backend.models.Card;
import com.example.soccer_club_backend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(Integer cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Card updateCard(Integer cardId, Card updatedCard) {
        if (cardRepository.existsById(cardId)) {
            updatedCard.setCardId(cardId);
            return cardRepository.save(updatedCard);
        }
        return null; // Handle not found case as needed
    }

    public boolean deleteCard(Integer cardId) {
        if (cardRepository.existsById(cardId)) {
            cardRepository.deleteById(cardId);
            return true;
        }
        return false; // Handle not found case as needed
    }
}

