package com.burialsociety.member_service.exception;

import java.time.OffsetDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String message,
        Map<String, String> errors
) {}
