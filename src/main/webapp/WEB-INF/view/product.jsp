<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        <spring:message code="title.product"/>
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
    <form:form cssClass="align-items-center" modelAttribute="product" action="/product" method="post"
               enctype="multipart/form-data">
        <form:hidden path="id"/>
        <form:hidden path="status"/>
        <form:hidden path="version"/>

        <div class="form-group mt-1">
            <label><spring:message code="label.product"/></label>
            <form:input cssClass="form-control" placeholder="Product Name" path="name"/>
        </div>

        <div class="form-group mt-1">
            <label><spring:message code="label.catagory"/></label>
            <form:select cssClass="form-select" path="category">
                <form:options items="${listOfCategory}" itemValue="id" itemLabel="name"/>
            </form:select>
        </div>

        <div class="form-group mt-1">
            <label><spring:message code="label.enterQuantity"/></label>
            <form:input cssClass="form-control" placeholder="Quantity" path="quantity"/>
        </div>

        <div class="form-group mt-1">
            <label><spring:message code="label.price"/></label>
            <form:input cssClass="form-control" placeholder="Price" path="price"/>
        </div>

        <div class="form-group mt-1">
            <label><spring:message code="label.details"/></label>
            <form:textarea cssClass="form-control" placeholder="Details" path="details"/>
        </div>

        <div class="form-group mt-1">
            <div class="row">
                <div class="d-flex">
                    <c:forEach items="${product.productImages}" var="image" varStatus="loop">
                        <div class="card p-0">
                            <c:url value="/image" var="imageUrl">
                                <c:param name="imageId" value="${image.id}"/>
                            </c:url>
                            <img src="${imageUrl}" class="card-img-top" style="height: 100px; width: 118px" alt="">
                            <c:url value="/removeImage" var="removeImageUrl">
                                <c:param name="productId" value="${product.id}"/>
                                <c:param name="imageId" value="${image.id}"/>
                            </c:url>
                            <a href="${removeImageUrl}" class="btn btn-sm btn-danger">
                                <spring:message code="button.remove"/>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="form-group mt-1">
            <form:input cssClass="form-control" type="file" path="images" multiple="true"/>
        </div>

        <div class="mt-1">
            <button type="submit" class="btn btn-sm btn-primary">
                <spring:message code="${product.isNew() ? 'button.add' : 'button.update'}"/>
            </button>
            <c:choose>
                <c:when test="${lang==\"bn\"}">
                    <c:url value="/product" var="productLangUrl">
                        <c:param name="lang" value="en"/>
                    </c:url>
                </c:when>
                <c:otherwise>
                    <c:url value="/product" var="productLangUrl">
                        <c:param name="lang" value="bn"/>
                    </c:url>
                </c:otherwise>
            </c:choose>
            <a href="${productLangUrl}" class="btn btn-primary btn-sm">
                <spring:message code="button.changeLanguage"/>
            </a>
        </div>
    </form:form>
</div>
</body>
</html>
