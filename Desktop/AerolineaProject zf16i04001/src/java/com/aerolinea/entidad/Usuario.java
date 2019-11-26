package com.aerolinea.entidad;

import com.aerolinea.anotaciones.*;

@Entity(table = "usuario")
public class Usuario {
    @PrimaryKey
    @FieldName(name = "idusuario")
    private String idusuario;    
    @NotNull
    @FieldName(name = "nombres")
    private String nombres;
    @NotNull
    @FieldName(name = "apellidos")
    private String apellidos;
    @NotNull
    @FieldName(name = "email")
    private String email;
    @FieldName(name = "telefono")
    private String telefono;
    @NotNull
    @FieldName(name = "clave")
    private String clave;
    @NotNull
    @FieldName(name = "idpais")
    private int idpais;
    @NotNull
    @FieldName(name = "idrol")
    private int idrol;

    public Usuario() {
    }

    public Usuario(String idusuario, String nombres, String apellidos, String email, String telefono, String clave, Integer idpais, Integer idrol) {
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.clave = clave;
        this.idpais = idpais;
        this.idrol = idrol;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getIdpais() {
        return idpais;
    }

    public void setIdpais(Integer idpais) {
        this.idpais = idpais;
    }

    public Integer getIdrol() {
        return idrol;
    }

    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }
    
}
