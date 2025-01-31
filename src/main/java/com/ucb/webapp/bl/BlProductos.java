package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoHistorico;
import com.ucb.webapp.dao.DaoProductos;
import com.ucb.webapp.dto.ListaProductos;
import com.ucb.webapp.dto.RegistroHistorico;
import com.ucb.webapp.dto.RegistroProducto;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BlProductos {
    @Autowired
    private DaoProductos productos;

    public List<ListaProductos> mostrar(){
        try {
            return productos.listarProductos();
        }catch (Exception e) {
            throw new CustomException("Error al llamar consulta");
        }
    }
/*
    public void construirRegistroProducto(Map<String, Object> request) throws SQLException {
        try {
            RegistroProducto producto = new RegistroProducto();
            producto.setNombre((String) request.get("nombre"));
            producto.setModelo((String) request.get("modelo"));
            producto.setCantidad((Integer) request.get("cantidad"));
            producto.setDescripcion((String) request.get("descripcion"));
            producto.setPrecio(new BigDecimal((String) request.get("precio")));

            int idMarca = (Integer) request.get("idMarca");
            int idCategoria = (Integer) request.get("idCategoria");
            int idEmpleado = (Integer) request.get("idEmpleado");

            registrarProducto(producto, idMarca, idCategoria, idEmpleado);
        } catch (Exception e) {
            throw new CustomException("Error al preparar el producto para el registro");
        }
    }

    @Autowired
    private DaoHistorico registros;
    @Transactional
    protected void registrarProducto(RegistroProducto producto, int idMarca, int idCategoria, int idEmpleado) throws SQLException {
        try {
            int idProducto = productos.insertarProducto(producto, idMarca, idCategoria);

            RegistroHistorico registro = new RegistroHistorico();
            registro.setAccion("Agregado");
            registro.setCantidad(producto.getCantidad());
            registro.setDescripcion(producto.getCantidad() + " unidades agregadas de " + producto.getModelo());

            registros.insertarHistorico(registro, idProducto, idEmpleado);
        } catch (SQLException e) {
            throw new CustomException("Error al preparar registro");
        }
    }
*/
    @Autowired
    private DaoHistorico registros;
    @Transactional
    public void construirRegistroProducto(Map<String, Object> request) throws SQLException {
        try {
            RegistroProducto producto = new RegistroProducto();
            producto.setNombre((String) request.get("nombre"));
            producto.setModelo((String) request.get("modelo"));
            producto.setCantidad((Integer) request.get("cantidad"));
            producto.setDescripcion((String) request.get("descripcion"));
            producto.setPrecio(new BigDecimal((String) request.get("precio")));
            int idMarca = (Integer) request.get("idMarca");
            int idCategoria = (Integer) request.get("idCategoria");
            int idEmpleado = (Integer) request.get("idEmpleado");

            int idProducto = productos.insertarProducto(producto, idMarca, idCategoria);
            RegistroHistorico registro = new RegistroHistorico();
            registro.setAccion("Agregado");
            registro.setCantidad(producto.getCantidad());
            registro.setDescripcion(producto.getCantidad() + " unidades agregadas de " + producto.getModelo());
            registros.insertarHistorico(registro, idProducto, idEmpleado);
        } catch (SQLException e) {
            throw new CustomException("Error al preparar registro");
        }
    }

    public Long agregarProducto(RegistroProducto producto) {
        try {
            int idProducto = productos.insertarProducto(producto, producto.getIdMarca(), producto.getIdCategoria());

            // Registrar en el histórico
            RegistroHistorico registro = new RegistroHistorico();
            registro.setAccion("Agregado");
            registro.setCantidad(producto.getCantidad());
            registro.setDescripcion(producto.getCantidad() + " unidades agregadas de " + producto.getModelo());

            registros.insertarHistorico(registro, idProducto, producto.getIdEmpleado());

            return (long) idProducto;
        } catch (SQLException e) {
            throw new CustomException("Error al insertar producto: " + e.getMessage());
        }
    }
}
