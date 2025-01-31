package com.ucb.webapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultApiResponseBuilder implements ApiResponseBuilder {
    @Override
    public ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String code, String result, String errorMessage, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("result", result);
        response.put("errorMessage", errorMessage);
        if (data != null) {
            response.put("data", data);
        }
        return ResponseEntity.status(status).body(response);
    }
}