<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<h1>Clasificaciones</h1>
<br/>
<form name="form_clasificaciones" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Clasificacion?accion=insertar_modificar" method="POST">
    <table border="0" id="table" style="width: 50%">
    <thead>
        <tr>
            <th colspan="">Complete la información<br><br></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>ID Clasificacion</td>
            <td><input type="text" name="txtIdclasificacion" value="${clasificacion.idclasificacion}" readonly="readonly"/></td>
        </tr>
        <tr>
            <td>Clasificacion</td>
            <td><input type="text" name="txtClasificacion" id="txtclasificacion" value="${clasificacion.clasificacion}" /></td>
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
	var clasificacion = document.getElementById('txtclasificacion');		
	if (clasificacion.value.length==0){
                clasificacion.focus();			
                alert("Digite nombre de la clasificacion");
            return false;
	}			
	
	return true;
    }                
</script>
<%@include file="../_down.jsp"%>

