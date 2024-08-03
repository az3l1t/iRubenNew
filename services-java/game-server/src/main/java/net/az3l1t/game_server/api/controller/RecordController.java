package net.az3l1t.game_server.api.controller;

import net.az3l1t.game_server.api.dto.RecordRequest;
import net.az3l1t.game_server.api.dto.RecordResponse;
import net.az3l1t.game_server.api.dto.RecordResponseFull;
import net.az3l1t.game_server.core.entity.RecordEntity;
import net.az3l1t.game_server.service.impl.RecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public CompletableFuture<RecordResponse> createRecord(
            @RequestBody RecordRequest request){
        return recordService.createRecord(request);
    }

    @GetMapping
    public CompletableFuture<List<RecordEntity>> findAllRecord(){
        return recordService.findAllRecords();
    }

    @GetMapping("/{username}")
    public CompletableFuture<RecordResponseFull> findRecordById(
            @PathVariable String username){
        return recordService.findRecordById(username);
    }
}
