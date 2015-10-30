<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cc" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
    <%-- JQuery --%>
    <%@include file="/include/jquery_include.jsp" %>
    <!-- Bootstrap Core JavaScript -->
<%@include file="/include/bootstrup_include.jsp" %>

</head>
<body>
<div class="container">

    <div class="panel" style="background: none;">
    <div class="container">
        <div class="row">
            <div class="col col-sm-4 col-md-4 col-lg-4"><c:message code="label.requests.type"/>:</div>
            <div class="col col-sm-4 col-md-4 col-lg-4">${requestObjectObject.typeRequest}</div>
        </div><div class="row">
            <div class="col col-sm-4 col-md-4 col-lg-4"><c:message code="label.requests.counterparty"/>:</div>
            <div class="col col-sm-4 col-md-4 col-lg-4">${requestObject.counterparty.shortName}</div>
            <div class="col col-sm-4 col-md-4 col-lg-4">${requestObject.counterparty.fullName}</div>
    </div><div class="row">
            <div class="col col-lg-3"><c:message code="label.requests.agreements"/>:</div>
            <div class="col col-lg-3">${requestObject.agreement.number}</div>
            <div class="col col-lg-3">${requestObject.agreement.name}</div>
            <div class="col col-lg-3">${requestObject.agreement.startDate}</div>
    </div><div class="row">
            <div class="col col-sm-2 col-md-2 col-lg-2"><c:message code="label.requests.responsePerson"/>:</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.responsiblePerson.surname}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.responsiblePerson.firstName}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.responsiblePerson.middleName}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2"><c:message code="label.requests.modificationDate"/>:</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.operationDate}</div>
    </div><div class="row">
            <div class="col col-sm-2 col-md-2 col-lg-2"><c:message code="label.requests.approvedPerson"/>:</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.approvedPerson.surname}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.approvedPerson.firstName}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.approvedPerson.middleName}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2"><c:message code="label.requests.approvedDate"/>:</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.approvedDate}</div>
    </div><div class="row">
            <div class="col col-sm-2 col-md-2 col-lg-2"><c:message code="label.requests.commitedPerson"/>:</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.executedPerson.surname}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.executedPerson.firstName}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.executedPerson.middleName}</div>
            <div class="col col-sm-2 col-md-2 col-lg-2"><c:message code="label.requests.commitedDate"/>:</div>
            <div class="col col-sm-2 col-md-2 col-lg-2">${requestObject.executedDate}</div>
    </div><div class="row">
        <div class="col col-lg-12">${requestObject.comments}</div>
    </div>
    </div>
    </div>
    <div class="panel">
        <div class="panel-heading"><c:message code="label.requests.nomenclature"/></div>
        <div class="panel-body">
            <table  class="table table-striped">
                <thead>
                    <th><c:message code="label.requests.nomenclature"/></th>
                    <th><c:message code="label.requests.items.count"/></th>
                    <th><c:message code="label.requests.items.unit"/></th>
                    <th><c:message code="label.requests.items.comments"/></th>
                    <th><c:message code="label.requests.items.date"/></th>
                </thead>
                <tbody>
                    <cc:forEach var="i" items="${requestObject.items}">
                        <tr>
                            <td><cc:out value="${i.nomenclature.name}"/></td>
                            <td><cc:out value="${i.count}"/></td>
                            <td><cc:out value="${i.units}"/></td>
                            <td><cc:out value="${i.comments}"/></td>
                            <td><cc:out value="${i.lastUpdate}"/></td>
                        </tr>
                    </cc:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>