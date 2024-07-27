package net.az3l1t.news_server.api.controller;

import jakarta.annotation.PostConstruct;
import net.az3l1t.news_server.api.dto.NewResponse;
import net.az3l1t.news_server.api.dto.NewsRequest;
import net.az3l1t.news_server.service.impl.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:8010")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<NewResponse> createNews(
            @RequestBody  NewsRequest newsRequest
    ){
        return ResponseEntity.ok(newsService.createNew(newsRequest));
    }

    @GetMapping("/{title}")
    public String findByTitle(
            @PathVariable String id
    ){
        return newsService.findByTitle(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> findById(
            @PathVariable String id
    ){
        return ResponseEntity.ok(newsService.findById(id));
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:8010")
    public ResponseEntity<List<NewResponse>> getAllNews(){
        return ResponseEntity.ok(newsService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(
            @PathVariable String id
    ){
        return ResponseEntity.ok(newsService.deleteById(id));
    }
}
