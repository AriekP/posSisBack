package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlRolesPermisos;
import com.ucb.webapp.bl.BlUsuariosRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ucb.webapp.dto.Permiso;


import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/roles_permisos")
public class ApiRolesPermisos {
    @Autowired
    private BlRolesPermisos blRolesPermisos;

    @PostMapping("/asignar")
    public ResponseEntity<String> asignarPermisoARol(@RequestParam int idRol, @RequestParam int idPermiso) {
        try {
            blRolesPermisos.asignarPermisoARol(idRol, idPermiso);
            return ResponseEntity.ok("Permiso asignado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/revocar")
    public ResponseEntity<String> revocarPermisoDeRol(@RequestParam int idRol, @RequestParam int idPermiso) {
        try {
            blRolesPermisos.revocarPermisoDeRol(idRol, idPermiso);
            return ResponseEntity.ok("Permiso revocado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Permiso>> obtenerPermisosPorRol(@RequestParam int idRol) {
        try {
            return ResponseEntity.ok(blRolesPermisos.obtenerPermisosPorRol(idRol));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}