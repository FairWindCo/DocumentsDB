<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2015
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />
<html>
<head>
  <title></title>
  <%-- JQuery --%>
  <%@include file="/include/jquery_include.jsp" %>
  <!-- Bootstrap Core JavaScript -->
  <%@include file="/include/bootstrup_include.jsp" %>
  <%@include file="/include/jgrid_include_ex.jsp" %>

</head>`
<body>
<div id="wrapper">
  <!-- Navigation -->
  <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <%@include file="/base_element/top_header.jsp" %>
    <%@include file="/base_element/left_menu.jsp" %>
  </nav>
</div>

<div id="page-wrapper">
  <ol class="breadcrumb">
    <li><a href="${pageContext.request.contextPath}/"><c:message code="label.main"/></a></li>
    <li class="active"><c:message code="label.globaldirectory.documenttype"/></li>
  </ol>
  <div class="row">

    <div class="col-lg-12">

      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title"><c:message code="label.messages.for_me.title"/></h3>
        </div>
        <div class="panel-body">
          <div>
            <%@include file="/jsp/messages/actual.jsp" %>
          </div>
        </div>
      </div>

    </div>
    <!-- /.col-lg-12 -->
  </div>
</div>
</body>
</html>
