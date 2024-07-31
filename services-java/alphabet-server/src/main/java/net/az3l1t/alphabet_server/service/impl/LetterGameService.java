package net.az3l1t.alphabet_server.service.impl;

import jakarta.annotation.PostConstruct;
import net.az3l1t.alphabet_server.api.dto.LetterGameRequest;
import net.az3l1t.alphabet_server.api.dto.LetterGameResponse;
import net.az3l1t.alphabet_server.core.entity.LetterGame;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterGameRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterGameMapper;
import org.springframework.stereotype.Service;

@Service
public class LetterGameService {
    private final LetterGameRepository letterGameRepository;
    private final LetterGameMapper letterGameMapper;

    public LetterGameService(LetterGameRepository letterGameRepository, LetterGameMapper letterGameMapper) {
        this.letterGameRepository = letterGameRepository;
        this.letterGameMapper = letterGameMapper;
    }

    @PostConstruct
    public void init() {
        if (letterGameRepository.count() == 0) {
            initArmGameData();
        }
    }

    private void initArmGameData() {

    }

    public LetterGameResponse createGameByCharacter(LetterGameRequest request){
        LetterGame letterGame = letterGameMapper.toLetterGame(request);
        letterGameRepository.save(letterGame);
        return letterGameMapper.toLetterGameResponse(letterGame);
    }

    public LetterGameResponse findGameByCharacter(String character){
        LetterGame letterGame = letterGameRepository.findById(character)
                .orElseThrow(
                        ()->new RuntimeException(
                                "The game was not found : %s".formatted(character)
                        )
                );
        return new LetterGameResponse(letterGame.getCharacter(), letterGame.getListOfWords());
    }
}
