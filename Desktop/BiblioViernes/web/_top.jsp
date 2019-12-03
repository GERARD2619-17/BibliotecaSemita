<%@page import="com.biblioteca.entidades.Menu"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<% HttpSession sesion = request.getSession(); 
     if(sesion.getAttribute("nombre")==null || request.getSession() == null){
        response.sendRedirect("index.jsp");
    }
        response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Biblioteca Semitatop</title>
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />        
        <link href="css/tabla.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="css/datetimepicker.css"/> 
        <script type="text/javascript" src="js/datetimepicker.js"></script>
    </head>
    
    <body background="lib.png" style="background-repeat: no-repeat; background-position: center;">
       
        <div class="ancho">
        <div id="header">
            <br>
            <img src="imagenes/logo1.jpg" />
        
        <div id="sesion">            
            <h2>Usuario: <%= session.getAttribute("nombre") %>
                <strong>[<%= session.getAttribute("usuario") %>]</strong>  
                <a href="Principal?accion=logout">Cerrar Sesión</a>
            </h2>            
        </div>        
        <div id="menu">
            <ul>
            <c:forEach var="menu" items="${MenuPrincipal}">
                <li><a href="${pageContext.servletContext.contextPath}${menu.url}?op=${menu.idmenu}">
                        ${menu.menu}</a></li>    
            </c:forEach>
            
            </ul>
        </div>
            </div>
            </div>

<div id="content">
        <div id="particles-js"></div>
        
        <script src="particles.js"></script>
</body>