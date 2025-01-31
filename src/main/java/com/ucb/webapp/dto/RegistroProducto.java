package com.ucb.webapp.dto;

import java.math.BigDecimal;

public class RegistroProducto {
    private String nombre;
    private String modelo;
    private int cantidad;
    private String descripcion;
    private BigDecimal precio;
    private Boolean borrado;
    private int idMarca;  // ✅ Atributo agregado
    private int idCategoria;  // ✅ Atributo agregado
    private int idEmpleado;  // ✅ Atributo agregado

    // Getters y Setters
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

    public int getIdMarca() {  // ✅ Método agregado
        return idMarca;
    }

    public void setIdMarca(int idMarca) {  // ✅ Método agregado
        this.idMarca = idMarca;
    }

    public int getIdCategoria() {  // ✅ Método agregado
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {  // ✅ Método agregado
        this.idCategoria = idCategoria;
    }

    public int getIdEmpleado() {  // ✅ Método agregado
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {  // ✅ Método agregado
        this.idEmpleado = idEmpleado;
    }
}
