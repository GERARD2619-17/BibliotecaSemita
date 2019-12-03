<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<script>
    function abrirVentana(URL) {
//funcion javascript para abrir un subventana para realizar
//busquedas, se le pasa la pagina a mostrar como parametro
        window.open(URL, "ventana1", "width=1000,height=400,scrollbars=YES,statusbar=YES,top=150,left=300")
    }
    
    
</script>
<h1>Registro de Prestamos</h1><br><br>
<form name="main" method="post" action="${pageContext.servletContext.contextPath}/RegPrestamos">
    <!--<input type="hidden" name="sw_nuevo" value="1"/>-->
    <br>
    Seleccione Libro Origen
    <input type="text" id="txtIdlibro" name="txtIdlibro" value="${idlibro}" size="3" readonly="readonly">
    <input type="text" placeholder="Selecciones un Libro" required id="txtTitulo" name="txtTitulo" size="50" value="${titulo}" readonly="readonly">
    <input type="button" class="boton" value="..." onclick="abrirVentana('${pageContext.servletContext.contextPath}/RegPrestamos?accion=listado_origen');">
    <br><br><br><br>
    <hr>
    <br>
    <table border="0" id="table" style="width: 50%">
    <thead>
        <tr>
            <th colspan="">Complete la información<br><br></th>
        </tr>
    </thead>
    <tbody>
    <tr>
        <td>Id Prestamo:</td>
        <td><input type="text" name="txtIdprestamo" id="txtIdprestamo" size="2" readonly="readonly"></td>
    </tr>
    
    <tr>
        <td>Estado :</td>
        <td><input type="text" name="txtIdestado" id="txtIdestado" size="2" readonly="readonly">
            <input type="text" placeholder="Revisar estado" required name="txtEstado" id="txtEstado" readonly="readonly" required>
    <input type="button" value="..." class="boton" onclick="abrirVentana('${pageContext.servletContext.contextPath}/RegPrestamos?accion=listado_estados');">
    
        </td>
    </tr>
    <tr>
        <td>Fecha de Devolucion:</td>
        <td><input class="datepicker" type="text" name="txtFechaD" id="fecha" size="25"></td>
    </tr>
    <tr>
        <td> </td>
    <td><input type="submit" align="center" value="Agregar" class="boton"></td>
    </tr>
    </tbody>
    </table>
</form><br><br>
<c:if test="${resultado!=null}">
    <c:if test="${resultado==1}">
        <p style="color:darkgreen; font-size: 15px; text-align: center"><strong>Operación realizada correctamente.</strong></p>
    </c:if>
    <c:if test="${resultado==0}">
        <p style="color:darkred; font-size: 15px; text-align: center"><strong>La operación no se realizó.</strong></p>
    </c:if>
</c:if>
${tabla}
<script>
    window.onload = function () {
//inicializamos el control de fecha
        var dtp = new DateTimePicker('.datepicker', {
            timePicker: true, // activamos la selección de hora
            format: 'd/m/Y H:i', //formato de fecha y hor
       
            defaultDate: "+W",
            changeMount: true,
            minDate: 0
        });
    };
//funcion que se llamará al seleccionar el origen desde la ventana
    function setDataLibros(idlibro, titulo) {
        document.getElementById("txtIdlibro").value = idlibro;
        document.getElementById("txtTitulo").value = titulo;
    }
    function setDataEstado(idestado, estado) {
        document.getElementById("txtIdestado").value = idestado;
        document.getElementById("txtEstado").value = estado;
    }
</script>
<%@include file="../_down.jsp"%>