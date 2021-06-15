<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>
        <decorator:title default="TherapShop"/>
    </title>
    <script type="text/javascript" src="<c:url value="/public/bootstrap/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/public/bootstrap/css/bootstrap.min.css"/>">
    <decorator:head/>
</head>
<body>
<nav class="navbar navbar-light bg-light shadow">
    <div class="container-fluid">
        <c:url value="/cartItem" var="cartItemUrl"/>
        <c:url value="/product" var="productUrl"/>
        <c:url value="/category" var="categoryUrl"/>
        <c:url value="/productList" var="productListUrl"/>
        <c:url value="/orderList" var="orderListUrl"/>
        <c:url value="/complaintList" var="complaintListUrl"/>
        <c:url value="/logout" var="logoutUrl"/>
        <c:url value="/profile" var="profileUrl"/>
        <c:url value="/public/images/therapShop.png" var="brandLogo"/>
        <a href="${productListUrl}">
            <img src="${brandLogo}" alt="">
        </a>
        <c:if test="${not empty sessionScope.role}">
            <div class="dropdown">
                <a href="#" class="text-decoration-none dropdown-toggle me-2 ms-3 fw-bold text-success"
                   role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">
                    <spring:message code="title.category"/>
                </a>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <c:forEach items="${sessionScope.categoryList}" var="category">
                        <li>
                            <c:url value="/productList" var="productListByCategoryUrl">
                                <c:param name="categoryId" value="${category.id}"/>
                            </c:url>
                            <a class="dropdown-item" href="${productListByCategoryUrl}">
                                <c:out value="${category.name}"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <a href="${productUrl}" class="text-decoration-none me-2 ms-3 fw-bold text-success">
                    <spring:message code="label.addProduct"/>
                </a>
                <a href="${categoryUrl}" class="text-decoration-none me-2 ms-3 fw-bold text-success">
                    <spring:message code="label.addCategory"/>
                </a>
            </c:if>
            <c:if test="${sessionScope.role == 'CUSTOMER'}">
                <a href="${cartItemUrl}" class="text-decoration-none me-2 ms-3 fw-bold text-success">
                    <spring:message code="label.cart"/>
                </a>
            </c:if>
            <a href="${complaintListUrl}" class="text-decoration-none me-2 ms-3 fw-bold text-success">
                <c:if test="${sessionScope.role == 'CUSTOMER'}">
                    <spring:message code="label.myComplaints"/>
                </c:if>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <spring:message code="label.complaints"/>
                </c:if>
            </a>
            <a href="${orderListUrl}" class="text-decoration-none me-2 ms-3 fw-bold text-success">
                <c:if test="${sessionScope.role == 'CUSTOMER'}">
                    <spring:message code="label.order"/>
                </c:if>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <spring:message code="label.showOrder"/>
                </c:if>
            </a>
            <a href="${profileUrl}" class="text-decoration-none me-2 ms-3 fw-bold text-success">
                <spring:message code="label.account"/>
            </a>
            <a href="${logoutUrl}" class="text-decoration-none me-auto ms-3 fw-bold text-success">
                <spring:message code="label.logout"/>
            </a>
            <form:form action="/productList" class="d-flex" method="get">
                <input class="form-control me-2" placeholder="Search" aria-label="Search" name="productName">
                <button class="btn btn-outline-success" type="submit">
                    <spring:message code="label.search"/>
                </button>
            </form:form>
        </c:if>
    </div>
</nav>
<div id="content">
    <decorator:body/>
</div>
<div id="Footer">
</div>
</body>
</html>