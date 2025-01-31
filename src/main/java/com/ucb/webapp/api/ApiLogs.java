package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlLogs;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.*;

@RestController
@RequestMapping("/v1/logs")
public class ApiLogs {
    
    @Autowired
    private BlLogs blLogs;

    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerLogs() {
        List<Map<String, String>> logs = blLogs.obtenerTodosLosLogs();
        Map<String, Object> response = new HashMap<>();
        response.put("logs", logs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarEvento(@RequestParam String usuario, @RequestParam String evento) {
        blLogs.registrarEvento(usuario, evento);
        return new ResponseEntity<>("Log registrado exitosamente", HttpStatus.CREATED);
    }
}