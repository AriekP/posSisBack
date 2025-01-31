package com.ucb.webapp.dto;

public class ListaCategorias {
    private Long idCategoria;
    private String categoria;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ListaCategoria{" +
                "idCategoria=" + idCategoria +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
