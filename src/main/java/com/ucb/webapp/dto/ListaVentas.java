package com.ucb.webapp.dto;

import java.math.BigDecimal;

public class ListaVentas {
    private Long idVenta;
    private String fecha;
    private BigDecimal total;
    private String carnet;
    private String alias;

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

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "ListaVenta{" +
                "idVenta=" + idVenta +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                ", carnet='" + carnet + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
