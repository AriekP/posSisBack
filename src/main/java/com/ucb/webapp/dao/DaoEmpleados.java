package com.ucb.webapp.dao;

import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.dto.ManejoEmpleados;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.dao.EmptyResultDataAccessException;


import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Repository
public class DaoEmpleados {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public List<ListaEmpleados> listarEmpleados() {
        List<ListaEmpleados> empleados = new LinkedList<>();
        String sql = "SELECT Empleados.idEmpleado, Empleados.alias, Empleados.carnet, Empleados.nombre, " +
                "Empleados.apellido, DATE_FORMAT(Empleados.creacion, '%Y/%m/%d') AS creacion " +
                "FROM Empleados WHERE Empleados.borrado = FALSE";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ListaEmpleados empleado = new ListaEmpleados();
                empleado.setIdEmpleado(resultSet.getLong("idEmpleado"));
                empleado.setAlias(resultSet.getString("alias"));
                empleado.setCarnet(resultSet.getString("carnet"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setApellido(resultSet.getString("apellido"));
                empleado.setCreacion(resultSet.getString("creacion"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepción
        }
        return empleados;
    }

    public Long validarUsuario(String alias, String clave) {
        String sql = "SELECT idEmpleado, clave FROM Empleados WHERE alias = ? AND borrado = FALSE";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{alias}, (rs, rowNum) -> {
                String storedPassword = rs.getString("clave");
                Long userId = rs.getLong("idEmpleado");
                
                System.out.println("Contraseña obtenida de la base de datos: " + storedPassword);
                
                if (storedPassword.startsWith("$2a$")) {
                    // Comparar usando BCrypt si la contraseña está encriptada
                    if (passwordEncoder.matches(clave, storedPassword)) {
                        System.out.println("✅ Contraseña encriptada válida, acceso permitido");
                        return userId;
                    } else {
                        System.out.println("❌ Contraseña incorrecta, acceso denegado");
                        return null;
                    }
                }
                
                // Comparación directa si la contraseña no está encriptada
                if (storedPassword.equals(clave)) {
                    System.out.println("✅ Contraseña en texto plano válida, acceso permitido");
                    return userId;
                }
                
                System.out.println("❌ Contraseña incorrecta, acceso denegado");
                return null;
            });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("❌ Usuario no encontrado");
            return null;
        } catch (Exception e) {
            System.out.println("❌ Error en la autenticación: " + e.getMessage());
            return null;
        }
    }


    public Long insertarEmpleado(ManejoEmpleados registroEmpleado) {
        String sql = "INSERT INTO Empleados (nombre, apellido, carnet, alias, clave, creacion, borrado) VALUES (?, ?, ?, ?, ?, ?, false)";
    
        try {
            jdbcTemplate.update(sql, registroEmpleado.getNombre(), registroEmpleado.getApellido(), registroEmpleado.getCarnet(),
                    registroEmpleado.getAlias(), registroEmpleado.getClave(), Date.valueOf(LocalDate.now()));
            return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar empleado: " + e.getMessage(), e);
        }
    }
    
    
    
    public void actualizarEmpleado(Long idEmpleado, ManejoEmpleados manejoEmpleado) {
        String sql = "UPDATE Empleados SET nombre = ?, apellido = ?, carnet = ?, alias = ?, clave = ? WHERE idEmpleado = ?";

        try {
            int rowsUpdated = jdbcTemplate.update(sql, manejoEmpleado.getNombre(), manejoEmpleado.getApellido(),
                    manejoEmpleado.getCarnet(), manejoEmpleado.getAlias(), manejoEmpleado.getClave(), idEmpleado);
            if (rowsUpdated == 0) {
                throw new CustomException("Empleado no encontrado");
            }
        } catch (Exception e) {
            throw new CustomException("Error en consulta de modificación");
        }
    }

    public Integer obtenerIdPorUsuario(String usuario) {
        String sql = "SELECT idEmpleado FROM empleados WHERE alias = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{usuario}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;  // Retorna NULL si el usuario no existe
        }
    }
    
}
