package net.az3l1t.news_server.service.mapper;

import net.az3l1t.news_server.api.dto.NewResponse;
import net.az3l1t.news_server.api.dto.NewsRequest;
import net.az3l1t.news_server.core.entity.News;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsMapper {
    public News toNews(NewsRequest newsRequest) {
        if(newsRequest == null){
            return null;
        }
        return new News(
                newsRequest.getTitle(), newsRequest.getInformation()
        );
    }

    public NewResponse toNewsResponse(News news) {
        return new NewResponse(
                news.getId(),
                news.getTitle(),
                news.getInformation()
        );
    }
}
