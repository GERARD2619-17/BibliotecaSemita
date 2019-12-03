<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<h1>Autores</h1>
<br/>
<form name="form_autores" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Autor?accion=insertar_modificar" method="POST">
    <table border="0" id="table" style="width: 50%">
    <thead>
        <tr>
            <th colspan="">Complete la información<br><br></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>ID Autor</td>
            <td><input type="text" name="txtIdautor" value="${autor.idautor}" readonly="readonly" /></td>
        </tr>
        <tr>
            <td>Nombre Autor</td>
            <td><input type="text" name="txtAutor" id="txtAutor" value="${autor.autor}" /></td>
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
	var autor = document.getElementById('txtAutor');		
	if (autor.value.length==0){
                autor.focus();			
                alert("Digite nombre del autor");
            return false;
	}			
	
	return true;
    }                
</script>
<%@include file="../_down.jsp"%>

