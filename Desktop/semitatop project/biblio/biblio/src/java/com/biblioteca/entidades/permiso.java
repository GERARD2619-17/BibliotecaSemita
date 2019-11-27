package com.biblioteca.entidades;
import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;
@Entity(table = "permiso")
public class permiso {
    @PrimaryKey
    @AutoIncrement
    private Integer idpermiso;
    private Integer idmenu;
    private Integer idrol;

    public permiso() {
    }

    public permiso(Integer idpermiso, Integer idmenu, Integer idrol) {
        this.idpermiso = idpermiso;
        this.idmenu = idmenu;
        this.idrol = idrol;
    }

    public Integer getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(Integer idpermiso) {
        this.idpermiso = idpermiso;
    }

    public Integer getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Integer idmenu) {
        this.idmenu = idmenu;
    }

    public Integer getIdrol() {
        return idrol;
    }

    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }
    
    
}
