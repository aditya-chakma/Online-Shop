<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="user.title"/></title>
</head>
<body>

<div class="d-flex justify-content-center text-center mt-5">
    <form:form method="post" action="user" modelAttribute="user" enctype="multipart/form-data">
        <table class="table table-responsive table-bordered table-striped">
            <tbody>
            <tr>
                <td colspan="2">
                    <form:hidden path="id"/>
                </td>
            </tr>

            <tr>
                <td><spring:message code="user.name"/> </td>
                <td><form:input path="name"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <form:errors path="name"/>
                </td>
            </tr>

            <tr>
                <td><spring:message code="user.email"/> </td>
                <td><form:input path="email"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <form:errors path="email"/>
                </td>
            </tr>

            <tr>
                <td><spring:message code="user.mobileNumber"/> </td>
                <td><form:input path="mobileNo"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <form:errors path="mobileNo"/>
                </td>
            </tr>

            <tr>
                <td><spring:message code="user.password"/></td>
                <td><form:password path="hashedPassword"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <form:errors path="hashedPassword"/>
                </td>
            </tr>

            <tr>
                <td><spring:message code="user.address"/> </td>
                <td><form:textarea path="address" cols="30" rows="15"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <form:errors path="address"/>
                </td>
            </tr>
            <tr>
                <td><spring:message code="user.image"/> </td>
                <td><form:input path="image" type="file" multiple="false"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <form:errors path="image"/>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" value=<spring:message code="submit"/>>
                </td>
            </tr>

            </tbody>
        </table>
    </form:form>
</div>

</body>
</html>
