package ru.kislyakova.anastasia.mailingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SchedulerApiException extends ResponseStatusException {
    private final String code;

    public SchedulerApiException(HttpStatus status, String code, String reason) {
        super(status, reason);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
