package net.az3l1t.game_server.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class KafkaConsumer {

    private static final ConcurrentLinkedQueue<String> valueQueue = new ConcurrentLinkedQueue<>();

    @KafkaListener(topics = "exp-topic", groupId = "exp-topic-group")
    public void listenJwtTopic(String message) {
        System.out.println("Received message: " + message);
        valueQueue.add(message);
    }

    public Integer getValue() {
        List<Integer> integers = valueQueue.stream()
                .map(this::convertToInteger)
                .toList();
        valueQueue.clear();

        return integers.stream().mapToInt(Integer::intValue).sum();
    }

    private Integer convertToInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}