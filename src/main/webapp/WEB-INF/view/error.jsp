<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title><spring:message code="error.page.title"/></title>
</head>
<body>
<div class="d-flex mt-5 justify-content-center text-center">
    <div class="col-md-6">
        <h2><c:out value="${message}"/></h2>
    </div>
</div>
</body>
</html>
