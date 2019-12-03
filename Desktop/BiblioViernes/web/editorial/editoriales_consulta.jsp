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
<h1>Listado Editoriales</h1><br>  
<div class="busqueda" style="width: 50%; text-align: right">
    <form action="${pageContext.servletContext.contextPath}/Editorial" method="get">
        <input type="text" name="txtBusqueda" id="txtBusqueda"
               value="${valor}" />
        <input type="submit" value="Buscar"/>
    </form>
</div>             
<div class="buttons" >
    <ul>        
        <li><a href="${pageContext.servletContext.contextPath}/Editorial?accion=insertar">Nuevo</a></li>
        <a href="${pageContext.servletContext.contextPath}/Reportes?idautor=${ideditorial}">Reporte</a>
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

