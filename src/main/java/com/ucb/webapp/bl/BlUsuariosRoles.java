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
public class BlUsuariosRoles {
    @Autowired
    private DaoUsuariosRoles daoUsuariosRoles;

    public void asignarRolAUsuario(int idEmpleado, int idRol) throws SQLException {
        daoUsuariosRoles.asignarRolAUsuario(idEmpleado, idRol);
    }


    public List<Rol> obtenerRolesPorUsuario(int idEmpleado) throws SQLException {
        return daoUsuariosRoles.obtenerRolesPorUsuario(idEmpleado);
    }


    public void removerRolDeUsuario(int idEmpleado, int idRol) throws SQLException {
        daoUsuariosRoles.removerRolDeUsuario(idEmpleado, idRol);
    }



}
