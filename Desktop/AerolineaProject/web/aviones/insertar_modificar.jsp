<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
    <h1>Paises</h1>
    <br/>
    <form name="form_aviones" onsubmit="return validar();"
    action="${pageContext.servletContext.contextPath}/Aviones?accion=insertar_modificar"
    method="POST">
        <table border="0" id="table">
            <thead>
                <tr>
                <th colspan="">Complete la información<br><br></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <td>ID Avión</td>
                <td><input type="text" name="txtIdavion" value="${avion.idavion}" readonly="readonly" /></td>
                </tr>
                <tr>
                <td>Capacidad</td>
                <td><input type="text" name="txtCapacidad" id="txtAeropuerto" value="${avion.capacidad}" required/></td>
                </tr>
                <tr>
                <td>Descripción</td>
                <td><input type="text" name="txtDescripcion" id="txtAeropuerto" value="${avion.descripcion}" required/></td>
                </tr>
            </tbody>
        </table>
        <br/>
        <div class="buttons">
            <ul>
                <li><input type="submit" value="Guardar" name="guardar"/></li>
                <li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li>
            </ul>   
        </div>
    </form>
<%@include file="../_down.jsp"%>
