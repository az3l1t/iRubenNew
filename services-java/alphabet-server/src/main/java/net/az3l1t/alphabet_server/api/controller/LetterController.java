package net.az3l1t.alphabet_server.api.controller;

import net.az3l1t.alphabet_server.api.dto.*;
import net.az3l1t.alphabet_server.core.entity.Letter;
import net.az3l1t.alphabet_server.service.impl.LetterButtonService;
import net.az3l1t.alphabet_server.service.impl.LetterGameService;
import net.az3l1t.alphabet_server.service.impl.LetterService;
import org.apache.hc.core5.concurrent.CompletedFuture;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<LetterGameResponse> createNewLetterGame(
            @Argument LetterGameRequest request
    ) {
        return letterGameService.createGameByCharacter(request);
    }

    @QueryMapping
    public CompletableFuture<LetterGameResponse> findById(
            @Argument String character
    ) {
        return letterGameService.findGameByCharacter(character);
    }

    @QueryMapping
    public CompletableFuture<List<LetterGameResponse>> findAllGamesUntil(
            @Argument Integer until
    ){
        return letterGameService.findAllGamesUntil(until);
    }

    /*
        LetterButtonService
     */
    @MutationMapping
    public CompletableFuture<LetterButtonResponse> createNewLetterButton(
            @Argument LetterButtonRequest request
    ) {
        return letterButtonService.createTheButtonInfo(request);
    }

    @QueryMapping
    public CompletableFuture<LetterButtonWithInfoResponse> getLetterButtonWithInfoById(
            @Argument LetterCharacterRequest request
            ){
        return letterButtonService.getButtonInfo(request);
    }

    @QueryMapping
    public CompletableFuture<List<LetterButtonWithInfoResponse>> getAllButtons(
    ) {
        return letterButtonService.getAllButtonsInfo();
    }

    /*
        LetterService
     */
    @QueryMapping
    @CrossOrigin(origins = "http://localhost:8010")
    public CompletableFuture<List<Letter>> getAllLetters(){
        return letterService.getAllLetter();
    }

    @QueryMapping
    public CompletableFuture<Optional<Letter>> getLetterById(@Argument Integer id){
        return letterService.getLetterById(id);
    }

    @MutationMapping
    public CompletableFuture<Letter> addLetter(@Argument("letterRequest") LetterRequest letterRequest){
        return letterService.addLetter(letterRequest);
    }

    @MutationMapping
    public CompletableFuture<String> deleteLetter(@Argument Integer id){
        return letterService.deleteLetter(id);
    }
}