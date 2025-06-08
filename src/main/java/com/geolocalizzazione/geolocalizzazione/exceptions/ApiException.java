package com.geolocalizzazione.geolocalizzazione.exceptions;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}