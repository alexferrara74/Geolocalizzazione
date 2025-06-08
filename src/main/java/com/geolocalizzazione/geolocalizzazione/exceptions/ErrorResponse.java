package com.geolocalizzazione.geolocalizzazione.exceptions;
import java.util.Date;

public class ErrorResponse {
    final private Date timestamp;
    final private String message;
    final private String details;

    public ErrorResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters e Setters
    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
