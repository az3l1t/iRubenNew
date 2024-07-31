package net.az3l1t.alphabet_server.service.mapper;

import net.az3l1t.alphabet_server.api.dto.LetterButtonRequest;
import net.az3l1t.alphabet_server.api.dto.LetterButtonWithInfoResponse;
import net.az3l1t.alphabet_server.core.entity.LetterButtonEntity;
import org.springframework.stereotype.Service;

@Service
public class LetterButtonMapper {
    public LetterButtonEntity toLetterButton(LetterButtonRequest request) {
        return new LetterButtonEntity(
                request.getCharacter(),
                request.getWord(),
                request.getSentence()
        );
    }

    public LetterButtonWithInfoResponse toLetterWithInfo(LetterButtonEntity letterButtonEntity) {
        return new LetterButtonWithInfoResponse(
                letterButtonEntity.getCharacter(),
                letterButtonEntity.getWord(),
                letterButtonEntity.getSentence()
        );
    }
}
