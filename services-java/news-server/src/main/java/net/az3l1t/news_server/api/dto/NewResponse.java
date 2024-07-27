package net.az3l1t.news_server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewResponse {
    private String id;
    private String title;
    private String information;
    private String url;
}
