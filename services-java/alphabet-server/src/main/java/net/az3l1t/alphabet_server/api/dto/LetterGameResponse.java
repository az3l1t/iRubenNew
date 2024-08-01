package net.az3l1t.alphabet_server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterGameResponse {
    private String character;
    private List<String> listOfArmenianWords;
    private List<String> listOfRussianWords;
}
