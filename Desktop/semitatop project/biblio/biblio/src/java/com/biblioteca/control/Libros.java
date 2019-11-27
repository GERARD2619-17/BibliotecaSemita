package com.biblioteca.control;

import com.biblioteca.conexion.Conexion;
import com.biblioteca.conexion.ConexionPool;
import com.biblioteca.entidades.Autor;
import com.biblioteca.entidades.Libro;
import com.biblioteca.operaciones.Operaciones;
import com.biblioteca.utilerias.Tabla;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Libros", urlPatterns = {"/Libros"})
public class Libros extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            if (request.getSession().getAttribute("resultado") != null) {
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
                    sql = "select \n" +
                        "idlibro,titulo,edicion,isbn,imagen,\n" +
                        "(select autor from autor where idautor = p.idautor) as autor,\n" +
                        "(select editorial from editorial where ideditorial = p.ideditorial) as editorial,\n" +
                        "(select clasificacion from clasificacion where idclasificacion = p.idclasificacion) as clasificacion,\n" +
                        "(select estado from estado where idestado = p.idestado) as estado\n" +
                        " from libros p where titulo like ?";
                } else {
                    sql = "select \n" +
                        "idlibro,titulo,edicion,isbn,imagen,\n" +
                        "(select autor from autor where idautor = p.idautor) as autor,\n" +
                        "(select editorial from editorial where ideditorial = p.ideditorial) as editorial,\n" +
                        "(select clasificacion from clasificacion where idclasificacion = p.idclasificacion) as clasificacion,\n" +
                        "(select estado from estado where idestado = p.idestado) as estado\n" +
                        " from libros p";
                }
                String[][] libros = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    libros = Operaciones.consultar(sql, params);
                } else {
                    libros = Operaciones.consultar(sql, null);
                }

                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Libro",
                    "Titilo",
                    "Edicion",
                    "ISBM",
                    "Imagen",
                    "Autor",
                    "Clasificacion",
                    "Editorial",
                    "Estado"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(libros, //array que contiene los datos
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
                tab.setPaginaEliminable("/Libros?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Libros?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Libros?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png"); 
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado libros");

                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("libros/libros_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("insertar")) {
            request.getRequestDispatcher("libros/insertar_modificar.jsp").forward(request, response);
        }  else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Libro p = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Libro());
                request.setAttribute("libros", p);
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("libros/insertar_modificar.jsp").forward(request, response);
            } else if (accion.equals("listado_autores")) {
                try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "select aut.idautor, aut.autor from autor as aut inner join autor as au on au.idautor = aut.idautor;";
                        
                String[][] autor = Operaciones.consultar(sql, null);
//declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Autor",
                    "Autor",
                };
//variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(autor, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
//url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
//icono para modificar y eliminar
// tab.setIconoModificable("/iconos/edit.png");
// tab.setIconoEliminable("/iconos/delete.png");
//columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
//pie de tabla
                tab.setPie("Resultado Autores");
//imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (autor != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("libros/listado_autores.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (accion.equals("listado_editoriales")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select edit.ideditorial, edit.editorial from editorial as edit inner join editorial as ed on ed.ideditorial = edit.ideditorial";

                String[][] editoriales = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Editorial",
                    "Editorial"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(editoriales, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Editoriales");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (editoriales != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("libros/listado_editoriales.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("listado_clasificaciones")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select clas.idclasificacion, clas.clasificacion from clasificacion as clas inner join clasificacion as cl on cl.idclasificacion = clas.idclasificacion;";

                String[][] clasificaciones = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Clasificacion",
                    "Clasificacion"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(clasificaciones, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Clasificaciones");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (clasificaciones != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("libros/listado_clasificaciones.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  else if (accion.equals("listado_estados")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select esta.idestado, esta.estado from estado as esta inner join estado as es on es.idestado = esta.idestado;";

                String[][] estados = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Estado",
                    "Estado"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(estados, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.LEFT, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Estados");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (estados != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("libros/listado_estados.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if (accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Libro p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Libro());
                if (p.getIdlibro()!= 0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath() + "/Libros");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String idautor = request.getParameter("txtIdautor");
        String autor = request.getParameter("txtAutor");
        String titulo = request.getParameter("txtTitulo");        
        String edicion = request.getParameter("txtEdicion");
        String isbn = request.getParameter("txtISBN");
        String imagen = request.getParameter("txtImage");
        String idclasificacion = request.getParameter("txtIdclasificacion");
        String ideditorial = request.getParameter("txtIdeditorial");
        String idestado = request.getParameter("txtIdestado");
        
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            Libro libros = new Libro();
            libros.setIdautor(Integer.parseInt(idautor));
//            libros.setAutor(autor);
            libros.setTitulo(titulo);
            libros.setEdicion(edicion);
            libros.setIsbn(isbn);
            libros.setImagen(imagen);
            libros.setIdclasificacion(Integer.parseInt(idclasificacion));
            libros.setIdeditorial(Integer.parseInt(ideditorial));
            libros.setIdestado(Integer.parseInt(idestado));
                                   //libros.set("Disponible");
            libros = Operaciones.insertar(libros);
            if (libros.getIdlibro() != 0) {
                request.getSession().setAttribute("resultado", 1);
            } else {

                request.getSession().setAttribute("resultado", 0);
            }
            Operaciones.commit();

        } catch (Exception ex) {
            try {
                out.println(ex.getMessage());
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex1);
            }
            request.getSession().setAttribute("resultado", 2);
            ex.printStackTrace();
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect(request.getContextPath() + "/Libros");
    }
}