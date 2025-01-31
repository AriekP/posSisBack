package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoClientes;
import com.ucb.webapp.dto.ListaClientes;
import com.ucb.webapp.dto.ManejoClientes;
import com.ucb.webapp.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlClientes {
    @Autowired
    private DaoClientes clientes;

    public List<ListaClientes> mostrar(){
        try {
            return clientes.listarClientes();
        } catch (CustomException e) {
            throw e; // Propagate the custom exception
        }
    }

    public void agregarCliente(ManejoClientes registrarClientes) {
        try {
            clientes.insertarCliente(registrarClientes);
        } catch (CustomException e) {
            throw e; // Propagate the custom exception
        } catch (Exception e) {
            throw new CustomException("Error al insertar en la base de datos");
        }
    }

    public void actualizarCliente(Long idCliente, ManejoClientes modificarClientes) {
        try {
            clientes.actualizarCliente(idCliente, modificarClientes);
        } catch (CustomException e) {
            throw e; // Propagate the custom exception
        } catch (Exception e) {
            throw new CustomException("Error al actualizar en la base de datos");
        }
    }
}
