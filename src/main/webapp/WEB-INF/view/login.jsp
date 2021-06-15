<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.login"/></title>
</head>
<body>
<div class="d-flex justify-content-center text-center mt-5">
    <table class="table table-striped table-bordered" style="width: auto">

        <caption class="caption-top"><spring:message code="login.caption"/> </caption>

        <tbody>
        <form:form action="login" method="post" modelAttribute="loginCommand">
            <tr>
                <td><spring:message code="login.email.label"/></td>
                <td><form:input path="email"/></td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="email"/></td>
            </tr>

            <tr>
                <td><spring:message code="login.password"/></td>
                <td><form:password path="password"/></td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="password"/> </td>
            </tr>

            <tr>
                <td>
                    <button type="submit" class="btn btn-primary btn-sm">
                        <spring:message code="button.login"/>
                    </button>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${lang==\"bn\"}">
                            <c:url value="login" var="loginUrl">
                                <c:param name="lang" value="en"/>
                            </c:url>
                        </c:when>
                        <c:otherwise>
                            <c:url value="login" var="loginUrl">
                                <c:param name="lang" value="bn"/>
                            </c:url>
                        </c:otherwise>
                    </c:choose>
                    <a href="${loginUrl}" class="btn btn-primary btn-sm"><spring:message code="button.changeLanguage"/></a>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <spring:message code="login.new"/>
                    <c:url value="user" var="registrationUrl"/>
                    <a href="${registrationUrl}"><spring:message code="login.registration"/> </a>
                </td>
            </tr>
        </form:form>
        </tbody>
    </table>
</div>
</body>
</html>
