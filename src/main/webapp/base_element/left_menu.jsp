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
      <li>
        <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.message"/><span class="fa arrow"></span></a>
        <ul class="nav nav-second-level">
          <li>
            <a href="${pageContext.request.contextPath}/messages/actual"><c:message code="label.message.actual_for_me"/></a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/messages/for_me"><c:message code="label.message.for_me"/></a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/messages/my"><c:message code="label.message.my"/></a>
          </li>
          <sec:authorize ifAnyGranted="ROLE_MESSAGE_ADMINISTRATOR">
            <li>
              <a href="${pageContext.request.contextPath}/messages/list"><c:message code="label.message.administrator"/></a>
            </li>
          </sec:authorize>
        </ul>
        <!-- /.nav-second-level -->
      </li>
      <li>
        <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.tasks"/><span class="fa arrow"></span></a>
        <ul class="nav nav-second-level">
          <li>
            <a href="${pageContext.request.contextPath}/task/list"><c:message code="label.tasks.task"/></a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/tasks/controlledTask"><c:message code="label.tasks.controltask"/></a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/task/active"><c:message code="label.tasks.activetask"/></a>
          </li>
          <sec:authorize ifAnyGranted="ROLE_TASK_CREATE">
            <li>
              <a href="${pageContext.request.contextPath}/task/control"><c:message code="label.tasks.control"/></a>
            </li>
          </sec:authorize>
        </ul>
        <!-- /.nav-second-level -->
      </li>
      <sec:authorize ifAnyGranted="ROLE_VIEW_DOCUMENTS, ROLE_EDIT_DOCUMENTS, ROLE_ADD_DOCUMENTS, ROLE_DELETE_DOCUMENTS">
        <li>
          <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.documents"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="${pageContext.request.contextPath}/documents/list/"><c:message code="label.documents.document"/></a>
            </li>
          </ul>
          <!-- /.nav-second-level -->
        </li>
      </sec:authorize>
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

      <sec:authorize ifAnyGranted="ROLE_VIEW_DOCUMENTS">
        <li>
          <a href="search/"><i class="fa fa-search fa-fw"></i> <c:message code="label.search"/></a>
        </li>
        <sec:authorize ifAnyGranted="ROLE_PRODUCT_ADD, ROLE_REPAIR_ADD, ROLE_REPAIR_MANAGE, ROLE_PRODUCT_VIEW">
          <li>
            <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.products"/><span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
              <li>
                <a href="${pageContext.request.contextPath}/documents/list/"><c:message code="label.products.view"/></a>
              </li>
              <sec:authorize ifAnyGranted="ROLE_REPAIR_ADD, ROLE_REPAIR_MANAGE">
              <li>
                <a href="${pageContext.request.contextPath}/documents/list/"><c:message code="label.products.repair"/></a>
              </li>
              </sec:authorize>
            </ul>
            <!-- /.nav-second-level -->
          </li>
        </sec:authorize>
      </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_REQUEST_VIEW, ROLE_REQUEST_EDIT">
        <li>
          <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.requests"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="${pageContext.request.contextPath}/requests/"><c:message code="label.requests.view"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/requests/list_production"><c:message code="label.requests.operation.production"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/requests/list_purchase"><c:message code="label.requests.operation.purchase"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/requests/list_shipment"><c:message code="label.requests.operation.shipment"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/requests/list_repair"><c:message code="label.requests.operation.reparir"/></a>
            </li>
          </ul>
          <!-- /.nav-second-level -->
        </li>
      </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_STOREHOUSE, ROLE_STOREHOUSE_MASTER, ROLE_STOREHOUSE_VIEW">
        <li>
          <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.storehouse"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="${pageContext.request.contextPath}/storehouses/list"><c:message code="label.storehouse.view"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/storehouse_operation/list"><c:message code="label.storehouse.operation"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/storehouse_operation/list_arrival"><c:message code="label.storehouse.operation.ARRIVAL"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/storehouse_operation/list_shipment"><c:message code="label.storehouse.operation.SHIPMENT"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/storehouse_operation/list_move"><c:message code="label.storehouse.operation.MOVE"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/storehouse_operation/list_utilization"><c:message code="label.storehouse.operation.UTILIZATION"/></a>
            </li>
          </ul>
          <!-- /.nav-second-level -->
        </li>
      </sec:authorize>
      <li>
        <a href="#"><i class="fa fa-inbox fa-fw"></i><c:message code="label.organizational_elements"/><span class="fa arrow"></span></a>
        <ul class="nav nav-second-level">
          <li>
            <a href="${pageContext.request.contextPath}/nomenclature/list/"><c:message code="label.nomenclature.title"/></a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/nomenclaturetypes/list/"><c:message code="label.nomenclaturetype.title"/></a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/counterparts/list/"><c:message code="label.counterparts.title"/></a>
          </li>
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
          </ul>
        </li>
      </sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_DIRECTORY">
        <li>
          <a href=""><i class="fa fa-sitemap fa-fw"></i><c:message code="label.menu.globaldirectory"/><span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li>
              <a href="${pageContext.request.contextPath}/tasktypes/list"> <c:message code="label.menu.globaldirectory.tasktypes"></c:message></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/documenttypes/list"> <c:message code="label.menu.globaldirectory.documentstypes"></c:message></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/contacttypes/list"> <c:message code="label.menu.globaldirectory.contacttype"/></a>
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/positions/list"> <c:message code="label.menu.globaldirectory.positions"/></a>
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