package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoRoles;
import com.ucb.webapp.dao.DaoPermisos;
import com.ucb.webapp.dao.DaoUsuariosRoles;
import com.ucb.webapp.dao.DaoRolesPermisos;
import com.ucb.webapp.dto.Rol;
import com.ucb.webapp.dto.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BlRolesPermisos {
    @Autowired
    private DaoRolesPermisos daoRolesPermisos;

    public void asignarPermisoARol(int idRol, int idPermiso) throws SQLException {
        daoRolesPermisos.asignarPermisoARol(idRol, idPermiso);
    }

    public List<Permiso> obtenerPermisosPorRol(int idRol) throws SQLException {
        return daoRolesPermisos.obtenerPermisosPorRol(idRol);
    }



    public void revocarPermisoDeRol(int idRol, int idPermiso) throws SQLException {
        daoRolesPermisos.revocarPermisoDeRol(idRol, idPermiso);
    }


}

