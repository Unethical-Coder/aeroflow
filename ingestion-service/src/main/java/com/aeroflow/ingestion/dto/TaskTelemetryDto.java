package com.aeroflow.ingestion.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskTelemetryDto {

    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotBlank(message = "Task ID cannot be blank")
    private String taskId;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    @Valid
    @NotNull(message = "Metadata is required")
    private Metadata metadata;

    @Valid
    @NotNull(message = "Metrics are required")
    private Metrics metrics;

    @Valid
    @NotNull(message = "Location is required")
    private Location location;

    // --- Nested Classes for Option B Structure ---

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        @NotBlank(message = "Task Type is required")
        private String taskType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metrics {
        @NotNull(message = "Current pace is required")
        private Double currentPace;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        @NotNull(message = "Latitude is required")
        private Double latitude;
        
        @NotNull(message = "Longitude is required")
        private Double longitude;
    }
}