package com.ucb.webapp.model;

import java.math.BigDecimal;

public class ModelVentasProductos {
    private Long idVentaProducto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;

    public Long getIdVentaProducto() {
        return idVentaProducto;
    }

    public void setIdVentaProducto(Long idVentaProducto) {
        this.idVentaProducto = idVentaProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "ModelVentasProductos{" +
                "idVentaProducto=" + idVentaProducto +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
