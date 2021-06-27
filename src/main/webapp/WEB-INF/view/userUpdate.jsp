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
    <form:form method="post" action="updateProfile" modelAttribute="user" enctype="multipart/form-data">
<%--        <form:hidden path="version"/>--%>
<%--        <form:hidden path="id"/>--%>

        <table class="table table-bordered table-responsive table-striped">
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
                <td colspan="2"><form:hidden path="hashedPassword"/></td>
            </tr>
            <tr>
                <td colspan="2"><form:hidden path="role"/></td>
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
                <td>
                    <form:hidden path="imageLink"/>
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
