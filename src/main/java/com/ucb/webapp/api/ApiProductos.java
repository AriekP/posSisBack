package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlEmpleados;
import com.ucb.webapp.bl.BlProductos;
import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ListaProductos;
import com.ucb.webapp.dto.RegistroProducto;
import com.ucb.webapp.util.ApiResponseBuilder;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/productos")
public class ApiProductos {
    @Autowired
    private BlProductos productos;

    @Autowired
    private ApiResponseBuilder respuestaApi;

    @GetMapping("/listas")
    public ResponseEntity<Map<String, Object>> listar(){
        try {
            List<ListaProductos> listaProductos = productos.mostrar();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Productos", listaProductos);
            return respuestaApi.buildResponse(HttpStatus.OK, "PRO-0001", "Productos recuperados con éxito", "", respuesta);
        } catch (Exception e) {
            throw new CustomException("Error recuperando productos", e);
        }
    }

    @PostMapping("/registro")
public ResponseEntity<Map<String, Object>> registrar(@RequestBody RegistroProducto registroProducto) {
    try {
        if (registroProducto.getNombre() == null || registroProducto.getPrecio() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El nombre y precio son obligatorios"));
        }
        
        Long idProducto = productos.agregarProducto(registroProducto);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("idProducto", idProducto);

        return respuestaApi.buildResponse(HttpStatus.OK, "PRO-0002", "Producto registrado con éxito", "", respuesta);
    } catch (CustomException e) {
        return respuestaApi.buildResponse(HttpStatus.BAD_REQUEST, "PRO-1001", "", "Error registrando producto: " + e.getMessage(), null);
    } catch (Exception e) {
        return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "PRO-1002", "", "Error inesperado: " + e.getMessage(), null);
    }
}


}
