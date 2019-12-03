<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Base de datos de biblioteca!</h1>
        
        
           <%
            try{
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/biblioteca";
                Connection conexion = DriverManager.getConnection(url, "root", "admin");
                if(conexion==null)
                    out.println("Falla en la conexion");
                else
                    out.println("Conexion exitosa <br>");     
                //Consulta los datos
                conexion.close();
            }catch(Exception e){
                out.println("Error en la conexion"+ e.getMessage());
            }
        %>
    </body>
</html>
