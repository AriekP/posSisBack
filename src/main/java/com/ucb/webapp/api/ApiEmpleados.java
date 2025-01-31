package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlEmpleados;
import com.ucb.webapp.bl.BlLogs;
import com.ucb.webapp.dto.InicioSesion;
import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.dto.ManejoEmpleados;
import com.ucb.webapp.util.ApiResponseBuilder;
import com.ucb.webapp.util.CustomException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/empleados")
public class ApiEmpleados {
    @Autowired
    private BlEmpleados empleados;

    @Autowired
    private BlLogs blLogs;

    @Autowired
    private ApiResponseBuilder respuestaApi;

    @GetMapping("/listas")
    public ResponseEntity<Map<String, Object>> listar(@RequestParam Map<String, String> allParams, HttpServletRequest request) {
        System.out.println("--- Nueva Solicitud GET /listas ---");
        System.out.println("🔍 URL Completa: " + request.getRequestURL() + "?" + request.getQueryString());
        System.out.println("📌 Parámetros recibidos (sin filtro de Spring Boot): " + allParams);
        
        // Imprimir encabezados de la solicitud
        System.out.println("📌 Encabezados de la solicitud:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        
        // Forzar la obtención del usuario de los parámetros recibidos
        String usuario = allParams.getOrDefault("usuario", "").trim();
        
        // Imprimir todos los parámetros de la solicitud (incluyendo valores vacíos)
        request.getParameterMap().forEach((key, value) -> {
            System.out.println("🔍 Parámetro recibido: " + key + " = " + String.join(",", value));
        });
        
        if (usuario.isEmpty()) {
            System.err.println("⚠️ Advertencia: No se recibió el usuario en Query Params, buscando en headers...");
            usuario = request.getHeader("usuario"); // Intentar obtenerlo desde los headers
            System.out.println("📌 Usuario obtenido de headers: " + usuario);
            if (usuario == null || usuario.trim().isEmpty()) {
                System.err.println("⚠️ Advertencia: No se recibió el usuario, asignando 'Sistema'.");
                usuario = "Sistema";
            }
        }
        
        System.out.println("✅ Usuario final utilizado en listar(): " + usuario);
        
        try {
            List<ListaEmpleados> listaEmpleados = empleados.mostrar();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Empleados", listaEmpleados);
            blLogs.registrarEvento(usuario, "Se listaron los empleados");
            return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0001", "Empleados recuperados con éxito", "", respuesta);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1000", "", "Error recuperando empleados: " + e.getMessage(), null);
        }
    }
    @PostMapping("/sesion")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody InicioSesion iniciarSesion) {
        try {
            Long verificarEmpleado = empleados.autenticarEmpleado(iniciarSesion.getAlias(), iniciarSesion.getClave());
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Sesion", verificarEmpleado);
            if (verificarEmpleado != null) {
                blLogs.registrarEvento(iniciarSesion.getAlias(), "Inicio sesión en el sistema");
                return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0002", "Ingreso exitoso al sistema", "", respuesta);
            } else {
                return respuestaApi.buildResponse(HttpStatus.UNAUTHORIZED, "EMP-1001", "", "Credenciales Incorrectas", null);
            }
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1002", "", "Error al validar. Intente de nuevo: " + e.getMessage(), null);
        }
    }

    @PostMapping("/registro")
public ResponseEntity<Map<String, Object>> agregar(HttpServletRequest request, @RequestBody ManejoEmpleados registrarEmpleados) {
    // Obtener usuario desde headers manualmente
    String usuario = request.getHeader("usuario");
    
    if (usuario == null || usuario.trim().isEmpty()) {
        System.err.println("⚠️ Advertencia: No se recibió el usuario en la solicitud. Usando 'Sistema'.");
        usuario = "Sistema"; // Fallback si no se envió el usuario
    }

    try {
        if (registrarEmpleados.getNombre() == null || registrarEmpleados.getApellido() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El nombre y apellido son obligatorios"));
        }

        empleados.agregarEmpleado(registrarEmpleados);
        blLogs.registrarEvento(usuario, "Se registró un nuevo empleado: " + registrarEmpleados.getNombre() + " " + registrarEmpleados.getApellido());

        System.out.println("✅ Usuario registrado en logs: " + usuario);
        return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0003", "Empleado agregado con éxito", "", null);

    } catch (CustomException e) {
        return respuestaApi.buildResponse(HttpStatus.BAD_REQUEST, "EMP-1003", "", "Error agregando empleado: " + e.getMessage(), null);
    } catch (Exception e) {
        return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1004", "", "Error inesperado: " + e.getMessage(), null);
    }
}


    @PutMapping("/modificacion/{idEmpleado}")
    public ResponseEntity<Map<String, Object>> actualizar(@RequestParam(value = "usuario", required = false, defaultValue = "Sistema") String usuario, @PathVariable Long idEmpleado, @RequestBody ManejoEmpleados actualizarEmpleados) {
        try {
            empleados.actualizarEmpleados(idEmpleado, actualizarEmpleados);
            blLogs.registrarEvento(usuario, "Se actualizó la información del empleado ID: " + idEmpleado);
            return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0004", "Empleado actualizado con éxito", "", null);
        } catch (CustomException e) {
            return respuestaApi.buildResponse(HttpStatus.BAD_REQUEST, "EMP-1005", "", "Error actualizando empleado: " + e.getMessage(), null);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1006", "", "Error inesperado: " + e.getMessage(), null);
        }
    }
}