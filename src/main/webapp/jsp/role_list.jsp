<html>
<head>
    <title></title>
    <%-- JQuery --%>
    <%@include file="/include/jquery_include.jsp" %>
    <!-- Bootstrap Core JavaScript -->
    <%@include file="/include/bootstrup_include.jsp" %>
    <%@include file="/include/jgrid_include.jsp" %>

</head>
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
        <li><a href="#"><c:message code="label.globaldirectory"/></a></li>
        <li class="active"><c:message code="label.globaldirectory.relativies"/></li>
    </ol>
    <div class="row">

        <div class="col-lg-12">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><c:message code="label.globaldirectory.relativies.title"/></h3>
                </div>
                <div class="panel-body">
                    <div>
                        <table id="grid"></table>
                        <div id="pager"></div>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {

        $("#grid").jqGrid({
            url:"${pageContext.request.contextPath}/roles/listing",
            datatype: "json",
            mtype: 'POST',
            width:600,
            caption:"<c:message code="label.role.title"/>",
            emptyrecords: "<c:message code="label.emptyrecords"/>",
            styleUI : 'Bootstrap',
            colNames: ['<c:message code="label.id"/>','<c:message code="label.role.name"/>'],
            colModel: [
                {name:"id",index:"id",width:80,hidden:true},
                {name:"name",index:"name",width:130,editable:false},
            ],
            id: "id",
            rowNum:10,
            rowList:[10,20,40,60],
            height: 240,
            autowidth: true,
            rownumbers: true,
            pager: '#pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption:"Records",
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
            }
        });
        $("#grid").jqGrid('navGrid','#pager',
                {edit:false, add:false, del:false, search:false}
        );

        jQuery("#grid").jqGrid('filterToolbar',{stringResult: false,searchOnEnter:true});


    });
</script>
</html>
