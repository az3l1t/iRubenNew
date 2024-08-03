package net.az3l1t.security_server.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic jwtTopic(){
        return TopicBuilder.name("exp-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
