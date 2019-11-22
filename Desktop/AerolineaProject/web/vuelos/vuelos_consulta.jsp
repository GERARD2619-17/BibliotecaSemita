<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
    <script>
    function abrirVentana (URL){
    //funcion javascript para abrir un subventana para realizar
    //busquedas, se le pasa la pagina a mostrar como parametro
    window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300")
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
    <h1>Registro de Vuelos</h1><br><br>
    <form name="main" method="post"
    action="${pageContext.servletContext.contextPath}/Vuelos">
    <!--<input type="hidden" name="sw_nuevo" value="1"/>-->
    <br>
    Seleccione Aeropuerto Origen
    <input type="text" id="txtIdorigen" name="txtIdorigen" value="${idorigen}" size="3"
    readonly="readonly">
    <input type="text" id="txtOrigen" name="txtOrigen" size="50" value="${origen}"
    readonly="readonly">
    <input type="button" class="boton" value="..."
    onclick="abrirVentana('${pageContext.servletContext.contextPath}/Vuelos?accion=listado_origen');">
    <a href="${pageContext.servletContext.contextPath}/Reportes?idorigen=${idorigen}">Reporte</a>
    <br><br><br><br>
    <hr>
    <br>
    Fecha y Hora:
    <input class="datepicker" type="text" name="txtFecha" size="25">
    Avion:
    <input type="text" name="txtIdavion" id="txtIdavion" size="2" readonly="readonly">
    <input type="text" name="txtAvion" id="txtAvion" readonly="readonly">
    <input type="button" value="..." class="boton"
    onclick="abrirVentana('${pageContext.servletContext.contextPath}/Vuelos?accion=listado_avion');">
    Destino :
    <input type="text" name="txtIddestino" id="txtIddestino" size="2" readonly="readonly">
    <input type="text" name="txtDestino" id="txtDestino" readonly="readonly">
    <input type="button" value="..." class="boton"
    onclick="abrirVentana('${pageContext.servletContext.contextPath}/Vuelos?accion=listado_destino');"
    >
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
        function setDataOrigen(idorigen, origen) {
        document.getElementById("txtIdorigen").value = idorigen;
        document.getElementById("txtOrigen").value = origen;
        }
        //funcion que se llamará al seleccionar el destino desde la ventana
        function setDataDestino(iddestino, destino) {
        document.getElementById("txtIddestino").value = iddestino;
        document.getElementById("txtDestino").value = destino;
        }
        //funcion que se llamará al seleccionar el avion desde la ventana
        function setDataAvion(idavion, avion) {
        document.getElementById("txtIdavion").value = idavion;
        document.getElementById("txtAvion").value = avion;
        }
    </script>
<%@include file="../_down.jsp"%>