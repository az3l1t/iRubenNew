package net.az3l1t.game_server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecordRequest {
    private String username;
    private Integer maxValue;
    private Integer maxRecord;
}
