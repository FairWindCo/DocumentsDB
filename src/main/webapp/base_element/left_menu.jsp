<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 09.10.2015
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>


<div class="navbar-default sidebar" role="navigation">
  <div class="sidebar-nav navbar-collapse">
    <ul class="nav" id="side-menu">
      <sec:authorize ifAnyGranted="ROLE_ADD_TASK">
        <li>
          <a href="dossers/"><i class="glyphicon glyphicon-bell"></i> <c:message code="label.addcomplaint"/></a>
        </li>
      </sec:authorize>
      <li>
        <a href="${pageContext.request.contextPath}/dashboard" onclick="$('#page-wrapper').load('${pageContext.request.contextPath}/dashboard'); return false;"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
      </li>
      <li>
        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i><c:message code="label.statistic"/><span class="fa arrow"></span></a>
        <ul class="nav nav-second-level">
          <li>
            <a href="stats/"><c:message code="label.statistic"/></a>
          </li>
          <li>
            <a href="stats/count"><c:message code="label.statistic.buble"/></a>
          </li>
          <li>
            <a href="stats/plot"><c:message code="label.statistic.plot"/></a>
          </li>
        </ul>
        <!-- /.nav-second-level -->
      </li>
      <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT,ROLE_GROUP_INF_VIEW,ROLE_SUPER_INF_VIEW,ROLE_MAIN_INF_VIEW">
        <li>
          <a href="search/"><i class="fa fa-search fa-fw"></i> <c:message code="label.search"/></a>
        </li>
      </sec:authorize>
      <li>
        <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.persons"/><span class="fa arrow"></span></a>
        <ul class="nav nav-second-level">
          <li>
            <a href="person/"><c:message code="label.persons.clients"/></a>
          </li>
          <sec:authorize ifAnyGranted="ROLE_PERSONAL_VIEW,ROLE_PERSONAL_EDIT,ROLE_PERSONAL_ADD">
            <li>
              <a href="person/worker"> <c:message code="label.persons.workers"/></a>
            </li>
          </sec:authorize>
        </ul>
        <!-- /.nav-second-level -->
      </li>
      <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <li>
          <a href=""><i class="fa fa-wrench fa-fw"></i><c:message code="label.administrate"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="journal/"> <c:message code="label.journal"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/users/list"> <c:message code="label.administrate.users"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/roles/list"> <c:message code="label.administrate.roles"/></a>
            </li>
            <li>
              <a href="subdivisions/"> <c:message code="label.administrate.subdivisions"/></a>
            </li>
          </ul>
        </li>
      </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_GLOBAL_INFO_EDIT">
        <li>
          <a href=""><i class="fa fa-sitemap fa-fw"></i><c:message code="label.menu.globaldirectory"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="${pageContext.request.contextPath}/tasktypes/list"> <c:message code="label.menu.globaldirectory.filetype"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/documenttypes/list"> <c:message code="label.menu.globaldirectory.activity"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/contacttypes/list"> <c:message code="label.menu.globaldirectory.contacttype"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/positions/list"> <c:message code="label.menu.globaldirectory.hobbi"/></a>
            </li>
          </ul>
          <!-- /.nav-second-level -->
        </li>
      </sec:authorize>

      <sec:authorize ifAnyGranted="ROLE_GROUP_INF_EDIT,ROLE_SUPER_INF_EDIT,ROLE_MAIN_INF_EDIT">
        <li>
          <a href=""><i class="fa fa-edit fa-fw"></i><c:message code="label.direcotry"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="category/"> <c:message code="label.direcotry.category"/></a>
            </li>
            <li>
              <a href="info/"> <c:message code="label.direcotry.info"/></a>
            </li>
          </ul>
          <!-- /.nav-second-level -->
        </li>
      </sec:authorize>
      <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> <c:message code="label.menu.Logout"/></a>
    </ul>
  </div>
  <!-- /.sidebar-collapse -->
</div>