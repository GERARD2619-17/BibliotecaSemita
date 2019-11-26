package com.aerolinea.entidad;

import com.aerolinea.anotaciones.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(table = "vuelo")
public class Vuelo {
    @PrimaryKey
    @AutoIncrement
    @NotNull
    private int idvuelo;
    @NotNull
    private int idorigen;
    @NotNull
    private int iddestino;
    private BigDecimal precio;
    @NotNull
    private Date fecha; //TimeStamp si es datetime en la bd
    @NotNull
    private String estado;
    @NotNull
    private int idavion;

    public Vuelo() {
    }

    public Vuelo(int idvuelo, int idorigen, int iddestino, BigDecimal precio, Date fecha, String estado, int idavion) {
        this.idvuelo = idvuelo;
        this.idorigen = idorigen;
        this.iddestino = iddestino;
        this.precio = precio;
        this.fecha = fecha;
        this.estado = estado;
        this.idavion = idavion;
    }

    public int getIdvuelo() {
        return idvuelo;
    }

    public void setIdvuelo(int idvuelo) {
        this.idvuelo = idvuelo;
    }

    public int getIdorigen() {
        return idorigen;
    }

    public void setIdorigen(int idorigen) {
        this.idorigen = idorigen;
    }

    public int getIddestino() {
        return iddestino;
    }

    public void setIddestino(int iddestino) {
        this.iddestino = iddestino;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdavion() {
        return idavion;
    }

    public void setIdavion(int idavion) {
        this.idavion = idavion;
    }
    
}
