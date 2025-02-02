package com.ucb.webapp.model;

public class Clientes {
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String carnet;
    private String telefono;
    private Boolean borrado;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "ModelClientes{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apelllido='" + apellido + '\'' +
                ", carnet='" + carnet + '\'' +
                ", telefono='" + telefono + '\'' +
                ", borrado=" + borrado +
                '}';
    }
}
