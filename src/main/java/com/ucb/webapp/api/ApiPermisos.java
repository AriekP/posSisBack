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
@RequestMapping("/permisos")
public class ApiPermisos {
    @Autowired
    private BlPermisos blPermisos;

    @GetMapping
    public List<Permiso> listarPermisos() throws SQLException {
        return blPermisos.obtenerTodosLosPermisos();
    }

    @PostMapping
    public ResponseEntity<String> crearPermiso(@RequestBody Permiso permiso) {
        try {
            blPermisos.crearNuevoPermiso(permiso.getNombre());
            return ResponseEntity.ok("Permiso creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPermiso(@PathVariable int id, @RequestBody Permiso permiso) {
        try {
            blPermisos.actualizarPermiso(id, permiso.getNombre());
            return ResponseEntity.ok("Permiso actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPermiso(@PathVariable int id) {
        try {
            blPermisos.eliminarPermiso(id);
            return ResponseEntity.ok("Permiso eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
