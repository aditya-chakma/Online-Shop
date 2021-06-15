<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        <spring:message code="title.productList"/>
    </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/public/bootstrap/css/bootstrap.min.css"/>">
</head>
<body>
<div class="container pt-5">
    <div class="row">
        <c:if test="${productList.size() == 0}">
            <h5 class="text-danger fw-bold text-center">
                <c:out value="No Products Available"/>
            </h5>
        </c:if>
        <c:forEach items="${productList}" var="product">
            <div class="col-sm-3 mt-2 mb-3">
                <div class="card">
                    <c:url value="/image" var="imageUrl">
                        <c:param name="productId" value="${product.id}"/>
                    </c:url>
                    <img class="card-img-top" style="width: 100%; height: 15vw; object-fit: contain;"
                         src="${imageUrl}" alt="">
                    <div class="card-body text-center p-0 pb-1">
                        <c:url value="/productDetails" var="productDetailsUrl">
                            <c:param name="productId" value="${product.id}"/>
                        </c:url>
                        <a href="${productDetailsUrl}" class="mb-0 text-decoration-none">
                            <c:out value="${product.name}"/>
                        </a>
                        <p class="card-text mb-0">
                            <spring:message code="label.priceValue"/>
                            <c:out value=": ${product.price}"/>
                        </p>
                        <c:if test="${product.status == 'IN_STOCK'}">
                            <p class="card-text text-success fw-bold mb-0">
                                <spring:message code="status.inStock"/>
                            </p>
                        </c:if>
                        <c:if test="${product.status == 'OUT_OF_STOCK'}">
                            <p class="card-text text-danger fw-bold mb-0">
                                <spring:message code="status.outOfStock"/>
                            </p>
                        </c:if>
                        <c:if test="${product.status == 'DISCONTINUED'}">
                            <p class="card-text text-danger fw-bold mb-0">
                                <spring:message code="status.discontinued"/>
                            </p>
                        </c:if>
                        <c:if test="${sessionScope.role == 'CUSTOMER'}">
                            <form:form modelAttribute="cartItemCmd" action="/cartItem" method="post">
                                <form:hidden path="id"/>
                                <form:hidden path="product" value="${product}"/>
                                <form:hidden path="user"/>
                                <form:hidden path="quantity"/>
                                <c:if test="${product.status == 'OUT_OF_STOCK' || product.status == 'DISCONTINUED'}">
                                    <button type="submit" class="btn btn-sm btn-primary ms-1 disabled">
                                        <spring:message code="button.addToCart"/>
                                    </button>
                                </c:if>
                                <c:if test="${product.status == 'IN_STOCK'}">
                                    <button type="submit" class="btn btn-sm btn-primary ms-1">
                                        <spring:message code="button.addToCart"/>
                                    </button>
                                </c:if>
                            </form:form>
                        </c:if>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                            <div class="d-flex justify-content-center">
                                <c:url value="/productDiscontinue" var="productDiscontinueUrl">
                                    <c:param name="productId" value="${product.id}"/>
                                    <c:if test="${product.status == 'DISCONTINUED'}">
                                        <c:param name="isContinue" value="true"/>
                                    </c:if>
                                </c:url>
                                <form:form action="${productDiscontinueUrl}" method="post">
                                    <c:if test="${product.status == 'DISCONTINUED'}">
                                        <button type="submit" class="btn btn-success btn-sm">
                                            <spring:message code="button.continue"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${product.status != 'DISCONTINUED'}">
                                        <button type="submit" class="btn btn-danger btn-sm">
                                            <spring:message code="button.discontinue"/>
                                        </button>
                                    </c:if>
                                </form:form>
                                <c:url value="/product" var="productUrl">
                                    <c:param name="id" value="${product.id}"/>
                                </c:url>
                                <a href="${productUrl}" class="ms-1 btn btn-sm btn-primary">
                                    <spring:message code="button.update"/>
                                </a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
