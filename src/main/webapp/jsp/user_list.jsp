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
        <li><a href="#"><c:message code="label.administrate"/></a></li>
        <li class="active"><c:message code="label.administrate.users"/></li>
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
            url:'${pageContext.request.contextPath}/users/listing',
            editurl:'${pageContext.request.contextPath}/users/edit',
            datatype: 'json',
            mtype: 'POST',
            styleUI : 'Bootstrap',
            colNames:['<c:message code="label.id"/>', '<c:message code="label.usertables.table.col_title.login"/>', '<c:message code="label.usertables.table.col_title.password"/>','<c:message code="label.user.enabled"/>', '<c:message code="label.version"/>'],
            colModel:[
                {name:'userID',index:'userID', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true},
                {name:'userName',index:'userName', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}},
                {name:'passwordHash',index:'passwordHash', width:100, editable:true, editrules:{required:true}, editoptions:{size:10},search:false},
                {name:'enabled',index:'numberFormat', width:100, editable:true, editrules:{required:true}, editoptions:{size:10},search:false},
                {name:'versionId',index:'versionID', width:100, editable:true, editrules:{readonly:true}, editoptions:{size:10,defaultValue:'0'}, hidden:true},
            ],
            rowNum:10,
            rowList:[10,20,40,60],
            height: 240,
            autowidth: true,
            rownumbers: true,
            pager: '#pager',
            sortname: 'userID',
            viewrecords: true,
            sortorder: "asc",
            caption:"<c:message code="label.user.title"/>",
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
                id: "userID"
            },
            subGrid: true,
            subGridRowExpanded: function(subgrid_id, row_id) {
                // we pass two parameters
                // subgrid_id is a id of the div tag created within a table
                // the row_id is the id of the row
                // If we want to pass additional parameters to the url we can use
                // the method getRowData(row_id) - which returns associative array in type name-value
                // here we can easy construct the following
                var subgrid_table_id;
                var subgrid_pager_id;
                subgrid_table_id = subgrid_id+"_t";
                subgrid_pager_id = subgrid_id+"_p"
                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"'></div>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    pager:subgrid_pager_id,
                    url:"${pageContext.request.contextPath}/users/roles?userID="+row_id,
                    editurl:"${pageContext.request.contextPath}/users/editroles?userID="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:600,
                    caption:"<c:message code="label.role.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colNames: ['<c:message code="label.id"/>','<c:message code="label.role.name"/>','<c:message code="label.role.role_id"/>'],
                    colModel: [
                        {name:"id",index:"id",width:80,hidden:true},
                        {name:"name",index:"name",width:130,editable:false},
                        {name:'role_id',editable:true,edittype:'select',editrules:{edithidden:true},editoptions:{dataUrl:"${pageContext.request.contextPath}/users/avaibleroles?userID="+row_id,
                            buildSelect:function(data){
                                var response = $.parseJSON(data);

                                var s = '<select>';

                                if (response && response.length) {
                                    for (var i = 0, l = response.length; i < l; i++) {
                                        s += '<option value="' + response[i]["id"] + '">' + response[i]["name"] + '</option>';
                                    }
                                }

                                return s + "</select>";
                            }
                        },hidden:true}
                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'name',
                    sortorder: "asc"
                });
                $("#"+subgrid_table_id).jqGrid('navGrid','#'+subgrid_pager_id,
                        {edit:false, add:true, del:true, search:false},
                        {/*MOD PARAM*/},
                        {/*ADD PARAM*/
                            closeOnEscape: true,
                            closeAfterAdd: true,
                            serializeEditData:function (data) {
                                if(data.id=="_empty")data.id=null;
                                return data;
                            }
                        }
                );
            }

        });
        $("#grid").jqGrid('navGrid','#pager',
                {edit:true, add:true, del:true, search:false},
                {/*MOD PARAM*/
                    closeAfterEdit: true,
                },
                {/*ADD PARAM*/
                    closeOnEscape: true,
                    closeAfterAdd: true,
                    serializeEditData:function (data) {
                        if(data.id=="_empty")data.id=null;
                        return data;
                    }
                },
                {/*DEL PARAM*/},
                { 	// search
                    sopt:['cn', 'eq', 'ne', 'lt', 'gt', 'bw', 'ew'],
                    closeOnEscape: true,
                    multipleSearch: true,
                    closeAfterSearch: true,
                    multipleGroup:true
                }
        );

        jQuery("#grid").jqGrid('filterToolbar',{stringResult: false,searchOnEnter:true});


    });
</script>
</html>
