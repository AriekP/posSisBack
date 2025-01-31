package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoRoles;
import com.ucb.webapp.dto.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BlRoles {
    @Autowired
    private DaoRoles daoRoles;

    public List<Rol> obtenerTodosLosRoles() throws SQLException {
        return daoRoles.listarRoles();
    }

    public int crearNuevoRol(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío");
        }
        return daoRoles.crearRol(nombre);
    }


    public void actualizarRol(int id, String nombre) throws SQLException {
        daoRoles.actualizarRol(id, nombre);
    }

    public void eliminarRol(int id) throws SQLException {
        daoRoles.eliminarRol(id);
    }
}
