<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.complaintReply"/></title>
</head>
<body>

<div class="d-flex justify-content-md-center mt-5">
    <div class="col-md-3">
        <c:if test="${isOpen && isAdmin}">
            <form:form method="post" action="closeComplaint" modelAttribute="complaint">
<%--                <form:hidden path="id"/>--%>
                <button type="submit" class="btn btn-primary"><spring:message code="complaint.close"/> </button>
            </form:form>
        </c:if>

        <table class="table table-borderless table-striped table-responsive">

                <thead class="table-dark">
                <th><c:out value="${complaint.title}"/></th>
                </thead>


            <tbody>
            <c:forEach items="${replies}" var="reply">
                    <tr>
                        <td><c:out value="${reply.message}"/></td>
                    </tr>
            </c:forEach>

            <c:if test="${isOpen}">
                <form:form method="post" modelAttribute="complaintReply" action="complaintReply">
                    <tr>
                        <td>
                            <form:textarea path="message" cols="30" rows="15"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:errors path="message"/>
                        </td>
                    </tr>

                    <form:hidden path="complaint.id" value="${complaint.id}"/>
                    <tr>
                        <td><button class="btn btn-sm btn-primary" type="submit"><spring:message code="button.reply"/></button></td>
                    </tr>
                </form:form>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
