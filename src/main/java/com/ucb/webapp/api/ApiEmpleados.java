package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlEmpleados;
import com.ucb.webapp.dto.InicioSesion;
import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.dto.ManejoEmpleados;
import com.ucb.webapp.util.ApiResponseBuilder;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/empleados")
public class ApiEmpleados {
    @Autowired
    private BlEmpleados empleados;

    @Autowired
    private ApiResponseBuilder respuestaApi;

    @GetMapping("/listas")
    public ResponseEntity<Map<String, Object>> listar(){
        try{
            List<ListaEmpleados> listaEmpleados = empleados.mostrar();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Empleados", listaEmpleados);
            return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0001", "Empleados recuperados con éxito", "", respuesta);
        }catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1000", "", "Error recuperando empleados:" + e.getMessage(), null);
        }
    }

    @PostMapping("/sesion")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody InicioSesion iniciarSesion) {
        try {
            Long verificarEmpleado = empleados.autenticarEmpleado(iniciarSesion.getAlias(), iniciarSesion.getClave());
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Sesion", verificarEmpleado);
            if (verificarEmpleado != null) {
                return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0002", "Ingreso exitoso al sistema", "", respuesta);
            } else {
                return respuestaApi.buildResponse(HttpStatus.UNAUTHORIZED, "EMP-1001", "", "Credenciales Incorrectas", null);
            }
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1002", "", "Error al validar. Intente de nuevo: " + e.getMessage(), null);
        }
    }

    @PostMapping("/registro")
public ResponseEntity<Map<String, Object>> agregar(@RequestBody ManejoEmpleados registrarEmpleados) {
    try {
        if (registrarEmpleados.getNombre() == null || registrarEmpleados.getApellido() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El nombre y apellido son obligatorios"));
        }
        
        empleados.agregarEmpleado(registrarEmpleados);
        return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0003", "Empleado agregado con éxito", "", null);
    } catch (CustomException e) {
        return respuestaApi.buildResponse(HttpStatus.BAD_REQUEST, "EMP-1003", "", "Error agregando empleado: " + e.getMessage(), null);
    } catch (Exception e) {
        return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1004", "", "Error inesperado: " + e.getMessage(), null);
    }
}







    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody ManejoEmpleados empleado) {
        try {
            empleados.actualizarEmpleados(id, empleado);
            return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0004", "Empleado actualizado con éxito", "", null);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1003", "", "Error al actualizar empleado: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        try {
            empleados.eliminarEmpleado(id);
            return respuestaApi.buildResponse(HttpStatus.OK, "EMP-0005", "Empleado eliminado con éxito", "", null);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "EMP-1004", "", "Error al eliminar empleado: " + e.getMessage(), null);
        }
    }

}
