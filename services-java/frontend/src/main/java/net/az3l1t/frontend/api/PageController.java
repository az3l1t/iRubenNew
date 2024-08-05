package net.az3l1t.frontend.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/success")
    public String toSuccess() {
        return "success.html";
    }
    @GetMapping("/alphabet")
    public String toAlphabet() {
        return "alphabet.html";
    }
    @GetMapping("/game")
    public String toGame() {
        return "game.html";
    }
    @GetMapping("/words")
    public String toWords() {
        return "words.html";
    }
}
