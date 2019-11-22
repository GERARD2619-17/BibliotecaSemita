package com.aerolinea.entidad;

import com.aerolinea.anotaciones.*;

@Entity(table = "avion")
public class Avion {
    @PrimaryKey
    @AutoIncrement
    @NotNull
    private int idavion;
    @NotNull
    private int capacidad;
    @NotNull
    private String descripcion;

    public Avion() {
    }

    public Avion(int idavion, int capacidad, String descripcion) {
        this.idavion = idavion;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public int getIdavion() {
        return idavion;
    }

    public void setIdavion(int idavion) {
        this.idavion = idavion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
