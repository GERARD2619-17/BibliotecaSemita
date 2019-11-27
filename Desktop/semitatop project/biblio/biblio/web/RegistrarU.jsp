
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title> Registro de Usuarios </title>
    <link rel="stylesheet" type="text/css" href="style.css">   
</head>
    <body>
    <div class="login-box">
    <img src="imagenes/avatar.png" class="avatar">
        <h1>Registrate</h1>
            <form>
            <p>Nombre</p>
            <input type="text" name="username" placeholder="nombre">
            <p>Apellido</p>
            <input type="text" name="username" placeholder="apellidos">
            <p>E-mail</p>
            <input type="text" name="username" placeholder="e-mail">
            <p>Nombre de Usuario</p>
            <input type="text" name="username" placeholder="nombre de usuario">
            <p>Contraseña</p>
            <input type="password" name="password" placeholder="contraseña">
            <input type="submit" name="submit" value="Registrar">
            </form>
        <%-- <button class="btn-login" type="submit" name="btnSpace" value="Space">Crear Usuario &nbsp; <i class="zmdi zmdi-arrow-right"></i></button> <%--esta es la parte de prueba para crear usuarios--%>
        
        </div>
    
    </body>
</html>