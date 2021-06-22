<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        <spring:message code="title.details"/>
    </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/public/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/public/bootstrap/js/bootstrap.min.js"/>">
</head>
<body>
<div class="d-flex flex-column">
    <div class="d-flex flex-row justify-content-center mt-5">
        <div id="carouselControls" class="carousel slide" style="height: 190px; width: 300px;" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <c:url value="/image" var="imageUrl">
                        <c:param name="productId" value="${product.id}"/>
                    </c:url>
                    <img src="${imageUrl}" alt="" style="height: 190px; width: 300px!important; margin: 0">
                </div>
                <c:forEach items="${product.productImages}" var="productImage" varStatus="loop">
                    <c:if test="${loop.index > 0}">
                        <div class="carousel-item">
                            <c:url value="/image" var="imageUrl">
                                <c:param name="imageId" value="${productImage.id}"/>
                            </c:url>
                            <img src="${imageUrl}" alt="" style="height: 190px; width: 300px!important; margin: 0">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselControls"
                    data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselControls"
                    data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <div class="ms-2">
            <h5>
                <c:out value="${product.name}"/>
            </h5>
            <p class="m-1">
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
            <p class="m-1">
                <spring:message code="label.averageRating"/>
                <c:out value=": ${averageRatingValue}"/>
            </p>
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <p class="m-1">
                    <spring:message code="label.quantity"/>
                    <c:out value=": ${product.quantity}"/>
                </p>
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
            </c:if>
            <c:if test="${sessionScope.role == 'CUSTOMER'}">
                <div class="mt-1">
                    <c:url value="/rate" var="rateUrl">
                        <c:param name="productId" value="${product.id}"/>
                    </c:url>
                    <form:form modelAttribute="rating" action="${rateUrl}" method="post">
                        <form:hidden path="id"/>
                        <form:hidden path="user"/>
                        <form:hidden path="product"/>
                        <form:hidden path="version"/>
                        <form:input type="number" class="text-center" style="width: 40px" path="ratingValue" step="1" min="1" max="10"/>
                        <button type="submit" class="btn btn-sm btn-primary">
                            <spring:message code="button.rateIt"/>
                        </button>
                    </form:form>
                </div>
                <div class="mt-1">
                    <form:form modelAttribute="cartItemCmd" action="/cartItem" method="post">
                        <form:hidden path="id"/>
                        <form:hidden path="product"/>
                        <form:hidden path="user"/>
                        <form:hidden path="version"/>
                        <form:hidden path="quantity"/>
                        <c:if test="${product.status == 'OUT_OF_STOCK' || product.status == 'DISCONTINUED'}">
                            <button type="submit" class="btn btn-sm btn-primary disabled" style="width: 105px">
                                <spring:message code="button.addToCart"/>
                            </button>
                        </c:if>
                        <c:if test="${product.status == 'IN_STOCK'}">
                            <button type="submit" class="btn btn-sm btn-primary" style="width: 105px">
                                <spring:message code="button.addToCart"/>
                            </button>
                        </c:if>
                    </form:form>
                </div>
            </c:if>
        </div>
    </div>
    <div class="d-flex justify-content-center mt-4">
        <c:out value="${product.details}"/>
    </div>
</div>
</body>
</html>
