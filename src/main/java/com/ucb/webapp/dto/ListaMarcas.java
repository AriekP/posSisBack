package com.ucb.webapp.dto;

public class ListaMarcas {
    private Long idMarca;
    private String marca;

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "ModelMarcas{" +
                "idMarca=" + idMarca +
                ", marca='" + marca + '\'' +
                '}';
    }
}