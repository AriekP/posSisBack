package com.ucb.webapp.dto;

import java.math.BigDecimal;

public class ListaProductos {
    private Long idProducto;
    private String nombre;
    private String modelo;
    private int cantidad;
    private String descripcion;
    private BigDecimal precio;
    private String marca;
    private String categoria;

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ListaProductos{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", marca='" + marca + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
