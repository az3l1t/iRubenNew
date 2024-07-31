package net.az3l1t.alphabet_server.api.controller;

import net.az3l1t.alphabet_server.api.dto.*;
import net.az3l1t.alphabet_server.core.entity.Letter;
import net.az3l1t.alphabet_server.service.impl.LetterButtonService;
import net.az3l1t.alphabet_server.service.impl.LetterGameService;
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
    private final LetterButtonService letterButtonService;
    private final LetterGameService letterGameService;

    public LetterController(LetterService letterService, LetterButtonService letterButtonService, LetterGameService letterGameService) {
        this.letterService = letterService;
        this.letterButtonService = letterButtonService;
        this.letterGameService = letterGameService;
    }

    /*
        LetterGameService
     */
    @MutationMapping
    public LetterGameResponse createNewLetterGame(
            @Argument LetterGameRequest request
    ) {
        return letterGameService.createGameByCharacter(request);
    }

    @QueryMapping
    public LetterGameResponse findById(
            @Argument String character
    ) {
        return letterGameService.findGameByCharacter(character);
    }

    /*
        LetterButtonService
     */
    @MutationMapping
    public LetterButtonResponse createNewLetterButton(
            @Argument LetterButtonRequest request
    ) {
        return letterButtonService.createTheButtonInfo(request);
    }

    @QueryMapping
    public LetterButtonWithInfoResponse getLetterButtonWithInfoById(
            @Argument LetterCharacterRequest request
            ){
        return letterButtonService.getButtonInfo(request);
    }

    @QueryMapping
    public List<LetterButtonWithInfoResponse> getAllButtons(
    ) {
        return letterButtonService.getAllButtonsInfo();
    }

    /*
        LetterService
     */
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