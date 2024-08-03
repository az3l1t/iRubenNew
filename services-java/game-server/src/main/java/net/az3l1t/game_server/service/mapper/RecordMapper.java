package net.az3l1t.game_server.service.mapper;

import net.az3l1t.game_server.api.dto.RecordRequest;
import net.az3l1t.game_server.api.dto.RecordResponse;
import net.az3l1t.game_server.api.dto.RecordResponseFull;
import net.az3l1t.game_server.core.entity.RecordEntity;
import org.springframework.stereotype.Service;

@Service
public class RecordMapper {
    public RecordEntity toRecordEntity(RecordRequest request) {
        return new RecordEntity(
                request.getUsername(),
                request.getMaxValue(),
                request.getMaxRecord()
        );
    }

    public RecordResponse toRecordResponse(RecordEntity recordEntity) {
        return new RecordResponse(
                recordEntity.getUsername()
        );
    }

    public RecordResponseFull toRecordResponseFull(RecordRequest request) {
        return new RecordResponseFull(
                request.getUsername(),
                request.getMaxValue(),
                request.getMaxRecord()
        );
    }

    public RecordResponseFull toRecordResponseFull(RecordEntity request) {
        return new RecordResponseFull(
                request.getUsername(),
                request.getMaxValue(),
                request.getMaxRecord()
        );
    }
}
