package com.example.soccer_club_backend.controllers;


import com.example.soccer_club_backend.dtos.ReferDTO;
import com.example.soccer_club_backend.models.Refer;
import com.example.soccer_club_backend.service.ReferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refer")
@CrossOrigin(origins = "http://localhost:3000") // Разрешить запросы с указанного источника
@AllArgsConstructor
public class ReferController {
    private final ReferService referService;

    @GetMapping
    public List<Refer> getAllRefer() {
        return referService.getAllRefers();
    }

    @GetMapping("/{referId}")
    public Refer getRefereeId(int referId) {
        return referService.getReferId(referId);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public Refer createRefer(@RequestBody @Valid ReferDTO referDTO) {
        return referService.createRefer(referDTO);
    }

//    @PutMapping("/{playerId}")
//    @Secured("ROLE_ADMIN")
//    public Player updatePlayer(@PathVariable int playerId, @RequestBody @Valid PlayerDTO updatePlayer) {
//        return playerService.updatePlayer(playerId, updatePlayer);
//    }

//    @DeleteMapping("/{playerId}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<Void> deletePlayer(@PathVariable int playerId) {
//        if (playerService.deletePlayer(playerId)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
