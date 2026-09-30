package com.marshall.pharma_app.dto.response.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status, String message, LocalDateTime timestamp
) {
}
