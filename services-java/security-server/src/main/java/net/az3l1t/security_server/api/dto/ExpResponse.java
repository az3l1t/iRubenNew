package net.az3l1t.security_server.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpResponse {
    private Integer exp;

    public ExpResponse(Integer exp) {
        this.exp = exp;
    }
}
