<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <title> Inicio de Sesion </title>
    <link rel="stylesheet" href="style.css">   
</head>
    <body>
    <div class="login-box">
    <img src="imagenes/avatar1.png" class="avatar">
        <h1>Inicia Sesion</h1>
        <c:if test="${error!=null}">
                <c:if test="${error==2}">
                    <p><strong style="color: red">Usuario y/o contraseña incorrectos</strong></p>
                </c:if>
            </c:if>
            <form name="main" action="Login?accion=login" method="POST">
            <p>Nombre de Usuario</p>
            <input type="text" name="txtUsuario" placeholder="nombre de usuario">
            <p>Contraseña</p>
            <input type="password" name="txtClave" placeholder="contraseña">
            <input type="submit" name="btnEntrar" value="Entrar">
               
            </form>
        
        
        </div>
    
    </body>
</html>