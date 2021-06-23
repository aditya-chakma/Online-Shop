<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.orderList"/></title>
</head>
<body>
<div class="table-responsive d-flex justify-content-center text-center mt-5">
    <table class="table table-striped table-bordered caption-top" style="width: auto">

        <caption><spring:message code="order.caption.order"/> </caption>

        <thead class="table-dark">
            <th scope="col"><spring:message code="order.number"/> </th>
            <th scope="col"><spring:message code="order.time"/></th>
            <th scope="col"><spring:message code="order.updateTime"/> </th>
            <th scope="col" style="max-width: 400px"><spring:message code="user.address"/></th>
            <th scope="col"><spring:message code="order.total"/></th>
            <th scope="col"><spring:message code="order.status"/></th>
            <th scope="col"><spring:message code="order.details"/> </th>
        </thead>

        <tbody>
        <c:forEach items="${listOfOrder}" var="order">
            <tr>
                <td><c:out value="# ${order.id}"/></td>
                <td><c:out value="${order.createdAt}"/></td>
                <td><c:out value="${order.updatedAt}"/></td>
                <td style="max-width: 400px"><c:out value="${order.user.address}"/></td>
                <td><c:out value="${order.subTotal}"/></td>
                <td><c:out value="${order.status}"/></td>
                <td>
                    <c:url var="orderUrl" value="order">
                        <c:param name="id" value="${order.id}"/>
                    </c:url>
                    <a href="${orderUrl}" class="btn btn-primary"><spring:message code="button.details"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
