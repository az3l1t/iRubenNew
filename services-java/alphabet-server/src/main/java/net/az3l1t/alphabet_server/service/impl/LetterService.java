package net.az3l1t.alphabet_server.service.impl;

import jakarta.annotation.PostConstruct;
import net.az3l1t.alphabet_server.api.dto.LetterRequest;
import net.az3l1t.alphabet_server.core.entity.Letter;
import net.az3l1t.alphabet_server.infrastructure.repository.LetterRepository;
import net.az3l1t.alphabet_server.service.mapper.LetterMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
                "ա", "բ", "գ", "դ", "ե", "զ", "է", "ը", "ու", "վ",
                "օ", "շ", "կ", "խ", "ծ", "լ", "մ", "ն", "տ", "ռ",
                "փ", "ս", "ֆ", "ջ"
        );

        if (letterRepository.count() == 0) {  // Проверяем, пуст ли репозиторий
            for (String character : armenianAlphabet) {
                LetterRequest request = new LetterRequest();
                request.setCharacter(character);
                addLetter(request);
            }
        }
    }
    @Async
    public CompletableFuture<List<Letter>> getAllLetter(){
        return CompletableFuture.completedFuture(letterRepository.findAll());
    }
    @Async
    public CompletableFuture<Optional<Letter>> getLetterById(Integer id){
        return CompletableFuture.completedFuture(letterRepository.findById(id));
    }
    @Async
    public CompletableFuture<Letter> addLetter(LetterRequest request){
        var letter = mapper.toLetter(request);
        letterRepository.save(letter);
        return CompletableFuture.completedFuture(letter);
    }
    @Async
    public CompletableFuture<String> deleteLetter(Integer id) {
        if(letterRepository.findById(id).isPresent()){
            letterRepository.deleteById(id);
            return CompletableFuture.completedFuture("Was deleted : %d".formatted(id));
        } else {
            throw new RuntimeException("" +
                    "Letter was not found : %d".formatted(id));
        }
    }
}
