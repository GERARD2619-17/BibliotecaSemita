package com.aerolinea.servlets;

import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.entidad.*;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.utilerias.Hash;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Seed extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch(accion){
            case "aeropuerto": insertarAeropuerto();
                break;
            case "avion": insertarAvion();
                break;
            case "pais": insertarPais();
                break;
            case "reservacion": insertarReservacion();
                break;
            case "rol": insertarRol();
                break;
            case "usuario": insertarUsuario();
                break;
            case "vuelo": insertarVuelo();
                break;
        }
    }
    
    public void insertarAeropuerto(){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //OPERACIONES
            

            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insertarAvion(){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //OPERACIONES
            Avion av = new Avion();
            av.setCapacidad(330);
            av.setDescripcion("Python GQX-3000");
            av = Operaciones.insertar(av);

            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insertarPais(){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //OPERACIONES
            Pais p = new Pais();
            p.setPais("El Salvador");
            p = Operaciones.insertar(p);
            

            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insertarReservacion(){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //OPERACIONES
            

            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insertarRol(){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //OPERACIONES
            Rol rol = new Rol();
            rol.setRol("admin");
            rol = Operaciones.insertar(rol);

            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insertarUsuario(){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //OPERACIONES
            /*Usuario u = new Usuario();
            u.setIdusuario("mario.rivera");
            u.setNombres("Mario");
            u.setApellidos("Rivera");
            u.setEmail("mario@gmail.com");
            u.setTelefono("7777-7777");
            u.setClave(Hash.generarHash("admin", Hash.SHA256));
            u.setIdpais(1);
            u.setIdrol(1);
            u = Operaciones.insertar(u);*/
            
            Usuario u2 = new Usuario();
            u2.setIdusuario("alejandra.gutierrez");
            u2.setNombres("Alejandra Gabriela");
            u2.setApellidos("Gutiérrez Panameño");
            u2.setEmail("alejandra@gmail.com");
            u2.setTelefono("7777-7778");
            u2.setClave(Hash.generarHash("alejandra", Hash.SHA256));
            u2.setIdpais(1);
            u2.setIdrol(2);
            u2 = Operaciones.insertar(u2);

            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insertarVuelo(){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //OPERACIONES
            

            Operaciones.commit();
        }catch(Exception e){
            try {
                Operaciones.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Seed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
