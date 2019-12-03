package com.biblioteca.control;

import com.biblioteca.conexion.Conexion;
import com.biblioteca.conexion.ConexionPool;
import com.biblioteca.entidades.Prestamo;
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

@WebServlet(name = "LisPrestamo", urlPatterns = {"/LisPrestamos"})
public class LisPrestamos extends HttpServlet {

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
                            "	idprestamo,\n" +
                            "	(select titulo from libros where idlibro = p.idlibro) as libro,\n" +
                            "	(select estado from estado where idestado = p.idestado) as estado,\n" +
                            "	fechadevolucion\n" +
                            " from prestamos p where titulo like ?";
                } else {
                    sql = "select \n" +
                            "	idprestamo,\n" +
                            "	(select titulo from libros where idlibro = p.idlibro) as libro,\n" +
                            "	(select estado from estado where idestado = p.idestado) as estado,\n" +
                            "	fechadevolucion\n" +
                            " from prestamos p";
                }
                String[][] prestamos = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    prestamos = Operaciones.consultar(sql, params);
                } else {
                    prestamos = Operaciones.consultar(sql, null);
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
                "50%", //ancho de la tabla px | % 
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT,  // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla

                tab.setPie("Resultado Prestamos");

                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("prestamos1/prestamos_consulta_1.jsp").forward(request, response);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(LisPrestamos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(LisPrestamos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    
}