package net.az3l1t.news_server.api.controller;

import jakarta.annotation.PostConstruct;
import net.az3l1t.news_server.api.dto.NewResponse;
import net.az3l1t.news_server.api.dto.NewsRequest;
import net.az3l1t.news_server.service.impl.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:8010")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public CompletableFuture<NewResponse> createNews(
            @RequestBody  NewsRequest newsRequest
    ){
        return newsService.createNew(newsRequest);
    }

    @GetMapping("/{title}")
    public String findByTitle(
            @PathVariable String id
    ){
        return newsService.findByTitle(id);
    }

    @GetMapping("/{id}")
    public CompletableFuture<Boolean> findById(
            @PathVariable String id
    ){
        return newsService.findById(id);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:8010")
    public CompletableFuture<List<NewResponse>> getAllNews(){
        return newsService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(
            @PathVariable String id
    ){
        return ResponseEntity.ok(newsService.deleteById(id));
    }
}
