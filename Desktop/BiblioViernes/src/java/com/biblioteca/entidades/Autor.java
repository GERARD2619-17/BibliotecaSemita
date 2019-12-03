package com.biblioteca.entidades;


import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;

@Entity(table = "autor")
public class Autor{
    @PrimaryKey
    @AutoIncrement
    private Integer idautor;
    private String autor;

    public Autor() {
    }

    public Autor(Integer idautor, String autor) {
        this.idautor = idautor;
        this.autor = autor;
    }

    public Integer getIdautor() {
        return idautor;
    }

    public void setIdautor(Integer idautor) {
        this.idautor = idautor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
}