package com.project.webparser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WrongWebsiteUrlException extends RuntimeException{
    public WrongWebsiteUrlException() {
        super("wrong website url exception");
    }

    public WrongWebsiteUrlException(String message) {
        super(message);
    }

    public WrongWebsiteUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongWebsiteUrlException(Throwable cause) {
        super(cause);
    }
}
