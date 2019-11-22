package com.aerolinea.control;

import com.aerolinea.conexion.Conexion;
import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.entidad.Vuelo;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.utilerias.Tabla;
import java.io.IOException;
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

@WebServlet(name = "Reservaciones", urlPatterns = {"/Reservaciones"})
public class Reservaciones extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion == null) {
            if(request.getSession().getAttribute("resultado")!=null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "select\n" +
                            "	idreservacion,\n" +
                            "	(select (select p.pais from pais p where p.idpais = v.idorigen) from vuelo v where v.idvuelo = r.idvuelo) as origen,\n" +
                            "	(select (select p.pais from pais p where p.idpais = v.iddestino) from vuelo v where v.idvuelo = r.idvuelo) as destino,\n" +
                            "	(select v.fecha from vuelo v where v.idvuelo = r.idvuelo) as fecha_vuelo\n" +
                            "   nboletos, precio, fecha\n" +
                            "from reservacion r\n" +
                            "where r.idusuario = ?";
                String[][] reservaciones = null;
                if (request.getSession().getAttribute("idusuario")!=null){
                    List<Object> param = new ArrayList();
                    param.add(request.getSession().getAttribute("idusuario"));
                    request.setAttribute("idorigen", request.getParameter("idorigen"));
                    request.setAttribute("origen", request.getParameter("origen"));
                    reservaciones = Operaciones.consultar(sql, param);
                }
//                else{
//                    vuelos= Operaciones.consultar(sql, new ArrayList());                    
//                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Reservación",
                    "Origen",
                    "Destino",
                    "Fecha de vuelo",
                    "# de boletos",
                    "Precio",
                    "Fecha de reservación"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(reservaciones, //array que contiene los datos
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
                tab.setPaginaEliminable("/Reservaciones?accion=eliminar");
                //pagina encargada de seleccion para operaciones
                //tab.setPaginaSeleccionable("/Paises");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                //tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado reservaciones");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (reservaciones!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("reservaciones/reservaciones_consulta.jsp").forward(request, response);
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Reservaciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Reservaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if(accion.equals("listado_vuelos")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "select\n" +
                            "	idvuelo,\n" +
                            "	(select p.pais from pais p where p.idpais = v.idorigen) as origen,\n" +
                            "	(select p.pais from pais p where p.idpais = v.iddestino) as destino,\n" +
                            "	precio, fecha, estado,\n" +
                            "	(select a.descripcion from avion a where a.idavion = v.idavion) as avion\n" +
                            "from vuelo v;";
                String[][] origenes = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Vuelo",
                    "Origen",
                    "Destino",
                    "Precio",
                    "Fecha",
                    "Estado",
                    "Avión"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(origenes, //array que contiene los datos
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
//                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado vuelos");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (origenes!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("reservaciones/listado_vuelos.jsp").forward(request,
                response);
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
        } else if(accion.equals("listado_destino")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "select aero.idaeropuerto, aero.aeropuerto,\n" +
                "pa.pais, aero.ciudad\n" +
                "from aeropuerto as aero\n" +
                "inner join pais as pa on pa.idpais = aero.idpais";
                String[][] destinos = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Aeropuerto",
                    "Aeropuerto",
                    "Pais",
                    "Ciudad"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(destinos, //array que contiene los datos
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
                tab.setPie("Resultado Aeropuertos");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (destinos!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("vuelos/aeropuertos_destino.jsp").forward(request,
                response);
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
        } else if(accion.equals("listado_avion")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "select idavion, descripcion, capacidad from avion";
                String[][] aviones = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Avion",
                    "Avion",
                    "Capacidad"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(aviones, //array que contiene los datos
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
                tab.setPie("Resultado Aeropuertos");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (aviones!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("vuelos/listado_aviones.jsp").forward(request, response);
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
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                Vuelo p = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Vuelo());
                if(p.getIdvuelo()!=0) {
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
            response.sendRedirect(request.getContextPath()+"/Reservaciones");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String idorigen = request.getParameter("txtIdvuelo");
        String origen = request.getParameter("txtOrigen");
        String fecha = request.getParameter("txtDestino");
        String idavion = request.getParameter("txtFecha");
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            Vuelo vuelo = new Vuelo();
//            vuelo.setIdavion(Integer.parseInt(idavion));
//            vuelo.setIdorigen(Integer.parseInt(idorigen));
//            vuelo.setIddestino(Integer.parseInt(iddestino));
            vuelo.setEstado("Disponible");
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(fecha);
            vuelo.setFecha(new Timestamp(date.getTime()));
            vuelo = Operaciones.insertar(vuelo);
            if(vuelo.getIdvuelo()!=0) {
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
            request.getSession().setAttribute("resultado", 2);
            ex.printStackTrace();
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Paises.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect(request.getContextPath()+"/Reservaciones?idorigen="+idorigen+"&origen="+origen);
    }
}