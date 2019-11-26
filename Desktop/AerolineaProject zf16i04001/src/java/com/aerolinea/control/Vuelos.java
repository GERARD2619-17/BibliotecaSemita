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

@WebServlet(name = "Vuelos", urlPatterns = {"/Vuelos"})
public class Vuelos extends HttpServlet {
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
                String sql = "select idvuelo, FORMAT(fecha, 'dd/MM/yyyy HH:mm', 'en-US') as date, \n" +
                "ori.aeropuerto as origen,\n" +
                "dest.aeropuerto as destino,\n" +
                "av.descripcion as avion,\n" +
                "av.capacidad as capacidad,\n" +
                "vuelo.estado as estado\n" +
                "from vuelo\n" +
                "inner join aeropuerto as ori on ori.idaeropuerto = vuelo.idorigen\n" +
                "inner join aeropuerto as dest on dest.idaeropuerto = vuelo.iddestino\n" +
                "inner join avion as av on av.idavion = vuelo.idavion";
                String[][] vuelos=null;
                if (request.getParameter("idorigen")!=null){
                    List<Object> param = new ArrayList();
                    param.add(request.getParameter("idorigen"));
                    request.setAttribute("idorigen", request.getParameter("idorigen"));
                    request.setAttribute("origen", request.getParameter("origen"));
                    sql+=" where vuelo.idorigen = ?";
                    vuelos= Operaciones.consultar(sql, param);
                }
//                else{
//                    vuelos= Operaciones.consultar(sql, new ArrayList());                    
//                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Vuelo",
                    "Fecha y Hora",
                    "Origen",
                    "Destino",
                    "Avión",
                    "Capacidad",
                    "Estado"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(vuelos, //array que contiene los datos
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
                tab.setPaginaEliminable("/Vuelos?accion=eliminar");
                //pagina encargada de seleccion para operaciones
                //tab.setPaginaSeleccionable("/Paises");
                //icono para modificar y eliminar
                //tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
                //tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Vuelos");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (vuelos!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("vuelos/vuelos_consulta.jsp").forward(request, response);
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
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
        } else if(accion.equals("listado_origen")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "select aero.idaeropuerto, aero.aeropuerto,\n" +
                "pa.pais, aero.ciudad\n" +
                "from aeropuerto as aero\n" +
                "inner join pais as pa on pa.idpais = aero.idpais";
                String[][] origenes = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "Id Aeropuerto",
                    "Aeropuerto",
                    "Pais",
                    "Ciudad"
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
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado Aeropuertos");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (origenes!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("vuelos/aeropuertos_origen.jsp").forward(request,
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
            response.sendRedirect(request.getContextPath()+"/Vuelos");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String idorigen = request.getParameter("txtIdorigen");
        String origen = request.getParameter("txtOrigen");
        String fecha = request.getParameter("txtFecha");
        String idavion = request.getParameter("txtIdavion");
        String iddestino = request.getParameter("txtIddestino");
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            Vuelo vuelo = new Vuelo();
            vuelo.setIdavion(Integer.parseInt(idavion));
            vuelo.setIdorigen(Integer.parseInt(idorigen));
            vuelo.setIddestino(Integer.parseInt(iddestino));
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
        response.sendRedirect(request.getContextPath()+"/Vuelos?idorigen="+idorigen+"&origen="+origen);
    }
}