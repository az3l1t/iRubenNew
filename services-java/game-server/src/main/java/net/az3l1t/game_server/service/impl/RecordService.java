package net.az3l1t.game_server.service.impl;

import net.az3l1t.game_server.api.dto.RecordRequest;
import net.az3l1t.game_server.api.dto.RecordResponse;
import net.az3l1t.game_server.api.dto.RecordResponseFull;
import net.az3l1t.game_server.config.KafkaConsumer;
import net.az3l1t.game_server.core.entity.RecordEntity;
import net.az3l1t.game_server.infrastructure.repository.RecordRepository;
import net.az3l1t.game_server.service.mapper.RecordMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class RecordService {

    private final RecordRepository repository;
    private final KafkaConsumer kafkaConsumer;
    private final RecordMapper recordMapper;

    public RecordService(RecordRepository repository, KafkaConsumer kafkaConsumer, RecordMapper recordMapper) {
        this.repository = repository;
        this.kafkaConsumer = kafkaConsumer;
        this.recordMapper = recordMapper;
    }
    @Async
    public CompletableFuture<RecordResponse> createRecord(RecordRequest request){
        RecordEntity recordEntity = recordMapper.toRecordEntity(request);
        if(recordEntity.getMaxRecord() == null){
            recordEntity.setMaxRecord(0);
        }
        if(recordEntity.getMaxValue()==null){
            recordEntity.setMaxValue(0);
        }
        if (recordEntity.getUsername() == null || recordEntity.getUsername().isEmpty()) {
            throw new IllegalArgumentException("RecordEntity : %s, RecordRequest: %s".formatted(recordEntity.getUsername(), request.getUsername()));
        }
        repository.save(recordEntity);
        RecordResponse recordResponse = recordMapper.toRecordResponse(recordEntity);
        return CompletableFuture.completedFuture(
                recordResponse
        );
    }
    @Async
    public CompletableFuture<List<RecordEntity>> findAllRecords(){
        Integer plusableValue = kafkaConsumer.getValue();

        List<RecordEntity> records = repository.findAll().stream()
                .peek(one -> one.setMaxValue(one.getMaxValue() + plusableValue))
                .toList();

        return CompletableFuture.completedFuture(records);
    }
    @Async
    public CompletableFuture<RecordResponseFull> findRecordById(String username){
        Integer plusableValue = kafkaConsumer.getValue();
//        if(plusableValue>-100){throw new RuntimeException("%d".formatted(plusableValue));}
        RecordEntity recordEntity = repository.findById(username)
                .orElseThrow(
                        ()->new RuntimeException(
                                "Record was not found : %s".formatted(username)
                        )
                );
        recordEntity.setMaxValue(recordEntity.getMaxValue()+plusableValue);
        repository.save(recordEntity);
        if (repository.findById(recordEntity.getUsername()).isPresent()){
            return CompletableFuture.completedFuture(recordMapper.toRecordResponseFull(
                    recordEntity
            ));
        } else {
            throw new RuntimeException(
                    "Record was not found : %s".formatted(recordEntity.getUsername())
            );
        }
    }
}
