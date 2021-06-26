<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        <spring:message code="title.cart"/>
    </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/public/bootstrap/css/bootstrap.min.css"/>">
</head>
<body>
<div class="d-flex justify-content-center text-center">
    <div class="column">
        <div class="row mt-5">
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
        <div class="row">
            <table class="table table-bordered" style="width: auto">
                <thead class="table-dark">
                <tr>
                    <th>
                        <spring:message code="table.productName"/>
                    </th>
                    <th>
                        <spring:message code="table.quantity"/>
                    </th>
                    <th>
                        <spring:message code="table.unitPrice"/>
                    </th>
                    <th>
                        <spring:message code="table.total"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cartItemsCmd}" var="cartItem">
                    <tr>
                        <td>
                            <c:out value="${cartItem.product.name}"/>
                        </td>
                        <td class="ms-0 me-0">
                            <div class="d-flex">
                                <c:url value="/cartItem" var="cartItemUrl">
                                    <c:param name="productId" value="${cartItem.product.id}"/>
                                    <c:param name="isCartItemPage" value="true"/>
                                </c:url>

                                <form:form modelAttribute="cartItemCmd" action="${cartItemUrl}" method="post">
                                    <form:hidden path="id" value="${cartItem.id}"/>
                                    <form:hidden path="product" value="${cartItem.product}"/>
                                    <form:hidden path="user" value="${cartItem.user}"/>
                                    <form:hidden path="version" value="${cartItem.version}"/>
                                    <form:input path="quantity" value="${cartItem.quantity}" type="number"
                                                style="text-align: center; width: 65px"/>
                                    <button name="update" type="submit" class="btn btn-sm btn-primary">
                                        <spring:message code="button.update"/>
                                    </button>
                                    <form:errors path="quantity"/>
                                </form:form>

                                <c:url value="/cartItemDelete" var="cartItemDeleteUrl">
                                    <c:param name="cartItemId" value="${cartItem.id}"/>
                                </c:url>
                                <form:form cssClass="ms-1" action="${cartItemDeleteUrl}" method="post">
                                    <button type="submit" class="btn btn-sm btn-danger">
                                        <spring:message code="button.remove"/>
                                    </button>
                                </form:form>
                            </div>
                        </td>
                        <td>
                            <c:out value="${cartItem.product.price}"/>
                        </td>
                        <td>
                            <c:out value="${cartItem.total}"/>
                        </td>
                    </tr>
                </c:forEach>
                <td></td>
                <td></td>
                <td class="fw-bold">
                    <spring:message code="table.grandTotal"/>
                </td>
                <td>
                    <c:out value="${grandTotalCmd}"/>
                </td>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="text-center">
                <c:if test="${cartItemsCmd.size() > 0}">
                    <form:form action="/proceed" method="post">
                        <button type="submit" class="btn btn-sm btn-primary">
                            <spring:message code="button.placeOrder"/>
                        </button>
                    </form:form>
                </c:if>
                <c:if test="${cartItemsCmd.size() == 0}">
                    <button type="submit" class="btn btn-sm disabled btn-primary">
                        <spring:message code="button.placeOrder"/>
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
