package net.az3l1t.alphabet_server.service.mapper;

import net.az3l1t.alphabet_server.api.dto.LetterRequest;
import net.az3l1t.alphabet_server.core.entity.Letter;
import org.springframework.stereotype.Service;

@Service
public class LetterMapper {
    public Letter toLetter(LetterRequest request) {
        return new Letter(
                request.getCharacter()
        );
    }
}
