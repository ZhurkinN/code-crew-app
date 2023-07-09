package cis.tinkoff.support.exceptions.model;

public record ErrorDTO(
        String path,
        String message,
        int statusCode,
        Long time
) {
}
