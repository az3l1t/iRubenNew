package net.az3l1t.alphabet_server.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String character;

    public Letter(Integer id, String character) {
        this.id = id;
        this.character = character;
    }

    public Letter(String character) {
        this.character = character;
    }

    public Letter() {
    }
}
