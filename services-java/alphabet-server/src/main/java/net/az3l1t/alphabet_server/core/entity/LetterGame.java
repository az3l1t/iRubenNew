package net.az3l1t.alphabet_server.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterGame {
    @Id
    private String character;
    private List<String> listOfArmenianWords;
    private List<String> listOfRussianWords;
}
