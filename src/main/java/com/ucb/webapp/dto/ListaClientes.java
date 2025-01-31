package com.ucb.webapp.dto;

public class ListaClientes {
    private Long idCliente;
    private String nombre;
    private String apelllido;
    private String carnet;
    private String telefono;

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

    public String getApelllido() {
        return apelllido;
    }

    public void setApelllido(String apelllido) {
        this.apelllido = apelllido;
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

    @Override
    public String toString() {
        return "ListarClientes{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apelllido='" + apelllido + '\'' +
                ", carnet='" + carnet + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
