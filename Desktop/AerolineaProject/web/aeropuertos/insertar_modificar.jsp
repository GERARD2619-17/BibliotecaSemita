<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
    <h1>Paises</h1>
    <br/>
    <form name="form_aeropuertos" onsubmit="return validar();"
    action="${pageContext.servletContext.contextPath}/Aeropuertos?accion=insertar_modificar"
    method="POST">
        <table border="0" id="table">
            <thead>
                <tr>
                <th colspan="">Complete la información<br><br></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <td>ID Aeropuerto</td>
                <td><input type="text" name="txtIdaeropuerto" value="${aeropuerto.idaeropuerto}" readonly="readonly" /></td>
                </tr>
                <tr>
                <td>Nombre Aeropuerto</td>
                <td><input type="text" name="txtAeropuerto" id="txtAeropuerto" value="${aeropuerto.aeropuerto}" /></td>
                </tr>
                <tr>
                <td>País</td>
                <td>
                    <select name="cmbPais">
                        <c:forEach var="pais" items="${Paises}">
                            <option value="${pais.idpais}">${pais.pais}</option>
                        </c:forEach>
                    </select>
                </td>
                </tr>
                <tr>
                <td>Ciudad</td>
                <td><input type="text" name="txtCiudad" id="txtAeropuerto" value="${aeropuerto.ciudad}" /></td>
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
    <script>
        function validar(){
            var pais = document.getElementById('txtPais');
            if (pais.value.length==0){
                pais.focus();
                alert("Digite nombre del aeropuerto");
                return false;
            }
            return true;
        }
    </script>
<%@include file="../_down.jsp"%>
