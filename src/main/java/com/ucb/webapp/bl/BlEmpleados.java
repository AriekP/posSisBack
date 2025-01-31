package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoEmpleados;
import com.ucb.webapp.dto.ListaEmpleados;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.dto.ManejoEmpleados;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlEmpleados {
    @Autowired
    private DaoEmpleados empleados;

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
            Long idEmpleado = empleados.insertarEmpleado(registrarEmpleados);  // ✅ Asegurar que el nombre es correcto
            if (idEmpleado == null) {
                throw new CustomException("No se generó un ID para el nuevo empleado");
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error al insertar en la base de datos: " + e.getMessage());
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


    public void eliminarEmpleado(Long id) {
        try {
            empleados.eliminarEmpleadoPorId(id); // Llamar al DAO para eliminar el empleado
        } catch (Exception e) {
            throw new CustomException("Error al eliminar el empleado: " + e.getMessage());
        }
    }


}
