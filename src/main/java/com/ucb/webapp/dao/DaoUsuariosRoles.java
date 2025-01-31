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
public class DaoUsuariosRoles {
    @Autowired
    private DataSource dataSource;

    public void asignarRolAUsuario(int idEmpleado, int idRol) throws SQLException {
        String sql = "INSERT INTO usuarios_roles (idEmpleado, idRol) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            stmt.setInt(2, idRol);
            stmt.executeUpdate();
        }
    }

    public void removerRolDeUsuario(int idEmpleado, int idRol) throws SQLException {
        String sql = "DELETE FROM usuarios_roles WHERE idEmpleado = ? AND idRol = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            stmt.setInt(2, idRol);
            stmt.executeUpdate();
        }
    }



    public List<Rol> obtenerRolesPorUsuario(int idEmpleado) throws SQLException {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT r.id, r.nombre FROM roles r " +
                "INNER JOIN usuarios_roles ur ON r.id = ur.idRol " +
                "WHERE ur.idEmpleado = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId(rs.getInt("id"));
                rol.setNombre(rs.getString("nombre"));
                roles.add(rol);
            }
        }
        return roles;
    }


}
