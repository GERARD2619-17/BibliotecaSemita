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
        <title>Destinos</title>
    </head>
    <body>
        <div id="contenido" style="padding: 10px">
            <h1>Aeropuertos Destino</h1>
            ${tabla}
            <script>
            //funcion javascript que se ejecuta al hacer click en una fila
            //recibe un elemento de tipo fila como parametro: row
                function _Seleccionar_(row){
                    //recupera el idorigen de la fila, en la celda 0
                    var iddestino = row.cells[0].innerHTML;
                    //recupera nombre del origen de la fila, en la celda 1
                    var destino = row.cells[1].childNodes[0].innerHTML;
                    // se manda a llamar la función desde la ventana padre que llamó esta ventana
                    window.opener.setDataDestino(iddestino, destino);
                    //cierra la ventana
                    window.close();
                }
            </script>
        </div>
    </body>
</html>