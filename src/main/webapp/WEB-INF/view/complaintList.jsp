<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.complaints"/></title>
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-md-center">
        <div class="col col-md-6">
            <h4>
                <c:if test="${isCustomer}">
                    <c:url value="complaint" var="complaintUrl"/>
                    <spring:message code="complaint.message"/>
                    <a href="${complaintUrl}"><spring:message code="complaint.new"/></a>
                </c:if>
            </h4>
        </div>
    </div>

    <div class="row justify-content-md-center">
        <div class="col col-md-6">
            <table class="table-responsive table table-striped table-bordered" style="width: auto">

                <caption class="caption-top"><spring:message code="title.complaints"/></caption>

                <thead class="table-dark">
                <th scope="col" style="min-width: 100px"><spring:message code="complaint.title"/></th>
                <th scope="col" style="min-width: 200px"><spring:message code="complaint.time"/></th>
                <th scope="col" style="min-width: 100px"><spring:message code="complaint.status"/></th>
                <th scope="col" style="min-width: 100px"><spring:message code="complaint.details"/></th>
                </thead>

                <tbody>
                <c:forEach items="${listOfComplaints}" var="complaint">
                    <tr>
                        <td><c:out value="${complaint.title}"/></td>
                        <td><c:out value="${complaint.createdAt}"/></td>
                        <td><c:out value="${complaint.status}"/></td>
                        <td>
                            <c:url value="complaintReply" var="replyUrl">
                                <c:param name="complaintId">${complaint.id}</c:param>
                            </c:url>
                            <a href="${replyUrl}" class="btn btn-primary"><spring:message code="button.details"/> </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
