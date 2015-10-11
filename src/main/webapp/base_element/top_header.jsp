<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 09.10.2015
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />

<div class="navbar-header">
  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
    <span class="sr-only">Toggle navigation</span>
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
  </button>
  <a class="navbar-brand" href="index.html"><c:message code="label"/></a>
</div>

<ul class="nav navbar-top-links navbar-right">
  <li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
      <i class="fa fa-user fa-fw"></i>
      Hi, ${user.username}
      <i class="fa fa-caret-down"></i>
    </a>

    <ul class="dropdown-menu dropdown-user">
      <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
      </li>
      <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
      </li>
      <li class="divider"></li>
      <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> <c:message code="label.menu.Logout"/></a>
      </li>
    </ul>
    <!-- /.dropdown-user -->
  </li>
</ul>