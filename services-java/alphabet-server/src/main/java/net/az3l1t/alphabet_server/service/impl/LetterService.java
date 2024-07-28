package net.az3l1t.alphabet_server.service.impl;

import net.az3l1t.alphabet_server.api.dto.LetterRequest;
import net.az3l1t.alphabet_server.core.entity.Letter;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LetterService {
    private final LetterRepository letterRepository;
    private final LetterMapper mapper;

    public LetterService(LetterRepository letterRepository, LetterMapper mapper) {
        this.letterRepository = letterRepository;
        this.mapper = mapper;
    }

    public List<Letter> getAllLetter(){
        return letterRepository.findAll();
    }

    public Optional<Letter> getLetterById(Integer id){
        return letterRepository.findById(id);
    }

    public Letter addLetter(LetterRequest request){
        var letter = mapper.toLetter(request);
        letterRepository.save(letter);
        return letter;
    }

    public String deleteLetter(Integer id) {
        if(letterRepository.findById(id).isPresent()){
            letterRepository.deleteById(id);
            return "Was deleted : %d".formatted(id);
        } else {
            throw new RuntimeException("" +
                    "Letter was not found : %d".formatted(id));
        }
    }
}
