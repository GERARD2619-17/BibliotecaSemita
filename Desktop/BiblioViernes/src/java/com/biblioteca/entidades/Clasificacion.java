package com.biblioteca.entidades;

import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;

@Entity(table = "clasificacion")
public class Clasificacion{
    @PrimaryKey
    @AutoIncrement
    private int idclasificacion;
    private String clasificacion;

    public Clasificacion() {
    }

    public Clasificacion(int idclasificacion, String clasificacion) {
        this.idclasificacion = idclasificacion;
        this.clasificacion = clasificacion;
    }

    public int getIdclasificacion() {
        return idclasificacion;
    }

    public void setIdclasificacion(int idclasificacion) {
        this.idclasificacion = idclasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    
}

