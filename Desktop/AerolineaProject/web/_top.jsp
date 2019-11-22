<%@page import="com.aerolinea.entidad.Menu"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<% 
    HttpSession sesion = request.getSession(); 
    List<Menu> MenuPrincipal = (List<Menu>)sesion.getAttribute("MenuPrincipal");
    
    if(sesion.getAttribute("Usuario") == null || request.getSession() == null){
        response.sendRedirect("Login");
    }
    response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aerolinea Project</title>
        <link href="css/tabla.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />
        <link href="css/datetimepicker.css" rel="stylesheet" type="text/css"/>
        <script src="js/datetimepicker.js"></script>
    </head>
    <body>
        <div id="header">
            <h1>Aerolínea DSi</h1><br/>
            <h2>A Example Project for Learning</h2>
            <img src="iconos/logo.png" />
        </div>
        <div id="sesion">
            <h2>Usuario: <%= sesion.getAttribute("Nombre") %>
            <strong>[<%= sesion.getAttribute("Usuario") %>]</strong> |
            <a href="Principal?accion=logout">Cerrar Sesión</a>
            </h2>
        </div>
            <h1></h1>
        <div id="menu">
            <ul>
            <c:forEach var="menu" items="<%=MenuPrincipal%>">
                <li><a
                href="${pageContext.servletContext.contextPath}${menu.url}">${menu.menu}</a></li>
            </c:forEach>
            </ul>
        </div>
        <div id="content">