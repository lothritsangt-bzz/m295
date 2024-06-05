package com.example.demo295.util.exception;

import jakarta.xml.bind.ValidationException;

public class FormatErrorException extends ValidationException {
    public FormatErrorException(String message) {
        super(message);
    }
}
