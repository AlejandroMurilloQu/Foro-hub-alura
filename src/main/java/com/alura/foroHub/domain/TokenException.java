package com.alura.foroHub.domain;

import org.springframework.security.access.AccessDeniedException;

public class TokenException extends AccessDeniedException {
    public TokenException(String message) {
        super(message);
    }
}
