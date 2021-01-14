package ru.kislyakova.anastasia.mailingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Error {
    private final String code;
    private final String message;
    private final List<Error> errors;

    public Error(String code, String message) {
        this(code, message, null);
    }
}
