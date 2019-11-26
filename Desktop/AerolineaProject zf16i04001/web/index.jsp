<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Aerolinea Project</title>
        <link rel="stylesheet" type="text/css" href="css/reset.css"/>
        <link rel="stylesheet" type="text/css" href="css/main.css"/>
        <style>
            .login{margin: 0 auto;}
            .login td{padding:  5px 0px;}
            #header >h1{margin-left: 0px; padding-top: 110px;}
            #content >h1{text-align: center;}
        </style>
    </head>
    <body>
        <div id="header">
            <h1>Aerolinea DSi</h1>
        </div>
        <div id="content">
            <hr/>
            <h1>Inicio de sesión</h1>
            <center>
                <c:if test="${error != null}">
                    <c:if test="${error == 2}">
                        <p><strong style="color: red">Usuario y/o contraseña incorrectos</strong></p>
                    </c:if>
                </c:if>
            </center>
            <form name="main" action="Login?accion=login" method="POST">
                <table>
                    <tr><td>Usuario</td></tr>
                    <tr><td><input type="text" name="txtUsuario" size="30px" autofocus="autofocus"/></td></tr>
                    <tr><td>Contraseña</td></tr>
                    <tr><td><input type="password" name="txtClave" size="30px"/></td></tr>
                    <tr>
                        <td>
                            <div class="buttons">
                                <ul>
                                    <li><input type="submit" value="Entrar" name="btnEntrar"/></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
            <br/><br/><br/><br/>
            <hr/>
        </div>
    </body>
