package com.ucb.webapp.dao;

import com.ucb.webapp.dto.ListaClientes;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.util.CustomException;
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
public class DaoClientes {
    @Autowired
    private DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public List<ListaClientes> listarClientes() {
        List<ListaClientes> clientes = new LinkedList<>();
        String sql = "SELECT\n" +
                "\tClientes.idCLiente,\n" +
                "\tClientes.carnet, \n" +
                "\tClientes.telefono,\n" +
                "\tClientes.nombre, \n" +
                "\tClientes.apellido\n" +
                "FROM\n" +
                "\tClientes\n" +
                "WHERE\n" +
                "\tClientes.borrado = FALSE";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ListaClientes cliente = new ListaClientes();
                cliente.setIdCliente((long) resultSet.getInt("idCLiente"));
                cliente.setCarnet(resultSet.getString("carnet"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApelllido(resultSet.getString("apellido"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new CustomException("Error en consulta");
        }
        return clientes;
    }

    public void insertarCliente(ManejoClientes registroCliente) {
        String sql = "INSERT INTO Clientes (nombre, apellido, carnet, telefono, borrado) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, registroCliente.getNombre());
            statement.setString(2, registroCliente.getApellido());
            statement.setString(3, registroCliente.getCarnet());
            statement.setString(4, registroCliente.getTelefono());
            statement.setBoolean(5, false); // borrado should be FALSE

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException("Error en consulta de insercion");
        }
    }

    public void actualizarCliente(Long idCliente, ManejoClientes manejoClientes) {
        String sql = "UPDATE Clientes SET nombre = ?, apellido = ?, carnet = ?, telefono = ? WHERE idCliente = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, manejoClientes.getNombre());
            statement.setString(2, manejoClientes.getApellido());
            statement.setString(3, manejoClientes.getCarnet());
            statement.setString(4, manejoClientes.getTelefono());
            statement.setLong(5, idCliente);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new CustomException("Cliente no encontrado");
            }
        } catch (SQLException e) {
            throw new CustomException("Error en consulta de modificacion");
        }
    }
}