<html>
<head>
    <title></title>
    <%-- JQuery --%>
    <%@include file="/include/jquery_include.jsp" %>
    <!-- Bootstrap Core JavaScript -->
    <%@include file="/include/bootstrup_include.jsp" %>
    <%-- JQGrid --%>
    <%@include file="/include/jgrid_include.jsp" %>
    <%-- jquery.ajax-combobox --%>
    <%@include file="/include/select_include.jsp" %>

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
        <li><a href="#"><c:message code="label.counterparts"/></a></li>
        <li class="active"><c:message code="label.counterparts.counterparts"/></li>
    </ol>
    <div class="row">

        <div class="col-lg-12">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><c:message code="label.counterparts.title"/></h3>
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

        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {

        $("#grid").jqGrid({
            url:'${pageContext.request.contextPath}/counterparts/listing',
            editurl:'${pageContext.request.contextPath}/counterparts/edit',
            datatype: 'json',
            mtype: 'POST',
            styleUI : 'Bootstrap',
            colNames:['<c:message code="label.id"/>', '<c:message code="label.counterparts.table.col_title.name"/>', '<c:message code="label.counterparts.table.col_title.fullname"/>','<c:message code="label.version"/>'],
            colModel:[
                {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true},
                {name:'shortName',index:'shortName', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}},
                {name:'fullName',index:'fullName', width:100, editable:true, editrules:{required:false}, editoptions:{size:10},search:false},
                {name:'version',index:'version', width:100, editable:true, editrules:{readonly:true}, editoptions:{size:10,defaultValue:'0'}, hidden:true},
            ],
            rowNum:10,
            rowList:[10,20,40,60],
            height: 240,
            autowidth: true,
            rownumbers: true,
            pager: '#pager',
            sortname: 'id',
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
                id: "id"
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
                tree_table_id = subgrid_id+"_tree";
                tree_pager_id = subgrid_id+"_tp"
                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"'></div><table id='"+tree_table_id+"' class='scroll'></table><div id='"+tree_pager_id+"'></div>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    pager:subgrid_pager_id,
                    url:"${pageContext.request.contextPath}/counterparts/agreements?ID="+row_id,
                    editurl:"${pageContext.request.contextPath}/counterparts/editagreements?ID="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.counterparts.agreements.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colNames: ['<c:message code="label.id"/>','<c:message code="label.agreements.namber"/>','<c:message code="label.agreements.name"/>','<c:message code="label.agreements.startdate"/>','<c:message code="label.agreements.planendDate"/>','<c:message code="label.agreements.endDate"/>'],
                    colModel: [
                        {name:"id",index:"id",width:80,hidden:true},
                        {name:"number",index:"number",width:80,editable:true},
                        {name:"name",index:"name",width:130,editable:true},
                        {name:"startDate",index:"startDate",width:130,editable:true,editrules:{date:true},editoptions:{dataInit:function(el){
                                    $(el).datepicker({dateFormat:'dd-mm-yy'});
                                }
                        },datefmt:'dd-mm-yyyy'},
                        {name:"planEndDate",index:"planEndDate",width:130,editable:true, editrules:{date:true},editoptions:{dataInit:function(el){
                                    $(el).datepicker({dateFormat:'dd-mm-yy'});
                                }
                        },datefmt:'dd-mm-yyyy'},
                        {name:"endDate",index:"endDate",width:130,editable:true,editrules:{date:true},editoptions:{dataInit:function(el){
                                    $(el).datepicker({dateFormat:'dd-mm-yy'});
                                }
                        },datefmt:'dd-mm-yyyy'},

                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'name',
                    sortorder: "asc"
                });
                $("#"+subgrid_table_id).jqGrid('navGrid','#'+subgrid_pager_id,
                        {edit:true, add:true, del:true, search:false},
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


                jQuery("#"+tree_table_id).jqGrid({
                    pager:tree_pager_id,
                    url:"${pageContext.request.contextPath}/counterparts/persons?ID="+row_id,
                    editurl:"${pageContext.request.contextPath}/counterparts/editpersons?ID="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.counterparts.persons.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    /**/
                    colNames: ['<c:message code="label.id"/>','<c:message code="label.persons.surname"/>','<c:message code="label.persons.firstName"/>','<c:message code="label.persons.middleName"/>','<c:message code="label.persons.date_of_birth"/>','<c:message code="label.persons.head"/>','<c:message code="label.persons.head"/>','<c:message code="label.persons.head"/>'],
                    colModel: [
                        {name:"id",index:"id",width:80,hidden:true,key:true},
                        {name:"surname",index:"surname",width:100,editable:true},
                        {name:"firstName",index:"firstName",width:100,editable:true},
                        {name:"middleName",index:"middleName",width:100,editable:true},
                        {name:"date_of_birth",width:50,editable:true,editrules:{date:true},editoptions:{dataInit:function(el){
                            $(el).datepicker({dateFormat:'dd-mm-yy'});
                        }
                        },datefmt:'dd-mm-yyyy'},
                        {name:"head.surname",index:"head.surname",width:80,hidden:false,editable:false},
                        {name:"headID_primary_key",width:80,hidden:false,editable:true},
                        {name:"head.id",hidden:true,width:100,editrules:{edithidden:true},editable:true,editoptions:{
                            /**/
                            dataInit : function (elem) {
                                var value_elem=$(elem).val();
                                $(elem).wrap("<div></div>");
                                $(elem).width='80px';
                                $(elem).ajaxComboBox('${pageContext.request.contextPath}/persons/showList?firmID='+row_id,
                                    {lang: 'en',
                                        db_table: 'nation',
                                        per_page: 20,
                                        navi_num: 10,
                                        select_only: true,
                                        primary_key: 'id',
                                        show_field: 'surname,middleName,firstName',
                                        field:'surname',
                                        //recalc_width:false,
                                        button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
                                        init_record: [value_elem],
                                        sub_info: true,
                                        bind_to:'personIDkey_setup',
                                        /**/
                                        sub_as: {
                                            surname: 'surname',
                                            middleName: 'middleName',
                                            firstName:'firstName'
                                        }/**/
                                    }).bind('personIDkey_setup', function() {
                                            $('#headID_primary_key').val($('#head.id_primary_key').val());
                                    });
                            }/**/
                        }},
                    ],/*
                    colModel: [
                        {name:"id",index:"id",width:80,hidden:true,key:true},
                        {name:"surname",index:"surname",width:100},
                        {name:"parentId",index:"parentId",width:80,hidden:true},
                    ],/**/
                    rowNum:20,
                    "hoverrows":false,
                    "viewrecords":false,
                    "gridview":true,
                    "height":"auto",
                    "scrollrows":true,
                    "loadonce":false,
                    "treeGrid":true,
                    "ExpandColumn":"surname",
                    "treedatatype":"json",
                    "treeGridModel":"adjacency",
                    "treeReader":{
                        "parent_id_field":"parentId",
                        "level_field":"level",
                        "leaf_field":"leaf",
                        "expanded_field":"expanded",
                        "loaded":"loaded",
                    },
                });
                /**/
                $("#"+tree_table_id).jqGrid('navGrid','#'+tree_pager_id,
                        {edit:true, add:true, del:true, search:false},
                        {},
                        {
                            closeOnEscape: true,
                            closeAfterAdd: true,
                            serializeEditData:function (data) {
                                if(data.id=="_empty")data.id=null;
                                return data;
                            }
                        }
                );/**/
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

/*
        jQuery("#test").ajaxComboBox('${pageContext.request.contextPath}/persons/showList?firmID=1',
                {lang: 'en',
                    db_table: 'nation',
                    per_page: 20,
                    navi_num: 10,
                    sub_info: true,
                    select_only: true,
                    primary_key: 'id',
                    field:'surname',
                    show_field: 'surname',
                    search_field: 'surname',
                    button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
                    init_record: [1],
                    sub_as: {
                        surname: 'surname',
                        middleName: 'middleName',
                        firstName:'firstName'
                    }
                });
/**/
    });
</script>
</html>
