package com.aeroflow.ingestion.controller;

import com.aeroflow.ingestion.dto.TaskTelemetryDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {

    @PostMapping
    public ResponseEntity<Void> ingestTelemetry(@Valid @RequestBody TaskTelemetryDto payload) {
        // For now, we are just proving the door is open and validation works
        log.info("Received valid telemetry for Task ID: {} from User ID: {}", payload.getTaskId(), payload.getUserId());
        
        // Return 202 Accepted (Meaning: We got it, and we will process it asynchronously)
        return ResponseEntity.accepted().build();
    }
}