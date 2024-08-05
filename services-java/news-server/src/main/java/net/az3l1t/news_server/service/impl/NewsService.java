package net.az3l1t.news_server.service.impl;

import net.az3l1t.news_server.api.dto.NewResponse;
import net.az3l1t.news_server.api.dto.NewsRequest;
import net.az3l1t.news_server.core.entity.News;
import net.az3l1t.news_server.infrastructure.repository.NewsRepository;
import net.az3l1t.news_server.service.mapper.NewsMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper mapper;

    public NewsService(NewsRepository newsRepository, NewsMapper mapper) {
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }
    @Async
    public CompletableFuture<NewResponse> createNew(NewsRequest newsRequest) {
        News news = newsRepository.save(mapper.toNews(newsRequest));
        return CompletableFuture.completedFuture(new NewResponse(news.getId(), news.getTitle(), news.getInformation()));
    }
    @Async
    public CompletableFuture<List<NewResponse>> findAll() {
        List<NewResponse> newsList = newsRepository.findAll().stream()
                .map(mapper::toNewsResponse)
                .toList();

        return CompletableFuture.completedFuture(newsList);
    }

    public String deleteById(String id) {
        newsRepository.deleteById(id);
        return "User was deleted : %s".formatted(id);
    }
    @Async
    public CompletableFuture<Boolean> findById(String id) {
        return CompletableFuture.completedFuture(newsRepository.findById(id).isPresent());
    }

    public String findByTitle(String title) {
        return newsRepository.findByTitle(title);
    }
}
