package net.az3l1t.alphabet_server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterButtonRequest {
    private String character;
    private String urlToPhoto;
    private String word;
    private String sentence;
}