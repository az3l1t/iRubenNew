package net.az3l1t.alphabet_server.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LetterButtonEntity {
    @Id
    private String character;
    private String urlToPhoto;
    private String word;
    private String sentence;
}
