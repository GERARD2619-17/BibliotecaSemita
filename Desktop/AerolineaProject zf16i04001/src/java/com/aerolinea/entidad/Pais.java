package com.aerolinea.entidad;

import com.aerolinea.anotaciones.*;

@Entity(table = "pais")
public class Pais {
    @PrimaryKey
    @AutoIncrement
    private int idpais;
    
    private String pais;

    public Pais() {
    }

    public Pais(int idpais, String pais) {
        this.idpais = idpais;
        this.pais = pais;
    }

    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
}
