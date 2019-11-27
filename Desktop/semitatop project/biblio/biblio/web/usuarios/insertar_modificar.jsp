<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<script>
    function abrirVentana(URL) {
//funcion javascript para abrir un subventana para realizar
//busquedas, se le pasa la pagina a mostrar como parametro
        window.open(URL, "ventana1", "width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300")
    }
    
   function test() {
    var p=document.getElementById("txtClave");
    var c=document.getElementById("chec");
    p.type=(c.checked) ? "text" : "password";
}
    
    function validateMail(idMail){
	//Creamos un objeto 
	object=document.getElementById(idMail);
	valueForm=object.value;
	// Patron para el correo
	var patron=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
	if(valueForm.search(patron)==0)	{
		//Mail correcto
		object.style.color="#000";
		return;
	}
	//Mail incorrecto
	object.style.color="#f00";
}

function soloNumeros(e){
	var key = window.Event ? e.which : e.keyCode
	return (key >= 48 && key <= 57)
}

</script>
<h1>Usuarios</h1>
<br/>
<form name="form_usuarios" onsubmit="return validar();" action="${pageContext.servletContext.contextPath}/Usuarios?accion=insertar_modificar" method="POST">
    <table border="0" id="table" style="width: 50%">
    <thead>
        <tr>
            <th colspan="">Complete la información<br><br></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>ID </td>
            <td><input type="text" name="txtIdusu" value="${usuario.idusuario}" minlength="5" maxlength="40" size="30" placeholder="Escribe tu nombre de usuario" required autofocus /></td>
        </tr>
        <tr>
            <td>Nombres </td>
            <td><input type="text" name="txtNombre" id="txtNombre" value="${usuario.nombres}" minlength="5" maxlength="40" size="30" placeholder="Escribe tu nombre completo" required autofocus/></td>
        </tr>    
        <tr>
            <td>Apellidos </td>
            <td><input type="text" minlength="5" maxlength="40" size="30" placeholder="Escribe tu apellido" required autofocus name="txtApellido" id="txtApellido" value="${usuario.apellidos}" /></td>
        </tr>  
        <tr>
            <td>Email </td>
            <td><input type="text" maxlength="40" size="30" placeholder="Escribe tu correo" required name="txtEmail" id="txtEmail" value="${usuario.email}" onKeyUp="javascript:validateMail('txtEmail')" title="Ingrese una direccion de correo valida" /></td>
        </tr>  
        <tr>
            <td>Telefono </td>
            <td><input type="text" maxlength="8" size="30" placeholder="Ej: 77774444" required name="txtTelefono" id="txtTelefono" value="${usuario.telefono}" onKeyPress="return soloNumeros(event)" /></td>
        </tr> 
        <tr>
            <td>Clave </td>
            <td><input type="password" minlength="5" maxlength="20" size="30" placeholder="Escribe tu clave" required title="Tamaño mínimo: 5. Tamaño máximo: 20" name="txtClave" id="txtClave" value="${usuario.clave}" />
                <label>
                <input type="checkbox" id="chec" name="chec" onclick="test()">Mostrar Contraseña
                </label>
            </td>
        </tr> 
        <tr>
        <td>Rol :</td>
        <td><input type="text" name="txtIdrol" id="txtIdrol" size="2" readonly="readonly">
    <input type="text" name="txtRol" id="txtRol" readonly="readonly" placeholder="Elija un Rol" required>
    <input type="button" value="..." class="boton" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Usuarios?accion=listado_roles');">
    
        </td>
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
    
//funcion que se llamará al seleccionar el origen desde la ventana
    function setDataRol(idrol, rol) {
        document.getElementById("txtIdrol").value = idrol;
        document.getElementById("txtRol").value = rol;
    }
</script>
<%@include file="../_down.jsp"%>

