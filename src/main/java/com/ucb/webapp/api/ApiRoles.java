package com.ucb.webapp.api;

import com.ucb.webapp.bl.BlRoles;
import com.ucb.webapp.bl.BlPermisos;
import com.ucb.webapp.bl.BlUsuariosRoles;
import com.ucb.webapp.bl.BlRolesPermisos;
import com.ucb.webapp.dto.Rol;
import com.ucb.webapp.dto.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class ApiRoles {
    @Autowired
    private BlRoles blRoles;

    @GetMapping
    public List<Rol> listarRoles() throws SQLException {
        return blRoles.obtenerTodosLosRoles();
    }

    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody Rol rol) {
        try {
            int id = blRoles.crearNuevoRol(rol.getNombre());
            return ResponseEntity.ok("{\"id\": " + id + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"Error al crear el rol\"}");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarRol(@PathVariable int id, @RequestBody Rol rol) {
        try {
            blRoles.actualizarRol(id, rol.getNombre());
            return ResponseEntity.ok("Rol actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRol(@PathVariable int id) {
        try {
            blRoles.eliminarRol(id);
            return ResponseEntity.ok("Rol eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}