package com.ucb.webapp.dao;

import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.dto.ManejoEmpleados;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Repository
public class DaoEmpleados {
    @Autowired
    private DataSource dataSource;

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public List<ListaEmpleados> listarEmpleados() {
        List<ListaEmpleados> empleados = new LinkedList<>();
        String sql = "SELECT Empleados.idEmpleado, Empleados.alias, Empleados.carnet, Empleados.nombre, " +
                "Empleados.apellido, DATE_FORMAT(Empleados.creacion, '%Y/%m/%d') AS creacion " +
                "FROM Empleados WHERE Empleados.borrado = FALSE";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ListaEmpleados empleado = new ListaEmpleados();
                empleado.setIdEmpleado((long) resultSet.getInt("idEmpleado"));
                empleado.setAlias(resultSet.getString("alias"));
                empleado.setCarnet(resultSet.getString("carnet"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setApellido(resultSet.getString("apellido"));
                empleado.setCreacion(resultSet.getString("creacion"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application logic
        }
        return empleados;
    }

    public Long validarUsuario(String alias, String clave) {
        String sql = "SELECT idEmpleado FROM Empleados WHERE alias = ? AND clave = ? AND borrado = FALSE";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, alias);
            statement.setString(2, clave);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("idEmpleado");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application logic
        }
        return null; // Return null if no matching user found
    }


    public Long insertarEmpleado(ManejoEmpleados registroEmpleado) {
        String sql = "INSERT INTO empleados (nombre, apellido, carnet, alias, clave, creacion, borrado) VALUES (?, ?, ?, ?, ?, ?, false)";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            statement.setString(1, registroEmpleado.getNombre());
            statement.setString(2, registroEmpleado.getApellido());
            statement.setString(3, registroEmpleado.getCarnet());
            statement.setString(4, registroEmpleado.getAlias());
            statement.setString(5, registroEmpleado.getClave());
            statement.setDate(6, Date.valueOf(LocalDate.now()));
    
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo insertar el empleado");
            }
    
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("No se obtuvo el ID del empleado insertado");
                }
            }
        } catch (SQLException e) {
            throw new CustomException("Error al insertar empleado: " + e.getMessage());
        }
    }
    
    
    
    public void actualizarEmpleado(Long idEmpleado, ManejoEmpleados manejoEmpleado) {
        String sql = "UPDATE Empleados SET nombre = ?, apellido = ?, carnet = ?, alias = ?, clave = ? WHERE idEmpleado = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, manejoEmpleado.getNombre());
            statement.setString(2, manejoEmpleado.getApellido());
            statement.setString(3, manejoEmpleado.getCarnet());
            statement.setString(4, manejoEmpleado.getAlias());
            statement.setString(5, manejoEmpleado.getClave());
            statement.setLong(6, idEmpleado);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new CustomException("Empleado no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application logic
            throw new CustomException("Error en consulta de modificacion");
        }
    }

    public void eliminarEmpleadoPorId(Long id) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?"; // Asegúrate de que el nombre de la tabla y la columna sean correctos

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el empleado con ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el empleado: " + e.getMessage(), e);
        }
    }
}
