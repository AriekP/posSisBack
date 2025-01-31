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
public class DaoPermisos {
    @Autowired
    private DataSource dataSource;

    public List<Permiso> listarPermisos() throws SQLException {
        List<Permiso> permisos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM permisos";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                permisos.add(new Permiso(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return permisos;
    }

    public void crearPermiso(String nombre) throws SQLException {
        String sql = "INSERT INTO permisos (nombre) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        }
    }
    public void actualizarPermiso(int id, String nombre) throws SQLException {
        String sql = "UPDATE permisos SET nombre = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void eliminarPermiso(int id) throws SQLException {
        String sql = "DELETE FROM permisos WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}