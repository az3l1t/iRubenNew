package net.az3l1t.alphabet_server.service.impl;

import jakarta.annotation.PostConstruct;
import net.az3l1t.alphabet_server.api.dto.LetterRequest;
import net.az3l1t.alphabet_server.core.entity.Letter;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    @PostConstruct
    public void init() {
        List<String> armenianAlphabet = Arrays.asList(
                "Ա", "Բ", "Գ", "Դ", "Ե", "Զ", "Է", "Ը", "Թ", "Ժ",
                "Ի", "Լ", "Խ", "Ծ", "Կ", "Հ", " Ձ", "Ղ", "Ճ", "Մ",
                "Յ", "Ն", "Շ", "Ո", "Չ", "Պ", "Ջ", "Ռ", "Ս", "Վ",
                "Տ", "Ր", "Ց", "Ւ", "Փ", "Ք", "Օ", "Ֆ"
        );

        if (letterRepository.count() == 0) {  // Проверяем, пуст ли репозиторий
            for (String character : armenianAlphabet) {
                LetterRequest request = new LetterRequest();
                request.setCharacter(character);
                addLetter(request);
            }
        }
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
