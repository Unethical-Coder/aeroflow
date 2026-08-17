package com.aeroflow.ingestion.controller;

import com.aeroflow.ingestion.dto.TaskTelemetryDto;
import com.aeroflow.ingestion.service.TelemetryProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor // Lombok creates a constructor for our service injection
public class TelemetryController {

    private final TelemetryProducerService producerService;

    @PostMapping
    public ResponseEntity<Void> ingestTelemetry(@Valid @RequestBody TaskTelemetryDto payload) {
        
        log.info("Received valid telemetry for Task ID: {}", payload.getTaskId());
        
        // Pass the payload to our Kafka Producer
        producerService.publishTelemetry(payload);
        
        return ResponseEntity.accepted().build();
    }
}