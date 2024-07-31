package net.az3l1t.alphabet_server.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.az3l1t.alphabet_server.api.dto.LetterGameResponse;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterGame {
    @Id
    private String character;
    private List<String > listOfWords;
}
