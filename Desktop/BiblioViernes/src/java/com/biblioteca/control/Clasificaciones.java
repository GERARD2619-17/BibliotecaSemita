package com.biblioteca.control;

import com.biblioteca.entidades.Clasificacion;
import com.biblioteca.conexion.Conexion;
import com.biblioteca.conexion.ConexionPool;
import com.biblioteca.entidades.Menu;
import com.biblioteca.operaciones.Operaciones;
import com.biblioteca.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Clasificacion", urlPatterns = {"/Clasificacion"})
public class Clasificaciones extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
//        HttpSession s = request.getSession();
//        List<Menu> per = (List<Menu>)s.getAttribute("Permisos");
//        List<Menu> MenuPrincipal = per.stream().filter(field -> field.getIdpadre()==0).collect(Collectors.toList());
//        request.setAttribute("MenuPrincipal", MenuPrincipal);
//        String op = request.getParameter("op");
//        if (op!=null){
//            List<Menu> PermisosAsignados = per.stream().filter(field -> field.getIdpadre()==Integer.parseInt(op)).collect(Collectors.toList());
//            request.setAttribute("PermisosAsignados", PermisosAsignados);
//        }
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
                    sql = "select * from clasificacion where clasificacion like ?";
                } else {
                    sql = "select * from clasificacion";
                }
                String[][] clasificaciones = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    clasificaciones = Operaciones.consultar(sql, params);
                } else {
                    clasificaciones = Operaciones.consultar(sql, null);
                }

                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                  "ID Clasificacion",
                  "Nombre Clasificacion"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(clasificaciones, //array que contiene los datos
                "50%", //ancho de la tabla px | % 
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT,  // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla

                //boton eliminar
                tab.setEliminable(true);
                //boton actualizar
                tab.setModificable(true); 
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/Clasificacion?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Clasificacion?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Clasificacion?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png"); 
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado clasificacion");

                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("clasificacion/clasificaciones_consulta.jsp").forward(request, response);
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            request.getRequestDispatcher("clasificacion/insertar_modificar.jsp").forward(request, response);
        }  else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Clasificacion p = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Clasificacion());
                request.setAttribute("clasificacion", p);
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("clasificacion/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Clasificacion p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Clasificacion());
                if(p.getIdclasificacion()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Clasificacion");
        } 
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch(accion) {
            case "insertar_modificar": {
                String idClasificacion = request.getParameter("txtIdclasificacion");
                String clasificacion = request.getParameter("txtClasificacion");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    if(idClasificacion!=null && !idClasificacion.equals("")) {
                        Clasificacion p = new Clasificacion(Integer.parseInt(idClasificacion), clasificacion);
                        p = Operaciones.actualizar(p.getIdclasificacion(), p);
                        if(p.getIdclasificacion()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Clasificacion p = new Clasificacion();
                        p.setClasificacion(clasificacion);
                        p = Operaciones.insertar(p);
                        if(p.getIdclasificacion()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }
                    Operaciones.commit();
                } catch(Exception ex) {
                    try {
                        Operaciones.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    request.getSession().setAttribute("resultado", 2);
                    ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Clasificaciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Clasificacion");
                break;
            }
            case "eliminar": {
                break;
            }
        }
    }

}