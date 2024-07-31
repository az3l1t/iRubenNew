package net.az3l1t.alphabet_server.service.mapper;

import net.az3l1t.alphabet_server.api.dto.LetterGameRequest;
import net.az3l1t.alphabet_server.api.dto.LetterGameResponse;
import net.az3l1t.alphabet_server.core.entity.LetterGame;
import org.springframework.stereotype.Service;

@Service
public class LetterGameMapper {
    public LetterGame toLetterGame(LetterGameRequest request) {
        return new LetterGame(
                request.getCharacter(),
                request.getListOfWords()
        );
    }
    public LetterGameResponse toLetterGameResponse(LetterGame letterGame) {
        return new LetterGameResponse(
                letterGame.getCharacter(),
                letterGame.getListOfWords()
        );
    }
}
