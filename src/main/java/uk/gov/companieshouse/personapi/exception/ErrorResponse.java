package uk.gov.companieshouse.personapi.exception;

import java.time.LocalTime;

public class ErrorResponse {
    private final LocalTime timestamp;
    private final String message;

    public ErrorResponse(LocalTime timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
