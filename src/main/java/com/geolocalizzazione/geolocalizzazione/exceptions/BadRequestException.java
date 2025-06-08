package com.geolocalizzazione.geolocalizzazione.exceptions;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(message);
    }
}