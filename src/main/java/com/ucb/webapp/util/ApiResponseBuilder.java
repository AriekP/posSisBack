package com.ucb.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ApiResponseBuilder {
    ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String code, String result, String errorMessage, Object data);
}
