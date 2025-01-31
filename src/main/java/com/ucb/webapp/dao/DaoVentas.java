package com.ucb.webapp.dao;

import com.ucb.webapp.dto.DetalleVenta;
import com.ucb.webapp.dto.ListaVentas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class DaoVentas {
    @Autowired
    private DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public List<ListaVentas> listarVenta() {
        List<ListaVentas> ventas = new LinkedList<>();
        String sql = "SELECT\n" +
                "\tVentas.idVenta,\n" +
                "\tClientes.carnet,\n" +
                "\tDATE_FORMAT( Ventas.fecha, '%Y/%m/%d %H:%i' ) AS fecha,\n" +
                "\tVentas.total,\n" +
                "\tEmpleados.alias \n" +
                "FROM\n" +
                "\tVentas\n" +
                "\tINNER JOIN Clientes ON Ventas.Clientes_idCLiente = Clientes.idCLiente\n" +
                "\tINNER JOIN Empleados ON Ventas.Empleados_idEmpleado = Empleados.idEmpleado ";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ListaVentas venta = new ListaVentas();
                venta.setIdVenta((long) resultSet.getInt("idVenta"));
                venta.setCarnet(resultSet.getString("carnet"));
                venta.setFecha(resultSet.getString("fecha"));
                venta.setTotal(resultSet.getBigDecimal("total"));
                venta.setAlias(resultSet.getString("alias"));
                ventas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application logic
        }
        return ventas;
    }

    public List<DetalleVenta> obtenerDetalleVenta(Long idVenta) {
        List<DetalleVenta> detallesVenta = new LinkedList<>();
        String sql = "SELECT\n" +
                "\tProductos.idProducto,\n" +
                "\tProductos.modelo,\n" +
                "\tProductos.nombre,\n" +
                "\tVentasProductos.cantidad,\n" +
                "\tVentasProductos.precioUnitario,\n" +
                "\tVentasProductos.precioTotal \n" +
                "FROM\n" +
                "\tVentas\n" +
                "\tINNER JOIN VentasProductos ON Ventas.idVenta = VentasProductos.Ventas_idVenta\n" +
                "\tINNER JOIN Productos ON VentasProductos.Productos_idProducto = Productos.idProducto \n" +
                "WHERE\n" +
                "\tVentas.idVenta = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idVenta);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto(resultSet.getLong("idProducto"));
                detalle.setModelo(resultSet.getString("modelo"));
                detalle.setNombre(resultSet.getString("nombre"));
                detalle.setCantidad(resultSet.getInt("cantidad"));
                detalle.setPrecioUnitario(resultSet.getBigDecimal("precioUnitario"));
                detalle.setPrecioTotal(resultSet.getBigDecimal("precioTotal"));
                detallesVenta.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application logic
        }
        return detallesVenta;
    }

}