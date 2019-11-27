package com.biblioteca.entidades;

import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;
@Entity(table = "libros")
public class Libro {
    @PrimaryKey
    @AutoIncrement
    private Integer idlibro;
    private String titulo;
    private Integer idautor;
    private Integer ideditorial;
    private Integer idestado;
    private Integer idclasificacion;
    private String edicion;
    private String isbn;
    private String imagen;
   
    public Libro() {
    }

    public Libro(Integer idlibro, String titulo, Integer idautor, Integer ideditorial, Integer idestado, Integer idclasificacion, String edicion, String isbn, String imagen) {
        this.idlibro = idlibro;
        this.titulo = titulo;
        this.idautor = idautor;
        this.ideditorial = ideditorial;
        this.idestado = idestado;
        this.idclasificacion = idclasificacion;
        this.edicion = edicion;
        this.isbn = isbn;
        this.imagen = imagen;
    }
 
    public Libro(int parseInt, String titulo, String idautor, String ideditorial, String idestado, String idclasificacion, String edicion, String bn, String anio, String imagen, String idautores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Libro(int i, String autor, String titulo, int i0, int i1, int i2, int i3, String bm, String edicion, String imagen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getIdlibro() {
        return idlibro;
    }

    public void setIdlibro(Integer idlibro) {
        this.idlibro = idlibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getIdautor() {
        return idautor;
    }

    public void setIdautor(Integer idautor) {
        this.idautor = idautor;
    }

    public Integer getIdeditorial() {
        return ideditorial;
    }

    public void setIdeditorial(Integer ideditorial) {
        this.ideditorial = ideditorial;
    }

    public Integer getIdestado() {
        return idestado;
    }

    public void setIdestado(Integer idestado) {
        this.idestado = idestado;
    }

    public Integer getIdclasificacion() {
        return idclasificacion;
    }

    public void setIdclasificacion(Integer idclasificacion) {
        this.idclasificacion = idclasificacion;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
//
//    public void setAutor(String autor) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    
}

