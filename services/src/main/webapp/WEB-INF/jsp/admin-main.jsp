<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"  "http://www.w3.org/TR/html4/strict.dtd">

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>
<h2 align="right" style="color: blue">Admin ${name} (<a href="/logout">logout</a>)</h2> <br>
<h2 align="left" > <a href="/admin/add">Add new user</a></h2>

<table bordercolor="grey" border="1" width="80%">
    <tr>
        <th><h2>LOGIN</h2></th>
        <th><h2>FIRST NAME</h2></th>
        <th><h2>LAST NAME</h2></th>
        <th><h2>AGE</h2></th>
        <th><h2>ROLE</h2></th>
        <th><h2>ACTION</h2></th>
    </tr>
    <c:forEach var="person" items="${persons }">
        <tr>
            <td> <h2 align="center">${person.login} </h2></td>
            <td> <h2 align="center">${person.firstName} </h2></td>
            <td> <h2 align="center">${person.lastName} </h2></td>
            <td> <h2 align="center">${person.age} </h2></td>
            <td> <h2 align="center">${person.role.role} </h2></td>
            <td> <h2 align="center">
                <a href="javascript:deleteUserAccount(${person.id})">Delete</a>
                <a href="/admin/update?up_id=${person.id}">Edit</a>
            </h2>
            </td>
        </tr>
    </c:forEach>
</table>

<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script>
    function deleteUserAccount(id) {
        if (confirm("Are you sure?")) {
            $('#idToDelete').val(id);
            $('#deleteForm').submit();
        }
    }
</script>

<div style="display:none">
    <form id="deleteForm" action="/admin/delete" method="post">
        <input id="idToDelete" name="id" value=""/>
    </form>
</div>

</body>
</html>

