<head>
    <title>Docentes</title>
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
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/jquery-1.11.2.min.js"><\/script>')</script>
    <script src="js/modernizr.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="js/main.js"></script>
</head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<c:if test="${resultado!=null}">
    <c:if test="${resultado==1}">
        <p style="color:darkgreen"><strong>Operación realizada correctamente.</strong></p>
    </c:if>
    <c:if test="${resultado==0}">
        <p style="color:darkred"><strong>La operación no se realizó.</strong></p>
    </c:if>
</c:if>
<h1>Listado Libros</h1><br>  
<div class="busqueda" style="width: 50%; margin-left: 25%; text-align: right">
    <form action="${pageContext.servletContext.contextPath}/Libros" method="get">
        <input type="text" name="txtBusqueda" id="txtBusqueda"
               value="${valor}" />
        <input type="submit" value="Buscar"/>
    </form>
</div>             
<div class="buttons" >
    <ul>        
        <li><a href="${pageContext.servletContext.contextPath}/Libros?accion=insertar">Nuevo</a></li>
    </ul>
</div>            
<br/>
${tabla}
<script>            
    window.onload = function() {
        document.getElementById("txtBusqueda").focus();
    };
</script>    

<%@include file="../_down.jsp"%>



