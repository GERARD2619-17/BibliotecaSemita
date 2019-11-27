<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="_top.jsp" %>
<style>
    #opciones li a{
        text-decoration: none;
        font-size: 14px
    }
</style>
    <ul id="opciones">
            <c:forEach var="opcion" items="${PermisosAsignados}">
                <li><a href="${pageContext.servletContext.contextPath}${opcion.url}?op=${opcion.idmenu}">${opcion.menu}</a></li>    
            </c:forEach>
            </ul>
<%@include file="_down.jsp" %>
<a href="principal.jsp"></a>