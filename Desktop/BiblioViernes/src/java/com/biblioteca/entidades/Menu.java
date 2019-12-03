
package com.biblioteca.entidades;

import com.biblioteca.anotaciones.AutoIncrement;
import com.biblioteca.anotaciones.Entity;
import com.biblioteca.anotaciones.PrimaryKey;

@Entity(table = "menu")
public class Menu {
    @PrimaryKey
    @AutoIncrement
    private Integer idmenu;
    private String menu;
    private String descripcion;
    private String url;
    private Integer idpadre;

    public Menu() {
    }

    public Menu(Integer idmenu, String menu, String descripcion, String url, Integer idpadre) {
        this.idmenu = idmenu;
        this.menu = menu;
        this.descripcion = descripcion;
        this.url = url;
        this.idpadre = idpadre;
    }

    public Integer getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Integer idmenu) {
        this.idmenu = idmenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(Integer idpadre) {
        this.idpadre = idpadre;
    }

    
}
