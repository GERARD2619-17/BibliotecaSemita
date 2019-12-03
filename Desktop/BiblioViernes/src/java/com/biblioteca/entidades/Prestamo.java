package com.biblioteca.entidades;

import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;
import java.sql.Date;
import java.sql.Timestamp;
@Entity(table = "prestamos")
public class Prestamo{
    @PrimaryKey
    @AutoIncrement
    private Integer idprestamo;
    private Integer idestado;
    private Integer idlibro;
    private Timestamp fechadevolucion;

    public Prestamo() {
    }

    public Prestamo(Integer idprestamo, Integer idestado, Integer idlibro, Timestamp fechadevolucion) {
        this.idprestamo = idprestamo;
        this.idestado = idestado;
        this.idlibro = idlibro;
        this.fechadevolucion = fechadevolucion;
    }

    public Integer getIdprestamo() {
        return idprestamo;
    }

    public void setIdprestamo(Integer idprestamo) {
        this.idprestamo = idprestamo;
    }

    public Integer getIdestado() {
        return idestado;
    }

    public void setIdestado(Integer idestado) {
        this.idestado = idestado;
    }

    public Integer getIdlibro() {
        return idlibro;
    }

    public void setIdlibro(Integer idlibro) {
        this.idlibro = idlibro;
    }

    public Timestamp getFechadevolucion() {
        return fechadevolucion;
    }

    public void setFechadevolucion(Timestamp fechadevolucion) {
        this.fechadevolucion = fechadevolucion;
    }
    
    
}
