package com.ucb.webapp.bl;


import com.ucb.webapp.dao.DaoPermisos;

import com.ucb.webapp.dto.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class BlPermisos {
    @Autowired
    private DaoPermisos daoPermisos;

    public List<Permiso> obtenerTodosLosPermisos() throws SQLException {
        return daoPermisos.listarPermisos();
    }

    public void crearNuevoPermiso(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del permiso no puede estar vacío");
        }
        daoPermisos.crearPermiso(nombre);
    }

    public void actualizarPermiso(int id, String nombre) throws SQLException {
        daoPermisos.actualizarPermiso(id, nombre);
    }

    public void eliminarPermiso(int id) throws SQLException {
        daoPermisos.eliminarPermiso(id);
    }
}