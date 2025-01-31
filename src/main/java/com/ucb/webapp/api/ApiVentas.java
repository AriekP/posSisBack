package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlVentas;
import com.ucb.webapp.dto.DetalleVenta;
import com.ucb.webapp.dto.ListaVentas;
import com.ucb.webapp.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/ventas")
public class ApiVentas {
    @Autowired
    private BlVentas ventas;

    @Autowired
    private ApiResponseBuilder respuestaApi;

    @GetMapping("/listas")
    public ResponseEntity<Map<String, Object>> listar(){
        try{
            List<ListaVentas> listaVentas = ventas.mostrar();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Ventas", listaVentas);
            return respuestaApi.buildResponse(HttpStatus.OK, "VEN-0001", "Ventas recuperadas con éxito", "", respuesta);
        }catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "VEN-1000", "", "Error recuperando ventas:" + e.getMessage(), null);
        }
    }

    @GetMapping("/detalles/{idVenta}")
    public ResponseEntity<Map<String, Object>> obtenerDetalle(@PathVariable Long idVenta) {
        try {
            List<DetalleVenta> detalleVentas = ventas.obtenerDetalleVenta(idVenta);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("DetalleVenta", detalleVentas);
            return respuestaApi.buildResponse(HttpStatus.OK, "VEN-0002", "Detalle de venta recuperado con éxito", "", respuesta);
        } catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "VEN-1001", "", "Error recuperando detalle de venta: " + e.getMessage(), null);
        }
    }
}
