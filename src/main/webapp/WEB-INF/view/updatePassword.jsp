<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="user.password"/></title>
</head>
<body>
<div class="d-flex mt-5 ms-3 justify-content-center text-center">
    <table class="table table-striped table-bordered table-responsive" style="width: auto">
        <tbody>
        <form:form action="updatePassword" method="post" modelAttribute="passwordCommand">
            <form:hidden path="userId"/>

            <tr>
                <td><spring:message code="password.old"/></td>
                <td><form:password path="oldPassword" placeholder="old password"/></td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="oldPassword"/> </td>
            </tr>

            <tr>
                <td><spring:message code="password.new"/></td>
                <td><form:password path="newPassword" placeholder="new password"/></td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="newPassword"/> </td>
            </tr>

            <tr>
                <td><spring:message code="password.retype"/></td>
                <td><form:password path="reTypePassword" placeholder="re-type password"/></td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="reTypePassword"/> </td>
            </tr>

            <tr>
                <td colspan="2">
                    <button type="submit" class="btn btn-sm btn-primary"><spring:message code="submit"/></button>
                </td>
            </tr>
        </form:form>

        </tbody>
    </table>
</div>

</body>
</html>
