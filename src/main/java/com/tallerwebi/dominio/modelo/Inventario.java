package com.tallerwebi.dominio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Inventario {

    @Id
    private Long idProductoInventario;
    private String nombre;
    private String imagen;
    private Integer cantidad = 0;

    public Long getIdProductoInventario() {
        return idProductoInventario;
    }

    public void setIdProductoInventario(Long idProductoInventario) {
        this.idProductoInventario = idProductoInventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}