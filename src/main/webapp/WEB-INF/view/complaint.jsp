<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="title.complaint"/></title>
</head>
<body>
<div class="d-flex justify-content-center text-center ms-3 mt-5">
    <table class="table table-striped table-bordered table-responsive" style="width: auto">
        <caption class="caption-top"><spring:message code="complaint.caption.newComplaint"/></caption>
        <form:form action="complaint" method="post" modelAttribute="complaint">
            <form:hidden path="user.id"/>
            <form:hidden path="version"/>

            <tbody>
                <tr>
                    <td><spring:message code="complaint.title"/></td>
                    <td><form:input path="title"/></td>
                </tr>
                <tr>
                    <td colspan="2"><form:errors path="title"/> </td>
                </tr>


                <tr>
                    <td><spring:message code="complaint.details"/></td>
                    <td><form:textarea path="reply.message" cols="30" rows="15"/></td>
                </tr>
                <tr>
                    <td colspan="2"><form:errors path="reply.message"/></td>
                </tr>

                <tr>
                    <td colspan="2">
                        <button type="submit" class="btn btn-sm btn-primary"><spring:message code="submit"/></button>
                    </td>
                </tr>
            </tbody>
        </form:form>
    </table>
</div>
</body>
</html>
