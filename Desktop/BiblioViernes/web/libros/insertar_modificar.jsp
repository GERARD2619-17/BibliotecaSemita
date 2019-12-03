<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../_top.jsp"%>
<script>
    function abrirVentana(URL) {
//funcion javascript para abrir un subventana para realizar
//busquedas, se le pasa la pagina a mostrar como parametro
        window.open(URL, "ventana1", "width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300")
    }
    
    function mostrar(){
  var archivo = document.getElementById("file").files[0];
  var reader = new FileReader();
  if (file) {
    reader.readAsDataURL(archivo );
    reader.onloadend = function () {
      document.getElementById("img").src = reader.result;
    }
  }
}
    
</script>
<h1>Registro de Libros</h1><br><br>
<form name="main" action="${pageContext.servletContext.contextPath}/Libros?accion=insertar_modificar" method="POST">
    <!--<input type="hidden" name="sw_nuevo" value="1"/>-->
    
    <table border="0" id="table" style="width: 50%">
    <thead>
        <tr>
            <th colspan="">Complete la información<br><br></th>
        </tr>
    </thead>
    <tbody>
    <tr>
        <td>Id libro:</td>
        <td><input type="text" name="txtIdlibro" id="txtIdlibro" size="2" readonly="readonly"></td>
    </tr>
    <tr>
        <td>Titulo:</td>
        <td><input type="text" placeholder="Titulo de Libro" required name="txtTitulo" id="txtTitulo" size="30" ></td>
    </tr>
    <tr>
        <td>Edicion:</td>
        <td><input type="text" placeholder="Nombre Edicion" required name="txtEdicion" id="txtEdicion" size="30" ></td>
    </tr>
    <tr>
        <td>ISBN:</td>
        <td><input type="text" placeholder="ISBN" required name="txtISBN" id="txtISBN" size="30" ></td>
    </tr>
    <tr>
        <td>Imagen:</td>
        <td><input type="file" id="file" accept="image/*" name="file" onchange="mostrar()" width="30" height="20"/>
         <br>
         <img id="img"/>
        </td>
    </tr>
        <tr>
        <td>Autores:</td>
        <td><input type="text" name="txtIdautor" id="txtIdautor" size="2" readonly="readonly">
             <input type="text" placeholder="Seleccione Autor" required name="txtAutor" id="txtAutor" readonly="readonly">
             <input type="button" value="..." class="boton" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Libros?accion=listado_autores');">
        </td>
    </tr>

    <tr>
        <td>Clasificacion:</td>
        <td><input type="text" name="txtIdclasificacion" id="txtIdclasificacion" size="2" readonly="readonly">
             <input type="text" placeholder="Seleccione Clasificacion" required name="txtClasificacion" id="txtClasificacion" readonly="readonly">
             <input type="button" value="..." class="boton" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Libros?accion=listado_clasificaciones');">
        </td>
    </tr>
    <tr>
        <td>Editorial :</td>
        <td><input type="text" name="txtIdeditorial" id="txtIdeditorial" size="2" readonly="readonly">
        <input type="text" placeholder="Seleccione Editorial" required name="txtEditorial" id="txtEditorial" readonly="readonly">
        <input type="button" value="..." class="boton" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Libros?accion=listado_editoriales');">
        </td>
    </tr>
    <tr>
        <td>Estado :</td>
        <td><input type="text" name="txtIdestado" id="txtIdestado" size="2" readonly="readonly">
    <input type="text" placeholder="Seleccione Estado" required name="txtEstado" id="txtEstado" readonly="readonly">
    <input type="button" value="..." class="boton" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Libros?accion=listado_estados');">
    
        </td>
    </tr>
    <tr>
        <td> </td>
    <td><input type="submit" align="center" value="Agregar" class="boton"></td>
    <li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li>
    </tr>
    </tbody>
    </table>
</form><br><br>
<c:if test="${resultado!=null}">
    <c:if test="${resultado==1}">
        <p style="color:darkgreen; font-size: 15px; text-align: center"><strong>Operación realizada correctamente.</strong></p>
    </c:if>
    <c:if test="${resultado==0}">
        <p style="color:darkred; font-size: 15px; text-align: center"><strong>La operación no se realizó.</strong></p>
    </c:if>
</c:if>
${tabla}
<script>
    
//funcion que se llamará al seleccionar el origen desde la ventana
    function setDataAutor(idautor, autor) {
        document.getElementById("txtIdautor").value = idautor;
        document.getElementById("txtAutor").value = autor;
    }
//funcion que se llamará al seleccionar el avion desde la ventana
    function setDataClasificacion(idclasificacion, clasificacion) {
        document.getElementById("txtIdclasificacion").value = idclasificacion;
        document.getElementById("txtClasificacion").value = clasificacion;
    }
    function setDataEditorial(ideditorial, editorial) {
        document.getElementById("txtIdeditorial").value = ideditorial;
        document.getElementById("txtEditorial").value = editorial;
    }
    function setDataEstado(idestado, estado) {
        document.getElementById("txtIdestado").value = idestado;
        document.getElementById("txtEstado").value = estado;
    }
</script>
<%@include file="../_down.jsp"%>