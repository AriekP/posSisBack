package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoProductos;
import com.ucb.webapp.dao.DaoVentas;
import com.ucb.webapp.dto.DetalleVenta;
import com.ucb.webapp.dto.ListaVentas;
import com.ucb.webapp.dto.RegistroHistorico;
import com.ucb.webapp.dto.RegistroProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class BlVentas {
    @Autowired
    private DaoVentas ventas;

    public List<ListaVentas> mostrar(){
        try {
            return ventas.listarVenta();
        }catch (Exception e) {
            throw e;
        }
    }

    public List<DetalleVenta> obtenerDetalleVenta(Long idVenta) {
        try {
            return ventas.obtenerDetalleVenta(idVenta);
        } catch (Exception e) {
            throw e; // Handle the exception according to your application logic
        }
    }
}