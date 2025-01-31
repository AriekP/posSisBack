package com.ucb.webapp.dao;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class DaoLogs {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DaoEmpleados daoEmpleados;

    public void registrarLog(String usuario, String evento) {
        Integer idEmpleado = obtenerIdPorUsuario(usuario);
        String sql = "INSERT INTO logs (usuario, evento, idEmpleado) VALUES (?, ?, ?)";
        
        try {
            System.out.println("✅ Intentando insertar log en la base de datos: Usuario=" + usuario + ", ID Empleado=" + idEmpleado + ", Evento=" + evento);
            jdbcTemplate.update(sql, usuario, evento, idEmpleado);
            System.out.println("✔ Log insertado exitosamente en la base de datos.");
        } catch (Exception e) {
            System.err.println("❌ ERROR AL INSERTAR LOG: " + e.getMessage());
            throw new RuntimeException("Error al registrar log en la base de datos: " + e.getMessage(), e);
        }
    }

    public Integer obtenerIdPorUsuario(String usuario) {
        String sql = "SELECT idEmpleado FROM empleados WHERE alias = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{usuario}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null; // Retorna NULL si el usuario no existe
        }
    }

    public List<Map<String, String>> obtenerLogs() {
        String sql = "SELECT l.fecha, l.usuario, l.evento, e.nombre, e.apellido FROM logs l LEFT JOIN empleados e ON l.idEmpleado = e.idEmpleado ORDER BY l.fecha DESC";
        List<Map<String, String>> logs = new ArrayList<>();
        
        try {
            logs = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Map<String, String> log = new HashMap<>();
                log.put("fecha", rs.getString("fecha"));
                log.put("usuario", rs.getString("usuario"));
                log.put("evento", rs.getString("evento"));
                log.put("nombreEmpleado", rs.getString("nombre") + " " + rs.getString("apellido"));
                return log;
            });
            System.out.println("📌 LOGS OBTENIDOS: " + logs.size() + " registros encontrados.");
        } catch (Exception e) {
            System.err.println("❌ ERROR AL OBTENER LOGS: " + e.getMessage());
            throw new RuntimeException("Error al obtener logs: " + e.getMessage(), e);
        }
        return logs;
    }
}
