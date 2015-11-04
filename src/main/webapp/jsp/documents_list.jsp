<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
    <%-- JQuery --%>
    <%@include file="/include/jquery_include.jsp" %>
    <!-- Bootstrap Core JavaScript -->
    <%@include file="/include/bootstrup_include.jsp" %>
    <%-- JQGrid --%>
    <%@include file="/include/jgrid_include_ex.jsp" %>
    <%-- jquery.ajax-combobox --%>
    <%@include file="/include/select_include.jsp" %>
    <%@include file="/include/te_include.jsp" %>

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
        <li><a href="#"><c:message code="label.documents"/></a></li>
    </ol>
    <div class="row">

        <div class="col-lg-12">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><c:message code="label.document.title"/></h3>
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
        var select_obj={};
        var GridData={
            documentType_key:function(){return select_obj.documentType_name_id},
            counterprty_id:function(){return select_obj.counterprty_id},
            person_id:function(){return select_obj.person_id},
            agreement_id:function(){return select_obj.agreement_id}
        };
        $("#grid").jqGrid({
            url:'${pageContext.request.contextPath}/documents/listing',
            editurl:'${pageContext.request.contextPath}/documents/edit',
            datatype: 'json',
            mtype: 'POST',
            styleUI : 'Bootstrap',
            colModel:[
                {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true, label:'<c:message code="label.id"/>'},
                {name:'number',index:'number', width:100, editable:true, editrules:{required:true}, editoptions:{size:10},label:'<c:message code="label.documents.table.col_title.number"/>'},
                {name:'name',index:'name', width:100, editable:true, editrules:{required:false}, editoptions:{size:10},search:false,label:'<c:message code="label.documents.table.col_title.name"/>'},
                {name:'class',width:30, editable:true, search:false,edittype:'select',
                    editrules:{required:true},
                    label:'<c:message code="label.documents.table.col_title.class"/>',jsonmap:'documentType.documentClass',
                    editoptions:{value:{
                        0:'<c:message code="label.INTERNAL"/>',
                        1:'<c:message code="label.IN"/>',
                        2:'<c:message code="label.OUT"/>',
                    },
                        dataEvents: [
                            { type: 'click', data: { i: 7 }, fn: function(e) {
                                    select_obj.class=e.currentTarget.value;
                                }
                            },
                        ]
                    }
                },
                fairwind_select_column('documentType','/documenttypes/showTypedList','<c:message code="label.documents.table.col_title.document_type"/>',select_obj,{
                    post_parameter_name:'class',
                    master_select_element:'class',
                    select_params:{
                        parameter_name:'documentType_name_class'
                    }
                }),
                fairwind_select_column('counterparty','/counterparts/showList','<c:message code="label.documents.table.col_title.counterpart"/>',select_obj,{
                    show_field: 'shortName',
                    select_params:{
                        parameter_name:'counterprty_id'
                    }
                }),
                fairwind_select_column('agreement','/counterparts/showListAgreements','<c:message code="label.documents.table.col_title.agreement"/>',select_obj,{
                    post_parameter_name:'counterpart_id',
                    master_select_element:'counterprty_id',
                    select_params:{
                        parameter_name:'agreement_id'
                    }
                }),
                fairwind_select_column('person','/persons/showList','<c:message code="label.documents.table.col_title.person"/>',select_obj,{
                    show_field: 'fio',
                    post_parameter_name:'firmID',
                    master_select_element:'counterprty_id',
                    select_params:{
                        parameter_name:'person_id'
                    }
                }),
                {name:'description',index:'description', width:100, editable:true, editrules:{required:false}, search:false,edittype:'textarea',
                    editoptions: {rows:"5",cols:"20",dataInit:function(el){
                            $(el).jqte();
                        },
                    }
                },
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
                var subgrid_table_sec_id;
                var subgrid_pager_sec_id;
                var subgrid_table_scr_id;
                var subgrid_pager_scr_id;
                subgrid_table_id = subgrid_id+"_t";
                subgrid_pager_id = subgrid_id+"_p"
                subgrid_table_sec_id = subgrid_id+"_t_sec";
                subgrid_pager_sec_id = subgrid_id+"_p_sec"
                subgrid_table_scr_id = subgrid_id+"_t_scr";
                subgrid_pager_scr_id = subgrid_id+"_p_scr"

                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"'></div>" +
                        "                    <table id='"+subgrid_table_sec_id+"' class='scroll'></table><div id='"+subgrid_pager_sec_id+"'></div>" +
                        "                    <table id='"+subgrid_table_scr_id+"' class='scroll'></table><div id='"+subgrid_pager_scr_id+"'></div>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    pager:subgrid_pager_id,
                    url:"${pageContext.request.contextPath}/documents/file_listing?document_id="+row_id,
                    editurl:null,
                    fileurl:"${pageContext.request.contextPath}/documents/editafile?document_id="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.documents.files.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colModel: [
                        {name:"id",index:"id",width:20,hidden:true,label:'<c:message code="label.id"/>'},
                        {name:"fileName",index:"fileName",width:180,editable:false,label:'<c:message code="label.documents.files.filename"/>'},
                        {name:"mimeType",index:"mimeType",width:70,editable:false,search:false,label:'<c:message code="label.documents.files.fileType"/>'},
                        {name:"size",index:"size",width:50,editable:false,search:false,label:'<c:message code="label.documents.files.fileSize"/>'},
                        {name:"creationDate",index:"startDate",width:70,editable:false,datefmt:'dd-mm-yyyy',label:'<c:message code="label.documents.files.creationDate"/>'},
                        {name:"version",index:"version",width:20,hidden:true,label:'<c:message code="label.version"/>'},
                        {name:"file",index:"file",width:50,label:'<c:message code="label.documents.files.file"/>',editable:true,edittype:'file'},
                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'fileName',
                    dataProxy:$.jgrid.ext.ajaxFormProxy, //our charming dataProxy ^__^
                    sortorder: "asc"
                }).jqGrid('extBindEvents');
                $("#"+subgrid_table_id).jqGrid('navGrid','#'+subgrid_pager_sec_id,
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

                //Таблица дескриптор безопасности
                var select_sec_obj={};
                var GridDataSec={
                    person_id:function(){return select_sec_obj.person_id},
                };
                jQuery("#"+subgrid_table_sec_id).jqGrid({
                    pager:subgrid_pager_sec_id,
                    url:"${pageContext.request.contextPath}/documents/security_listing?document_id="+row_id,
                    editurl:null,
                    fileurl:"${pageContext.request.contextPath}/documents/editSecurity?documentID="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.documents.security.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colModel: [
                        {name:"id",index:"id",width:20,hidden:true,label:'<c:message code="label.id"/>'},
                        {name:'action',width:30, editable:true, search:false,edittype:'select',
                            editrules:{required:true},
                            label:'<c:message code="label.documents.table.col_title.class"/>',jsonmap:'documentType.documentClass',
                            editoptions:{
                                value:{
                                    0:'<c:message code="label.ALL_ACTION"/>',
                                    1:'<c:message code="label.VIEW_ACTION"/>',
                                    2:'<c:message code="label.EDIT_ACTION"/>',
                                    3:'<c:message code="label.DELETE_ACTION"/>',
                                },
                            }
                        },
                        {name:'permission',width:30, editable:true, search:false,edittype:'select',
                            editrules:{required:true},
                            label:'<c:message code="label.documents.table.col_title.class"/>',jsonmap:'documentType.documentClass',
                            editoptions:{
                                value:{
                                    0:'<c:message code="label.RESTRICT"/>',
                                    1:'<c:message code="label.PERMIT"/>',
                                },
                            }
                        },
                        fairwind_select_column('person','/persons/showList','<c:message code="label.documents.table.col_title.person"/>',select_sec_obj,{
                            show_field: 'fio',
                            select_params:{
                                parameter_name:'person_id'
                            }
                        }),
                        {name:"version",index:"version",width:20,hidden:true,label:'<c:message code="label.version"/>'},
                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'fileName',
                    dataProxy:$.jgrid.ext.ajaxFormProxy, //our charming dataProxy ^__^
                    sortorder: "asc"
                }).jqGrid('extBindEvents');
                $("#"+subgrid_table_sec_id).jqGrid('navGrid','#'+subgrid_pager_sec_id,
                        {edit:true, add:true, del:true, search:false},
                        {/*MOD PARAM*/
                            editData:GridDataSec,},
                        {/*ADD PARAM*/
                            editData:GridDataSec,
                            closeOnEscape: true,
                            closeAfterAdd: true,
                            serializeEditData:function (data) {
                                if(data.id=="_empty")data.id=null;
                                return data;
                            }
                        }
                );

                //список лиц на утверждение
                var select_sub_obj={};
                var GridDataSub={
                    person_id:function(){return select_sub_obj.person_id},
                };
                jQuery("#"+subgrid_table_scr_id).jqGrid({
                    pager:subgrid_pager_scr_id,
                    url:"${pageContext.request.contextPath}/documents/subscriber_listing?document_id="+row_id,
                    editurl:null,
                    fileurl:"${pageContext.request.contextPath}/documents/editSubscribe?documentID="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.documents.subscriber.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colModel: [
                        {name:"id",index:"id",width:20,hidden:true,label:'<c:message code="label.id"/>'},
                        fairwind_select_column('person','/persons/showList','<c:message code="label.documents.table.col_title.person"/>',select_sub_obj,{
                            show_field: 'fio',
                            select_params:{
                                parameter_name:'person_id'
                            }
                        }),
                        {name:"version",index:"version",width:20,hidden:true,label:'<c:message code="label.version"/>'},

                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'fileName',
                    dataProxy:$.jgrid.ext.ajaxFormProxy, //our charming dataProxy ^__^
                    sortorder: "asc"
                }).jqGrid('extBindEvents');
                $("#"+subgrid_table_scr_id).jqGrid('navGrid','#'+subgrid_pager_scr_id,
                        {edit:true, add:true, del:false, search:false},
                        {/*MOD PARAM*/
                            editData:GridDataSub,},
                        {/*ADD PARAM*/
                            editData:GridDataSub,
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
                {
                    <sec:authorize ifAnyGranted="ROLE_ADD_DOCUMENTS">
                    edit:true,
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_EDIT_DOCUMENTS">
                    add:true,
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="ROLE_DELETE_DOCUMENTS">
                    del:true,
                    </sec:authorize>
                    search:false},
                {/*MOD PARAM*/
                    editData:GridData,
                    closeAfterEdit: true,
                },
                {/*ADD PARAM*/
                    editData:GridData,
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
