package com.example.demo.n10.dto;

import java.util.List;
import java.util.UUID;

/**
 * Request DTO for toggling isEditable status of multiple exam results
 */
public record ToggleEditMultipleRequest(
    List<UUID> ids,
    Boolean isEditable
) {
}
