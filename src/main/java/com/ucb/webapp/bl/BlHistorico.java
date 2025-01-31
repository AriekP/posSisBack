package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoHistorico;
import com.ucb.webapp.dto.ListaHistorico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlHistorico {
    @Autowired
    private DaoHistorico registros;

    public List<ListaHistorico> mostrar(){
        try {
            return registros.listarRegistros();
        }catch (Exception e) {
            throw e;
        }
    }
}
