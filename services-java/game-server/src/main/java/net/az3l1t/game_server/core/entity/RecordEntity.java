package net.az3l1t.game_server.core.entity;

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
public class RecordEntity {
    @Id
    private String username;
    private Integer maxValue;
    private Integer maxRecord;
}
