package com.biblioteca.control;

import com.biblioteca.conexion.Conexion;
import com.biblioteca.conexion.ConexionPool;
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

@WebServlet(name = "LisLibros", urlPatterns = {"/LisLibros"})
public class LisLibros extends HttpServlet {

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
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado libros");

                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("libros1/libros_consulta.jsp").forward(request, response);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(LisLibros.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(LisLibros.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
                Logger.getLogger(LisLibros.class.getName()).log(Level.SEVERE, null, ex1);
            }
            request.getSession().setAttribute("resultado", 2);
            ex.printStackTrace();
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(LisLibros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect(request.getContextPath() + "/LisLibros");
    }
}