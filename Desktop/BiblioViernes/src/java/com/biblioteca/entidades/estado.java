package com.biblioteca.entidades;

import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;

@Entity(table = "estado")
public class estado{
    @PrimaryKey
    @AutoIncrement
    private int idestado ;
    private String estado;

    public estado() {
    }

    public estado(int idestado, String estado) {
        this.idestado = idestado;
        this.estado = estado;
    }

    public int getIdestado() {
        return idestado;
    }

    public void setIdestado(int idestado) {
        this.idestado = idestado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}


