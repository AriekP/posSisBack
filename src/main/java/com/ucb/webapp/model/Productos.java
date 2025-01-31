package com.ucb.webapp.model;

import java.math.BigDecimal;

public class Productos {
    private Long idProducto;
    private String nombre;
    private String modelo;
    private int cantidad;
    private String descripcion;
    private BigDecimal precio;
    private Boolean borrado;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "ModelProductos{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", borrado=" + borrado +
                '}';
    }
}
