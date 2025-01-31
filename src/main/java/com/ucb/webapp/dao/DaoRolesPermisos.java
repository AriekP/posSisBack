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
public class DaoRolesPermisos {
    @Autowired
    private DataSource dataSource;

    public void asignarPermisoARol(int idRol, int idPermiso) throws SQLException {
        String sql = "INSERT INTO roles_permisos (idRol, idPermiso) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            stmt.setInt(2, idPermiso);
            stmt.executeUpdate();
        }
    }
    public void revocarPermisoDeRol(int idRol, int idPermiso) throws SQLException {
        String sql = "DELETE FROM roles_permisos WHERE idRol = ? AND idPermiso = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            stmt.setInt(2, idPermiso);
            stmt.executeUpdate();
        }
    }




    public List<Permiso> obtenerPermisosPorRol(int idRol) throws SQLException {
        List<Permiso> permisos = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre FROM permisos p " +
                "INNER JOIN roles_permisos rp ON p.id = rp.idPermiso " +
                "WHERE rp.idRol = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRol);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Permiso permiso = new Permiso();
                permiso.setId(rs.getInt("id"));
                permiso.setNombre(rs.getString("nombre"));
                permisos.add(permiso);
            }
        }
        return permisos;
    }

}
