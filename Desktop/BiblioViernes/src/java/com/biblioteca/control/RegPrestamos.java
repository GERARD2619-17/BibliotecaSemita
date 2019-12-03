package com.biblioteca.control;

import com.biblioteca.conexion.Conexion;
import com.biblioteca.conexion.ConexionPool;
import com.biblioteca.entidades.Prestamo;
import com.biblioteca.operaciones.Operaciones;
import com.biblioteca.utilerias.Tabla;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegPrestamos", urlPatterns = {"/RegPrestamos"})
public class RegPrestamos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();
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
                String sql = "select idprestamo, FORMAT(fechadevolucion, 'dd/MM/yyyy HH:mm', 'en-US') as date,"
                        + "libr.titulo as libros,\n,"
                        + "esta.estado as estado\n"
                        + "from prestamos\n"
                        + "inner join libros as libr on libr.idlibro = prestamos.idlibro\n"
                        + "inner join estado as esta on esta.idestado = prestamos.idestado";
                String sql2 = "select \n" +
                            "	idprestamo,\n" +
                            "	(select titulo from libros where idlibro = p.idlibro) as libro,\n" +
                            "	(select estado from estado where idestado = p.idestado) as estado,\n" +
                            "	fechadevolucion\n" +
                            " from prestamos p";
                out.println(1);
                String[][] prestamos = null;
                if (request.getParameter("idlibro") != null) {
                out.println(2);
                    List<Object> param = new ArrayList();
                out.println(3);
                    param.add(request.getParameter("idlibro"));
                out.println(4);
                    request.setAttribute("idlibro", request.getParameter("idlibro"));
                    request.setAttribute("titulo", request.getParameter("titulo"));
                    sql += " where prestamos.idlibro = ?";
                    sql2 += " where idlibro = ?";
                    prestamos = Operaciones.consultar(sql2, param);
                out.println(5);
                }
//declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Prestamos",
                    "Titulo de Libros",
                    "Estado de Libros",
                    "Fecha de entrega"
                    
                };
//variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(prestamos, //array que contiene los datos
                        "100%", //ancho de la tabla px | %
                        Tabla.STYLE.TABLE01, //estilo de la tabla
                        Tabla.ALIGN.CENTER, // alineacion de la tabla
                        cabeceras); //array con las cabeceras de la tabla
//boton eliminar
                tab.setEliminable(true);
//url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setSeleccionable(false);
//pagina encargada de eliminar
                tab.setPaginaEliminable("/Prestamos?accion=eliminar");
//pagina encargada de seleccion para operaciones
//tab.setPaginaSeleccionable("/Paises");
//icono para modificar y eliminar
//tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
//columnas seleccionables
//tab.setColumnasSeleccionables(new int[]{1});
//pie de tabla
                tab.setPie("Resultado Prestamos");
//imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (prestamos != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("prestamos1/prestamos_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                out.println(ex.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("listado_origen")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "select idlibro, titulo, edicion, isbn, aut.autor as autor,clas.clasificacion as clasificacion,edit.editorial as editorial from libros inner join autor as aut on aut.idautor = libros.idautor inner join clasificacion as clas on clas.idclasificacion = libros.idclasificacion inner join editorial as edit on edit.ideditorial = libros.ideditorial";
                        
                String[][] libros = Operaciones.consultar(sql, null);
//declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Libros",
                    "Libros",
                    "Edicion",
                    "ISBM",
                    "Autor",
                    "Clasificacion",
                    "Editorial"
                };
//variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(libros, //array que contiene los datos
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
                tab.setPie("Resultado Libros");
//imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (libros != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("prestamos1/libros_origen.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Libros.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (accion.equals("listado_libros")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();

                String sql = "select libr.idlibro, libr.titulo from libros as libr inner join libros as li on li.idlibro = libr.idlibro;";

                String[][] libros = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Libro",
                    "Titulo"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(libros, //array que contiene los datos
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
                tab.setPie("Resultado Libros");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if (libros != null) {
                    tabla01 = tab.getTabla();
                }
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("prestamos1/listado_libros.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (accion.equals("listado_estados")) {
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
                request.getRequestDispatcher("prestamos1/listado_estados.jsp").forward(request, response);
            } catch (Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String idlibro = request.getParameter("txtIdlibro");
        String titulo = request.getParameter("txtTitulo");
        String idestado = request.getParameter("txtIdestado");
        String fechadevolucion = request.getParameter("txtFechaD");
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            Prestamo prestamos = new Prestamo();
            prestamos.setIdlibro(Integer.parseInt(idlibro));
            prestamos.setIdestado(Integer.parseInt(idestado));
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fechadevolucion);
            prestamos.setFechadevolucion(new Timestamp(date.getTime()));
            //libros.set("Disponible");
            prestamos = Operaciones.insertar(prestamos);
            
            String sqlinsert = "insert into prestamos"; 
            if (prestamos.getIdlibro() != 0) {
                request.getSession().setAttribute("resultado", 1);
            } else {
                request.getSession().setAttribute("resultado", 0);
            }
            Operaciones.commit();

            response.sendRedirect(request.getContextPath() + "/RegPrestamos?idlibro=" + idlibro + "&titulo=" + titulo);
        } catch (Exception ex) {
            try {
                Operaciones.rollback();
                out.println(ex.getMessage());
            } catch (SQLException ex1) {
                Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex1);
            }
            request.getSession().setAttribute("resultado", 2);
            ex.printStackTrace();
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RegPrestamos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
