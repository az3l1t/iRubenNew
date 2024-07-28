package net.az3l1t.alphabet_server.api.controller;

import net.az3l1t.alphabet_server.api.dto.LetterRequest;
import net.az3l1t.alphabet_server.core.entity.Letter;
import net.az3l1t.alphabet_server.service.impl.LetterService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8010")
public class LetterController {
    private final LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    @QueryMapping
    @CrossOrigin(origins = "http://localhost:8010")
    public List<Letter> getAllLetters(){
        return letterService.getAllLetter();
    }

    @QueryMapping
    public Optional<Letter> getLetterById(@Argument Integer id){
        return letterService.getLetterById(id);
    }

    @MutationMapping
    public Letter addLetter(@Argument("letterRequest") LetterRequest letterRequest){
        return letterService.addLetter(letterRequest);
    }

    @MutationMapping
    public String deleteLetter(@Argument Integer id){
        return letterService.deleteLetter(id);
    }
}