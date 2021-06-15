<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.profile"/></title>
</head>
<body>
<div class="table-responsive d-flex justify-content-center text-center mt-5">
    <table class="table table-borderless table-striped" style="width: auto">
        <tbody>
        <tr>
            <td rowspan="5">
                    <c:url value="/profileImage" var="imageUrl"/>
                    <img src="${imageUrl}" alt="" style="height: 250px; width: 200px!important; margin: 0">
            </td>
        </tr>

        <tr>
            <td><spring:message code="user.name"/> </td>
            <td><c:out value="${user.name}"/></td>
        </tr>

        <tr>
            <td><spring:message code="user.address"/></td>
            <td><c:out value="${user.address}"/></td>
        </tr>

        <tr>
            <td><spring:message code="user.email"/></td>
            <td><c:out value="${user.email}"/></td>
        </tr>

        <tr>
            <td><spring:message code="user.mobileNumber"/> </td>
            <td><c:out value="${user.mobileNo}"/></td>
        </tr>

        <tr>
            <td colspan="2">
                <c:url value="updateProfile" var="uProfileUrl"/>
                <a href="${uProfileUrl}" class="btn-primary btn btn-sm"><spring:message code="button.update.profile"/> </a>
            </td>
            <td>
                <c:url value="updatePassword" var="uPassUrl"/>
                <a href="${uPassUrl}" class="btn-primary btn btn-sm"><spring:message code="button.update.password"/> </a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
