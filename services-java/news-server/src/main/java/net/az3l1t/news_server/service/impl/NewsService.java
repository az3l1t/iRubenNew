package net.az3l1t.news_server.service.impl;

import net.az3l1t.news_server.api.dto.NewResponse;
import net.az3l1t.news_server.api.dto.NewsRequest;
import net.az3l1t.news_server.core.entity.News;
import net.az3l1t.news_server.infrastructure.repository.NewsRepository;
import net.az3l1t.news_server.service.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper mapper;

    public NewsService(NewsRepository newsRepository, NewsMapper mapper) {
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    public NewResponse createNew(NewsRequest newsRequest) {
        News news = newsRepository.save(mapper.toNews(newsRequest));
        return new NewResponse(news.getId(), news.getTitle(), news.getInformation(), news.getLinkToPhoto());
    }

    public List<NewResponse> findAll() {
        return newsRepository.findAll().stream()
                .map(mapper::toNewsResponse)
                .toList();
    }

    public String deleteById(String id) {
        newsRepository.deleteById(id);
        return "User was deleted : %s".formatted(id);
    }

    public Boolean findById(String id) {
        return newsRepository.findById(id).isPresent();
    }

    public String findByTitle(String title) {
        return newsRepository.findByTitle(title);
    }
}
