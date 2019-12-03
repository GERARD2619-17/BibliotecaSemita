<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<h1>Editoriales</h1>
<br/>
<form name="form_editoriales" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Editorial?accion=insertar_modificar" method="POST">
    <table border="0" id="table" style="width: 50%">
    <thead>
        <tr>
            <th colspan="">Complete la información<br><br></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>ID Editorial</td>
            <td><input type="text" name="txtIdeditorial" value="${editorial.ideditorial}" readonly="readonly" /></td>
        </tr>
        <tr>
            <td>Nombre Editorial</td>
            <td><input type="text" name="txtEditorial" id="txtEditorial" value="${editorial.editorial}" /></td>
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
	var editorial = document.getElementById('txtEditorial');		
	if (editorial.value.length==0){
                editorial.focus();			
                alert("Digite nombre del editorial");
            return false;
	}			
	
	return true;
    }                
</script>
<%@include file="../_down.jsp"%>

