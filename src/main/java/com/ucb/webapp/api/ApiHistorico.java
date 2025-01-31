package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlHistorico;
import com.ucb.webapp.bl.BlProductos;
import com.ucb.webapp.dto.ListaHistorico;
import com.ucb.webapp.dto.ListaProductos;
import com.ucb.webapp.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/registros")
public class ApiHistorico {
    @Autowired
    private BlHistorico registros;

    @Autowired
    private ApiResponseBuilder respuestaApi;

    @GetMapping("/listas")
    public ResponseEntity<Map<String, Object>> listar(){
        try{
            List<ListaHistorico> listaRegistros = registros.mostrar();
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("Registros", listaRegistros);
            return respuestaApi.buildResponse(HttpStatus.OK, "REG-0001", "Registros recuperados con éxito", "", respuesta);
        }catch (Exception e) {
            return respuestaApi.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "REG-1000", "", "Error recuperando registros:" + e.getMessage(), null);
        }
    }
}