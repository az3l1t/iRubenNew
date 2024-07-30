package net.az3l1t.alphabet_server.service.impl;

import net.az3l1t.alphabet_server.api.dto.LetterButtonRequest;
import net.az3l1t.alphabet_server.api.dto.LetterButtonResponse;
import net.az3l1t.alphabet_server.api.dto.LetterButtonWithInfoResponse;
import net.az3l1t.alphabet_server.api.dto.LetterCharacterRequest;
import net.az3l1t.alphabet_server.core.entity.LetterButtonEntity;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterButtonRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterButtonMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterButtonService {
    private final LetterButtonRepository letterButtonRepository;
    private final LetterButtonMapper mapper;

    public LetterButtonService(LetterButtonRepository letterButtonRepository, LetterButtonMapper mapper) {
        this.letterButtonRepository = letterButtonRepository;
        this.mapper = mapper;
    }

    public LetterButtonResponse createTheButtonInfo(LetterButtonRequest request){
        LetterButtonEntity buttonInfo = mapper.toLetterButton(request);
        if(letterButtonRepository.findById(buttonInfo.getCharacter()).isPresent()){
            throw new RuntimeException("LetterButton was not found : %s".formatted(buttonInfo.getCharacter()));
        }
        letterButtonRepository.save(buttonInfo);
        return new LetterButtonResponse(buttonInfo.getCharacter());
    }

    public LetterButtonWithInfoResponse getButtonInfo(LetterCharacterRequest request){
        LetterButtonEntity letterButtonEntity = letterButtonRepository.findById(request.getCharacter())
                .orElseThrow(
                        ()->new RuntimeException("LetterButton was found : %s".formatted(request.getCharacter()))
                );
        return  new LetterButtonWithInfoResponse(
                letterButtonEntity.getCharacter(),
                letterButtonEntity.getUrlToPhoto(),
                letterButtonEntity.getWord(),
                letterButtonEntity.getSentence()
        );
    }

    public List<LetterButtonWithInfoResponse> getAllButtonsInfo(){
        return letterButtonRepository.findAll()
                .stream()
                .map(mapper::toLetterWithInfo)
                .toList();
    }
}
