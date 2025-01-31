package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlClientes;
import com.ucb.webapp.dto.ListaClientes;
import com.ucb.webapp.dto.ManejoClientes;
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
@RequestMapping("/v1/clientes")
public class ApiClientes {
    @Autowired
    private BlClientes clientes;

    @Autowired
    private ApiResponseBuilder respuestaApi;

    @GetMapping("/listas")
    public ResponseEntity<Map<String, Object>> listar(){
        try{
            List<ListaClientes> listaClientes = clientes.mostrar();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Clientes", listaClientes);
            return respuestaApi.buildResponse(HttpStatus.OK, "CLI-0001", "Clientes recuperados con éxito", "", respuesta);
        } catch (CustomException e) {
            return respuestaApi.buildResponse(HttpStatus.BAD_REQUEST, "CLI-1000", "", "Error listando clientes: " + e.getMessage(), null);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "CLI-1001", "", "Error inesperado: " + e.getMessage(), null);
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody ManejoClientes registrarClientes) {
        try {
            clientes.agregarCliente(registrarClientes);
            return respuestaApi.buildResponse(HttpStatus.OK, "CLI-0002", "Cliente agregado con éxito", "", null);
        } catch (CustomException e) {
            return respuestaApi.buildResponse(HttpStatus.BAD_REQUEST, "CLI-1002", "", "Error agregando cliente: " + e.getMessage(), null);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "CLI-1003", "", "Error inesperado: " + e.getMessage(), null);
        }
    }

    @PutMapping("/modificacion/{idCliente}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long idCliente, @RequestBody ManejoClientes actualizarClientes) {
        try {
            clientes.actualizarCliente(idCliente, actualizarClientes);
            return respuestaApi.buildResponse(HttpStatus.OK, "CLI-0003", "Cliente actualizado con éxito", "", null);
        } catch (CustomException e) {
            return respuestaApi.buildResponse(HttpStatus.BAD_REQUEST, "CLI-1004", "", "Error actualizando cliente: " + e.getMessage(), null);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "CLI-1005", "", "Error inesperado: " + e.getMessage(), null);
        }
    }
}
