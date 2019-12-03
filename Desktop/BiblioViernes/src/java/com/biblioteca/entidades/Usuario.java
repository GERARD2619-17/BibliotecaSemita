package com.biblioteca.entidades;
import com.biblioteca.anotaciones.*;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;
@Entity(table = "usuario")
public class Usuario{
    @PrimaryKey
    private String idusuario;
    @FieldName(name="nombres")
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;
    private String clave;
    private Integer idrol;

    public Usuario() {
    }

    public Usuario(String idusuario, String nombres, String apellidos, String telefono, String email, String clave, Integer idrol) {
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getIdrol() {
        return idrol;
    }

    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "idusuario=" + idusuario + ", nombres=" + nombres + ", apellidos=" + apellidos + '}';
    }
}
