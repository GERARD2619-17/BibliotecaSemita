<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
    <script>
    function abrirVentana (URL){
    //funcion javascript para abrir un subventana para realizar
    //busquedas, se le pasa la pagina a mostrar como parametro
    window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    </script>
    <c:if test="${resultado!=null}">
        <c:if test="${resultado == 1}">
            <p style="color:green">Operación realizada con éxito</p>
        </c:if>
        <c:if test="${resultado == 0}">
            <p style="color:red">La operación no se pudo realizar</p>
        </c:if>
    </c:if>
    <h1>Reservación de vuelos</h1><br><br>
    <form name="main" method="post"
    action="${pageContext.servletContext.contextPath}/Reservaciones">
    <!--<input type="hidden" name="sw_nuevo" value="1"/>-->
    <br>
    Seleccione el vuelo
    <br>
    <table>
        <tr><th>ID Vuelo</th><th>Origen</th><th>Destino</th><th>Fecha de vuelo</th><th></th></tr>
        <br>
        <tr>
            <td><input type="text" id="txtIdvuelo" name="txtIdvuelo" value="${idvuelo}" size="3" readonly="readonly"></td>
            <td><input type="text" id="txtOrigen" name="txtOrigen" value="${origen}" readonly="readonly"></td>
            <td><input type="text" id="txtDestino" name="txtDestino" value="${destino}" readonly="readonly"></td>
            <td><input type="text" id="txtFecha" name="txtFecha" value="${fecha}" readonly="readonly"></td>
            <td><input type="button" class="boton" value="..." onclick="abrirVentana('${pageContext.servletContext.contextPath}/Reservaciones?accion=listado_vuelos');"></td>
        </tr>
    </table>    
    <br><br>
    Número de boletos:
    <input type="text" name="txtFecha" size="25">
    Precio:
    <input type="text" name="txtFecha" size="25">
    Fecha-Hora:
    <input class="datepicker" type="text" name="txtFecha" size="25" readonly="readonly" disabled="">
    <input type="submit" value="Agregar" class="boton">
    </form><br><br>
    ${tabla}
    <script>
        window.onload = function() {
        //inicializamos el control de fecha
            var dtp = new DateTimePicker('.datepicker', {
            timePicker: true, // activamos la selección de hora
            format: 'd/m/Y H:i' //formato de fecha y hora
            });
        };
        //funcion que se llamará al seleccionar el origen desde la ventana
        function setDataReservacion(idvuelo, origen, destino, fecha) {
            document.getElementById("txtIdvuelo").value = idvuelo;
            document.getElementById("txtOrigen").value = origen;
            document.getElementById("txtDestino").value = destino;
            document.getElementById("txtFecha").value = fecha;
        }
    </script>
<%@include file="../_down.jsp"%>