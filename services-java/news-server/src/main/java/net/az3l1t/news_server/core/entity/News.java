package net.az3l1t.news_server.core.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class News {
    @Id
    private String id;
    private String title;
    private String information;

    public News(String id, String title, String information) {
        this.id = id;
        this.title = title;
        this.information = information;
    }

    public News(String title, String information) {
        this.title = title;
        this.information = information;
    }

    public News() {
    }

}
