package com.ucb.webapp.bl;

import com.ucb.webapp.dao.DaoLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class BlLogs {

    @Autowired
    private DaoLogs daoLogs;

    public void registrarEvento(String usuario, String evento) {
        if (usuario == null || usuario.trim().isEmpty()) {
            System.err.println("⚠️ Advertencia: Se intentó registrar un evento sin usuario válido.");
            return; // No registrar el evento si no hay usuario válido
        }
        if (evento == null || evento.trim().isEmpty()) {
            evento = "Evento desconocido";
        }
        
        try {
            System.out.println("✅ Registrando evento: Usuario=" + usuario + ", Evento=" + evento);
            daoLogs.registrarLog(usuario, evento);
            System.out.println("✔ Evento registrado correctamente en la base de datos.");
        } catch (Exception e) {
            System.err.println("❌ Error al registrar evento: " + e.getMessage());
        }
    }

    public List<Map<String, String>> obtenerTodosLosLogs() {
        System.out.println("📌 Obteniendo todos los logs desde la base de datos...");
        List<Map<String, String>> logs = null;
        try {
            logs = daoLogs.obtenerLogs();
            System.out.println("📌 Se encontraron " + logs.size() + " logs.");
        } catch (Exception e) {
            System.err.println("❌ Error al obtener logs: " + e.getMessage());
        }
        return logs;
    }
}
