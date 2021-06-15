<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.order"/></title>
</head>
<body>
<div class=" d-flex justify-content-center text-center mt-5">
    <table class="table table-responsive table-bordered table-striped caption-top" style="width: auto">

        <caption><spring:message code="order.caption.products"/></caption>

        <thead class="table-dark">
            <th scope="col"><spring:message code="title.product"/> </th>
            <th scope="col"><spring:message code="product.price"/> </th>
            <th scope="col"><spring:message code="order.quantity"/> </th>
            <c:if test="${isAdmin}">
                <th scope="col"><spring:message code="product.stock"/> </th>
            </c:if>
        </thead>

        <tbody>
        <c:forEach items="${listOfOrderProduct}" var="orderProduct">
            <tr>
                <td><c:out value="${orderProduct.product.name}"/></td>
                <td><c:out value="${orderProduct.product.price}"/></td>
                <td><c:out value="${orderProduct.quantity}"/></td>
                <c:if test="${isAdmin}">
                    <td><c:out value="${orderProduct.product.quantity}"/></td>
                </c:if>
            </tr>
        </c:forEach>

        <c:if test="${!order.delivered && isAdmin}">
            <tr>
                <td colspan="4">
                    <div class="form-group mt-3 ms-3">
                        <form:form action="order" modelAttribute="order" method="post">
                            <form:hidden path="id"/>
                            <form:hidden path="user.id"/>
                            <form:hidden path="createdAt" class="date"/>
                            <form:hidden path="updatedAt" class="date"/>
                            <form:hidden path="subTotal"/>

                            <spring:message code="order.status"/>
                            <form:select path="status" cssClass="form-select" items="${listOfStatus}"/>
                            <form:errors path="status"/>

                            <button type="submit" class="btn btn-sm btn-primary"> <spring:message code="button.update"/> </button>
                        </form:form>
                    </div>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
