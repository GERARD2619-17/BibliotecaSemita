<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="css/tabla.css" media="screen" />
        <style>
            #table01 td{ padding-top: 8px; cursor: pointer}
        </style>
        <title>Origenes</title>
    </head>
    <body>
        <div id="contenido" style="padding: 10px">
            <h1>Aeropuertos Origen</h1>
            ${tabla}
            <script>
                //funcion javascript que se ejecuta al hacer click en una fila
                //recibe un elemento de tipo fila como parametro: row
                function _Seleccionar_(row){
                    //recupera el idorigen de la fila, en la celda 0
                    var idorigen = row.cells[0].innerHTML;
                    //recupera nombre del origen de la fila, en la celda 1
                    var origen = row.cells[1].childNodes[0].innerHTML;
                    //redirecciona hacia vuelos.jsp con los valores obtenidos
                    //de idorigen y origen
                    window.opener.location.href="${pageContext.servletContext.contextPath}/Vuelos?idorigen="+idorigen+"&origen="+origen;
                    //cierra la ventana
                    window.close();
                    return false;
                }
            </script>
        </div>
    </body>
</html>