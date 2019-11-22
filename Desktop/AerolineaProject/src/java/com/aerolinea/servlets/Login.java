package com.aerolinea.servlets;

import com.aerolinea.utilerias.*;
import com.aerolinea.operaciones.Operaciones;
import com.aerolinea.conexion.*;
import com.aerolinea.entidad.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Cache-control", "no-store");
        response.setHeader("Cache-control", "must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        String accion = request.getParameter("accion");
        if(accion == null){
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else if(accion.equals("login")){
            iniciarSesion(request, response);
        }
    }
    
    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
        String usuario = request.getParameter("txtUsuario");
        String clave = request.getParameter("txtClave");
        PrintWriter io = response.getWriter();
        if(usuario == null){
            usuario = "";
        }
        if(clave == null){
            clave = "";
        }        
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            if(conn.getConexion() == null){
                request.setAttribute("error", 1);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }else{
                HttpSession sesion = request.getSession();
                Usuario u = Operaciones.get(usuario, new Usuario());
                if(u.getIdusuario() != null){
                    if(u.getClave().equals(Hash.generarHash(clave, Hash.SHA256))){
                        sesion.setAttribute("Usuario", u.getIdusuario());
                        sesion.setAttribute("Nombre", u.getNombres() + " " + u.getApellidos());
                        sesion.setAttribute("Rol", u.getIdrol());
                        List<Menu> permisos = getPermisos(u.getIdrol());
                        
                        List<Menu> MenuPrincipal = permisos.stream().filter(field -> field.getIdpadre()==0).collect(Collectors.toList());
                        sesion.setAttribute("MenuPrincipal", MenuPrincipal);
                        
                        sesion.setAttribute("Permisos", permisos);
                        response.sendRedirect("Principal");                        
                    }else{
                        request.setAttribute("error", 2);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }else{
                    request.setAttribute("error", 2);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
            
            Operaciones.commit();
        }catch(Exception ex){
            Operaciones.rollback();
            io.print(ex);
        }finally{
            try{
                Operaciones.cerrarConexion();
            }catch(SQLException ex){
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private List<Menu> getPermisos(Integer idrol) throws SQLException{
        List<Menu> permisos = new ArrayList();
        try{
            String sql = "select * from menu where idmenu in (select idmenu from permiso where idrol = ?)";
            List<Object> param = new ArrayList();
            param.add(idrol);
            String[][] rs = Operaciones.consultar(sql, param);
            for(int i=0; i<rs[0].length; i++){
                Menu m = new Menu(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], rs[3][i], Integer.parseInt(rs[4][i]==null?"0":rs[4][i]));
                permisos.add(m);
            }
        }catch(Exception ex){
            Operaciones.rollback();
        }
        return permisos;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
