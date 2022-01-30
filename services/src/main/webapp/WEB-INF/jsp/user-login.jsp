<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>

<%-- MainController / MainController --%>
<html>
<body>
<h1 align="center" style="color: red;"> ${message}</h1> <br>

<hr>
<h1 style="color: green;">Please LOG IN OR <a href="/register">REGISTER</a> IN THE SYSTEM</h1>
<hr>
<form action="/login" method="post">
    <h2 style="color: blue;"> INPUT LOGIN <input type="text" name="login"></h2>
    <h2 style="color: blue;"> INPUT PASSWORD: <input type="password" name="password"></h2> <br>
    <input type="submit" value="sign in">
</form>
</body>
</html>