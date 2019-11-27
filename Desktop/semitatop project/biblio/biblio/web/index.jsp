<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="es">
<head>
    <title>Inicio de sesión</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="Shortcut Icon" type="image/x-icon" href="assets/icons/book.ico" />
    <script src="js/sweet-alert.min.js"></script>
    <link rel="stylesheet" href="css/sweet-alert.css">
    <link rel="stylesheet" href="css/material-design-iconic-font.min.css">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/jquery.mCustomScrollbar.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/login.css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/jquery-1.11.2.min.js"><\/script>')</script>
    <script src="js/modernizr.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="js/main.js"></script>
</head>
<body class="full-cover-background" style="background-image:url(assets/img/font-loginn.jpg);">
    <div class="form-container">
        <p class="text-center" style="margin-top: 17px;">
         
          <i class="zmdi zmdi-accounts-alt zmdi-hc-5x"></i>
           
       </p>
       <h4 class="text-center all-tittles" style="margin-bottom: 30px;">inicia sesión con tu cuenta</h4>
            <c:if test="${error!=null}">
                <c:if test="${error==2}">
                    <p><strong style="color: red">Usuario y/o contraseña incorrectos</strong></p>
                </c:if>
            </c:if>
       <form name="main" action="Login?accion=login" method="POST">
            <div class="group-material-login">
              <input type="text" name="txtUsuario" class="material-login-control" required="" maxlength="70">
              <span class="highlight-login"></span>
              <span class="bar-login"></span>
              <label><i class="zmdi zmdi-account"></i> &nbsp; Nombres</label>
            </div><br>
            <div class="group-material-login">
              <input type="password" name="txtClave" class="material-login-control" required="" maxlength="70">
              <span class="highlight-login"></span>
              <span class="bar-login"></span>
              <label><i class="zmdi zmdi-lock"></i> &nbsp; Contraseña</label>
              <br>
              
            </div>
            <button class="btn-Crear" type="submit" name="btnSpace" value="Space">Crear Usuario &nbsp; <i class="zmdi zmdi-arrow-right"></i></button> <%--esta es la parte de prueba para crear usuarios--%>
            <button class="btn-login" type="submit" name="btnEntrar" value="Entrar">Ingresar al sistema &nbsp; <i class="zmdi zmdi-arrow-right"></i></button>
            
            
        </form>
    </div>  
</body>
</html>