package com.aerolinea.control;

import com.aerolinea.conexion.Conexion;
import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.entidad.*;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.utilerias.Hash;
import com.aerolinea.utilerias.Tabla;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Usuarios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter io = response.getWriter();
        String accion = request.getParameter("accion");
        if(accion==null) {
            if(request.getSession().getAttribute("resultado")!=null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "";
                if(request.getParameter("txtBusqueda")!=null) {
                    sql = "select idusuario, nombres, apellidos, email, telefono, idpais, idrol from usuario where idusuario like ?";
                } else {
                    sql = "select idusuario, nombres, apellidos, email, telefono, idpais, idrol from usuario";
                }
                String[][] usuario = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    usuario = Operaciones.consultar(sql, params);
                } else {
                    usuario = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Usuario",
                    "Nombres",
                    "Apellidos",
                    "Email",
                    "Telefono",
                    "ID País",
                    "ID Rol"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(usuario, //array que contiene los datos
                "75%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                //boton eliminar
                tab.setEliminable(true);
                //boton actualizar
                tab.setModificable(true);
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/Usuarios?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Usuarios?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Usuarios?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado usuarios");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("usuarios/usuarios_consulta.jsp").forward(request, response);
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("usuarios/usuarios_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sqlpais = "select * from pais";
                String sqlrol = "select * from rol";
                
                List<Pais> listaPaises = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlpais, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Pais p = new Pais(Integer.parseInt(rs[0][i]), rs[1][i]);
                    listaPaises.add(p);
                }
                request.setAttribute("Paises", listaPaises);
                
                List<Rol> listaRoles = new ArrayList();
                String[][] rsroles = Operaciones.consultar(sqlrol, new ArrayList());
                for(int i=0; i<rsroles[0].length; i++){
                    Rol p = new Rol(Integer.parseInt(rsroles[0][i]), rsroles[1][i]);
                    listaRoles.add(p);
                }
                request.setAttribute("Roles", listaRoles);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("usuarios/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Usuario u = Operaciones.get(request.getParameter("id"), new Usuario());
                request.setAttribute("usuario", u);
                String sqlpais = "select * from pais";
                String sqlrol = "select * from rol";
                
                List<Pais> listaPaises = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlpais, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Pais p = new Pais(Integer.parseInt(rs[0][i]), rs[1][i]);
                    listaPaises.add(p);
                }
                request.setAttribute("Paises", listaPaises);
                
                List<Rol> listaRoles = new ArrayList();
                String[][] rsroles = Operaciones.consultar(sqlrol, new ArrayList());
                for(int i=0; i<rsroles[0].length; i++){
                    Rol p = new Rol(Integer.parseInt(rsroles[0][i]), rsroles[1][i]);
                    listaRoles.add(p);
                }
                request.setAttribute("Roles", listaRoles);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getSession().setAttribute("mod", 1);
            request.setAttribute("readonly", "readonly='readonly'");
            request.getRequestDispatcher("usuarios/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Usuario u = Operaciones.eliminar(request.getParameter("id"), new Usuario());
                if(u.getIdusuario()!=null) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Usuarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter io = response.getWriter();
        String accion = request.getParameter("accion");
        switch(accion) {
            case "insertar_modificar": {
                String idUsuario = request.getParameter("txtIdusuario");
                String clave = request.getParameter("txtClave");
                String nombres = request.getParameter("txtNombres");
                String apellidos = request.getParameter("txtApellidos");
                String email = request.getParameter("txtEmail");
                String telefono = request.getParameter("txtTelefono");
                int idPais = Integer.parseInt(request.getParameter("cmbPaises"));
                int idRol = Integer.parseInt(request.getParameter("cmbRoles"));
                io.println("1 "+request.getSession().getAttribute("mod") + " " + idUsuario + " " + clave + " " + nombres + " " + apellidos + " " + email + " " + telefono + " " + idPais+ " " + idRol);
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if(request.getSession().getAttribute("mod")!=null) {
                        Usuario u = new Usuario(idUsuario, nombres, apellidos, email, telefono, clave, idPais, idRol);
                        
                        io.println("2 "+u.getIdusuario() + u.getNombres() + u.getApellidos() + u.getClave() + u.getEmail() + u.getTelefono() + u.getIdpais() + u.getIdrol());
                        u = Operaciones.actualizar(u.getIdusuario(), u);
                        io.println("3 "+u.getIdusuario() + u.getNombres());
                        if(!u.getIdusuario().equals("")) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Usuario u = new Usuario();
                        
                        u.setIdusuario(idUsuario);
                        u.setNombres(nombres);
                        u.setApellidos(apellidos);
                        u.setClave(Hash.generarHash(clave, Hash.SHA256));
                        u.setEmail(email);
                        u.setTelefono(telefono);
                        u.setIdpais(idPais);
                        u.setIdrol(idRol);
                        
                        io.println("4 "+u.getIdusuario() + u.getApellidos());
                        u = Operaciones.insertar(u);
                        io.println("5 "+u.getIdusuario() + u.getApellidos());
                        if(u.getIdusuario() != null) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }
                    Operaciones.commit();
                } catch(Exception ex) {
                    io.println(ex.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                request.getSession().removeAttribute("mod");
                io.println("mod: "+request.getSession().getAttribute("mod"));
//                response.sendRedirect(request.getContextPath()+"/Usuarios");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
