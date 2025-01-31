package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlRolesPermisos;
import com.ucb.webapp.bl.BlUsuariosRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ucb.webapp.dto.Rol;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/usuarios_roles")
public class ApiUsuariosRoles {
    @Autowired
    private BlUsuariosRoles blUsuariosRoles;

    @PostMapping("/asignar")
    public ResponseEntity<String> asignarRolesAUsuario(@RequestParam int idEmpleado, @RequestBody List<Integer> idRoles) {
        try {
            for (int idRol : idRoles) {
                blUsuariosRoles.asignarRolAUsuario(idEmpleado, idRol);
            }
            return ResponseEntity.ok("Roles asignados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }




    @DeleteMapping("/remover")
    public ResponseEntity<String> removerRolesDeUsuario(@RequestParam int idEmpleado, @RequestBody List<Integer> idRoles) {
        try {
            for (int idRol : idRoles) {
                blUsuariosRoles.removerRolDeUsuario(idEmpleado, idRol);
            }
            return ResponseEntity.ok("Roles eliminados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }




    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> obtenerRolesPorUsuario(@RequestParam int idEmpleado) {
        try {
            return ResponseEntity.ok(blUsuariosRoles.obtenerRolesPorUsuario(idEmpleado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
