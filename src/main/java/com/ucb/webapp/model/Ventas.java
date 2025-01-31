package com.ucb.webapp.model;

import java.math.BigDecimal;

public class Ventas {
    private Long idVenta;
    private String fecha;
    private BigDecimal total;
    private Boolean borrado;

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "ModelVentas{" +
                "idVenta=" + idVenta +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                ", borrado=" + borrado +
                '}';
    }
}
