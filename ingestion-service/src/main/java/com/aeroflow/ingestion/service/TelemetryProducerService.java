package com.aeroflow.ingestion.service;

import com.aeroflow.ingestion.dto.TaskTelemetryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryProducerService {

    // Spring's built-in tool for sending messages to Kafka
    private final KafkaTemplate<String, String> kafkaTemplate;
    
    // Jackson's tool for converting Java objects back into JSON strings
    private final ObjectMapper objectMapper;
    
    private static final String TOPIC = "telemetry-events";

    public void publishTelemetry(TaskTelemetryDto payload) {
        try {
            // 1. Convert the validated DTO back into a clean JSON string
            String message = objectMapper.writeValueAsString(payload);
            
            // 2. Send to Kafka. 
            // We use the TaskId as the "Key" so all metrics for the same task go to the same Kafka partition.
            kafkaTemplate.send(TOPIC, payload.getTaskId(), message);
            
            log.info("Successfully published telemetry for Task ID: {} to topic: {}", payload.getTaskId(), TOPIC);
            
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize telemetry payload for Task ID: {}", payload.getTaskId(), e);
            // In a real system, we might route this to a Dead Letter Queue (DLQ)
            throw new RuntimeException("Error processing telemetry payload", e);
        }
    }
}