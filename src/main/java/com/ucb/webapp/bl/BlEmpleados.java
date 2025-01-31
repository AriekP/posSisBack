package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoEmpleados;
import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.dto.ManejoEmpleados;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Service
public class BlEmpleados {
    @Autowired
    private DaoEmpleados empleados;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public List<ListaEmpleados> mostrar(){
        try {
            return empleados.listarEmpleados();
        }catch (Exception e) {
            throw e;
        }
    }

    public Long autenticarEmpleado(String alias, String clave) {
        try {
            return empleados.validarUsuario(alias, clave);
        } catch (Exception e) {
            throw e;
        }
    }

    public void agregarEmpleado(ManejoEmpleados registrarEmpleados) {
        try {
            // Hash de la contraseña antes de guardarla
            String hashedPassword = passwordEncoder.encode(registrarEmpleados.getClave());
            registrarEmpleados.setClave(hashedPassword);

            // Insertar en la base de datos
            Long idEmpleado = empleados.insertarEmpleado(registrarEmpleados);

            if (idEmpleado == null) {
                throw new RuntimeException("No se generó un ID para el nuevo empleado");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar empleado: " + e.getMessage());
        }
    }
    
    

    public void actualizarEmpleados(Long idEmpleado, ManejoEmpleados modificarEmpleados) {
        try {
            empleados.actualizarEmpleado(idEmpleado, modificarEmpleados);
        } catch (CustomException e) {
            throw e; // Propagate the custom exception
        } catch (Exception e) {
            throw new CustomException("Error al actualizar en la base de datos");
        }
    }

    
}
