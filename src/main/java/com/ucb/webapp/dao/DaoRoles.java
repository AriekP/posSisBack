package com.ucb.webapp.dao;

import com.ucb.webapp.dto.Rol;
import com.ucb.webapp.dto.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoRoles {
    @Autowired
    private DataSource dataSource;

    public List<Rol> listarRoles() throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT id, nombre FROM roles";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roles.add(new Rol(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return roles;
    }

    public int crearRol(String nombre) throws SQLException {
        String sql = "INSERT INTO roles (nombre) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Devuelve el ID generado
                }
            }
        }
        throw new SQLException("No se pudo obtener el ID del rol creado");
    }

    public void actualizarRol(int id, String nombre) throws SQLException {
        String sql = "UPDATE roles SET nombre = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void eliminarRol(int id) throws SQLException {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
