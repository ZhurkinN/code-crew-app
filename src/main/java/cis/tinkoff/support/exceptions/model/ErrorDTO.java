package cis.tinkoff.support.exceptions.model;

import java.time.LocalDateTime;

public record ErrorDTO(
        String path,
        String message,
        int statusCode,
        LocalDateTime time
) {
}
