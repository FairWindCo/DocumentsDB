<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 02.11.2015
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>

      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title"><c:message code="label.messages.view"/></h3>
        </div>
        <div class="panel-body">
          <div>
            <table id="grid"></table>
            <div id="pager"></div>
            <div>
            </div>
          </div>
        </div>
      </div>
<%@include file="/base_element/validate_elements.jsp" %>
<script type="text/javascript">
  $(document).ready(function () {
    $("#grid").jqGrid({
      url:'${pageContext.request.contextPath}/messages/actual_for_me_listing',
      datatype: 'json',
      mtype: 'POST',
      styleUI : 'Bootstrap',
      colModel:[
        {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
        {name:'userName',width:50, editable:false, editrules:{required:false}, search:false,sort:false,label:'<c:message code="label.messages.creationUser"/>'},
        {name:'messageText',index:'comments', width:200, editable:true, editrules:{required:true},search:false,sort:false, edittype:'textarea',label:'<c:message code="label.messages.text"/>'},
        {name:'actual',index:'actual', width:40, editable:true,search:false, label:'<c:message code="label.messages.actualDate"/>',formatter:'date',
          formatoptions:{
            srcformat:'d.m.Y H:i:s',
            newformat:'d.m.Y H:i:s',
            defaultValue:null
          }
        },
        {name:'creationDate',editable:false,width:40,search:false, label:'<c:message code="label.messages.creationDate"/>',formatter:'date',
          formatoptions:{
            srcformat:'d.m.Y H:i:s',
            newformat:'d.m.Y H:i:s'
          }
        },
        {name: "act", width: 20,label:'&nbsp', editable:false,search:false,
          formatter:fairwind_validate_create('/messages/validate',{
            postfield:'messgeId'
          }),
        },
      ],
      rowNum:10,
      rowList:[10,20,40,60],
      height: 240,
      autowidth: true,
      rownumbers: true,
      pager: '#pager',
      sortname: 'creationDate',
      viewrecords: true,
      sortorder: "desc",
      caption:"<c:message code="label.messages.view"/>",
      emptyrecords: "<c:message code="label.emptyrecords"/>",
      loadonce: false,
      loadComplete: function() {},
      jsonReader : {
        root: "rows",
        page: "page",
        total: "total",
        records: "records",
        repeatitems: false,
        cell: "cell",
        id: "id"
      },
    });
    $("#grid").jqGrid('navGrid','#pager',
            {
              edit:false,
              add:false,
              del:false,
              search:false
            }
    );

    jQuery("#grid").jqGrid('filterToolbar',{stringResult: false,searchOnEnter:true});

  });
</script>