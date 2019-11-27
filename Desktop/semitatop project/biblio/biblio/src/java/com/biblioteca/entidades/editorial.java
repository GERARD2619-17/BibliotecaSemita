package com.biblioteca.entidades;

import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;

@Entity(table = "editorial")
public class editorial{
    @PrimaryKey
    @AutoIncrement
    private int ideditorial;
    private String editorial;

    public editorial() {
    }

    public editorial(int ideditorial, String editorial) {
        this.ideditorial = ideditorial;
        this.editorial = editorial;
    }

    public int getIdeditorial() {
        return ideditorial;
    }

    public void setIdeditorial(int ideditorial) {
        this.ideditorial = ideditorial;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
}

