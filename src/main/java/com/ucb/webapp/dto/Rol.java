// Modelo Rol
package com.ucb.webapp.dto;

public class Rol {
    private int id;
    private String nombre;

    // Constructor vacío (necesario para Spring)
    public Rol() {}

    // Constructor con parámetros
    public Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
