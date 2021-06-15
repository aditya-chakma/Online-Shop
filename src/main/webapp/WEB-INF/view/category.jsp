<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>
        <spring:message code="title.category"/>
    </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/public/bootstrap/css/bootstrap.min.css"/>">
</head>
<body>
<div class="d-flex flex-column align-items-center justify-content-center">
    <div class="mt-5">
        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <c:out value="${message}"/>
                <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:out value="${error}"/>
                <button type="button" class="btn-close btn-sm" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
    </div>
    <form:form class="align-items-center" modelAttribute="category" action="/category" method="post">
        <div class="form-row">
            <form:hidden path="id"/>
            <div class="form-group">
                <label><spring:message code="label.categoryName"/></label>
                <form:input class="form-control mt-1" placeholder="Category Name" path="name"/>
            </div>
        </div>
        <div class="">
            <button type="submit" class="btn btn-primary mt-1">
                <spring:message code="${category.isNew() ? 'button.add' : 'button.update'}"/>
            </button>
        </div>
    </form:form>
</div>
</body>
</html>
