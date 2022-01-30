<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta charset="UTF-8">
    <title>${params.title}</title>
</head>

<body>
<c:if test="${params.withLogout}">
<h2 align="right">Admin ${name} (<a href="/">logout</a>)</h2> <br>
</c:if>
<h2 style="color: green;">${message }</h2>

<h3 style="color: blue;">
    <form:form action="${params.submitAction}" modelAttribute="user">
        <table>
            <c:if test="${!params.loginEnabled}">
            <form:hidden path="id"/>
            </c:if>
            <tr>
                <td>First Name:</td>
                <td><form:input path="firstName" placeholder="input first name"/></td>
                <td><form:errors path="firstName" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><form:input path="lastName" placeholder="input last name"/></td>
                <td><form:errors path="lastName" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Login:</td>
                <td><form:input path="login" placeholder="input login" readonly="${!params.loginEnabled}"/></td>
                <td><form:errors path="login" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Birthday:</td>
                <td><form:input path="dob" placeholder="MM/dd/yyyy"/></td>
                <td><form:errors path="dob" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><form:password path="password" placeholder="input password"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Password Again:</td>
                <td><form:password path="passwordAgain" placeholder="repeat password"/></td>
                <td><form:errors path="passwordAgain" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><form:input path="email" placeholder="input email"/></td>
                <td><form:errors path="email" cssClass="error"/></td>
            </tr>
            <tr>
                <label for="text">ROLE</label>
                <select id="isRole" name="isRole">
                    <option value="user">user</option>
                    <option value="admin">admin</option>
                </select>
            </tr>

            <c:if test="${params.withCaptcha}">
            <tr>
                <td>Captcha Image:</td>
                <td colspan="2">
                    <img src="data:image/jpg;base64,${user.captchaImg}">
                </td>
            </tr>
            <tr>
                <td>Captcha Text:</td>
                <td><form:input path="captchaText" placeholder="input captcha"/></td>
                <td><form:errors path="captchaText" cssClass="error"/></td>
            </tr>
            </c:if>

            <tr>
                <td colspan="3">
                    <input type="submit" value="Submit">
                </td>
            </tr>
        </table>
    </form:form>
</h3>
<form action="${params.cancelAction}">
    <button type="submit">CANCEL</button>
</form>
</body>
</html>
