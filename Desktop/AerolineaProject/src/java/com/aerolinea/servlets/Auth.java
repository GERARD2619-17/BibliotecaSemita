package com.aerolinea.servlets;

import com.aerolinea.conexion.ConexionPool;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.entidad.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Auth extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion==null){
            request.getRequestDispatcher("index.jsp").forward(request, response);            
            request.removeAttribute("estado");
        }else{
            switch(accion){
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion==null){
            response.sendRedirect("/AerolineaProject");
            request.removeAttribute("estado");
        }else{
            switch(accion){
                case "auth":
                    autenticarUsuario(request, response);
                    request.removeAttribute("estado");
                break;
            }
        }
    }

    public void autenticarUsuario(HttpServletRequest request, HttpServletResponse response){
        ConexionPool con = new ConexionPool();
        con.conectar();
        try{
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            //Autenticación de usuario
            String usuario = request.getParameter("txtUsuario");
            String password = request.getParameter("txtPass");
            Usuario u = new Usuario();
            u = Operaciones.get(usuario, new Usuario());
            Rol r = new Rol();
            r = Operaciones.get(u.getIdrol(), new Rol());
            
            if(usuario==null && password == null){
                request.removeAttribute("estado");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(usuario.equals(u.getIdusuario()) && !password.equals(u.getClave())){
                request.setAttribute("estado", "Contraseña no válida");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                request.removeAttribute("estado");
            }else if(password.equals(u.getClave()) && usuario.equals(u.getIdusuario())){
                request.setAttribute("usuario", (u.getNombres() + " " + u.getApellidos()));
                if(r.getRol().equals("admin")){
                    request.getRequestDispatcher("Pagina1.jsp").forward(request, response);        
                }else if(r.getRol().equals("usuario")){                    
                    request.getRequestDispatcher("Pagina2.jsp").forward(request, response); 
                }
                request.removeAttribute("estado");            
            }else if(!usuario.equals(u.getIdusuario())){
                request.setAttribute("estado", "Usuario no válido");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                request.removeAttribute("estado");
            }

                

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
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
