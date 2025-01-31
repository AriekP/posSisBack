package com.ucb.webapp.dao;

import com.ucb.webapp.dto.ListaHistorico;
import com.ucb.webapp.dto.RegistroHistorico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Repository
public class DaoHistorico {
    @Autowired
    private DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public List<ListaHistorico> listarRegistros() {
        List<ListaHistorico> registros = new LinkedList<>();
        String sql = "SELECT\n" +
                "\tEmpleados.idEmpleado,\n" +
                "\tEmpleados.alias,\n" +
                "\tHistorico.accion,\n" +
                "\tHistorico.cantidad,\n" +
                "\tHistorico.descripcion,\n" +
                "\tDATE_FORMAT( Historico.fecha, '%Y/%m/%d %H:%i' ) AS fecha \n" +
                "FROM\n" +
                "\tHistorico\n" +
                "\tINNER JOIN Empleados ON Historico.Empleados_idEmpleado = Empleados.idEmpleado \n" +
                "ORDER BY\n" +
                "\tHistorico.idHistorico ASC";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ListaHistorico registro = new ListaHistorico();
                registro.setIdEmpleado((long) resultSet.getInt("idEmpleado"));
                registro.setAlias(resultSet.getString("alias"));
                registro.setAccion(resultSet.getString("accion"));
                registro.setCantidad(resultSet.getInt("cantidad"));
                registro.setDescripcion(resultSet.getString("descripcion"));
                registro.setFecha(resultSet.getString("fecha"));
                registros.add(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application logic
        }
        return registros;
    }

    public void insertarHistorico(RegistroHistorico registroProducto, int idProducto, int idEmpleado) throws SQLException {
        String sql = "INSERT INTO Historico (accion, cantidad, descripcion, fecha, Productos_idProducto, Empleados_idEmpleado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, registroProducto.getAccion());
            statement.setInt(2, registroProducto.getCantidad());
            statement.setString(3, registroProducto.getDescripcion());
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(5, idProducto);
            statement.setInt(6, idEmpleado);

            statement.executeUpdate();
        }
    }
}