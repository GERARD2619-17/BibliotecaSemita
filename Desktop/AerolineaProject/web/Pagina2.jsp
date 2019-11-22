<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuario</title>
    </head>
    <body>
        <h1>Página de usuario</h1>
        <h2 style="color:dodgerblue">Usuario: ${usuario}</h2>
        <a href="/AerolineaProject/Auth?estado=null">Volver a principal</a>
    </body>
</html>