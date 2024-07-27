package net.az3l1t.news_server.infrastructure.repository;

import net.az3l1t.news_server.core.entity.News;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> {
    String findByTitle(String title);
}
