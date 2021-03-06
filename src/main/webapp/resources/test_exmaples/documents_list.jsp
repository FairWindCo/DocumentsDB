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
        var documentType_id;
        var counterprty_from_id=1;
        var counterprty_to_id=1;
        var person_from_id;
        var person_to_id;

        var select_obj={};
        var GridData={documentType_key:function(){return select_obj.documentType_name_id},
            counterprty_to_id:function(){return counterprty_to_id},
            counterprty_from_id:function(){return counterprty_from_id},
            person_from_id:function(){return person_from_id},
            person_to_id:function(){return person_to_id}
        };
        $("#grid").jqGrid({
            url:'${pageContext.request.contextPath}/documents/listing',
            editurl:'${pageContext.request.contextPath}/documents/edit',
            datatype: 'json',
            mtype: 'POST',
            styleUI : 'Bootstrap',
            //colNames:[, , ,'<c:message code=""/>','<c:message code="label.documents.table.col_title.counterpart_from"/>','<c:message code="label.documents.table.col_title.person_from"/>','<c:message code="label.documents.table.col_title.counterpart_to"/>','<c:message code="label.documents.table.col_title.person_to"/>', '<c:message code="label.documents.table.col_title.description"/>','<c:message code="label.version"/>'],
            colModel:[
                {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true, label:'<c:message code="label.id"/>'},
                {name:'number',index:'number', width:100, editable:true, editrules:{required:true}, editoptions:{size:10},label:'<c:message code="label.documents.table.col_title.number"/>'},
                {name:'name',index:'name', width:100, editable:true, editrules:{required:false}, editoptions:{size:10},search:false,label:'<c:message code="label.documents.table.col_title.name"/>'},
                fairwind_select_column('documentType_name','/documenttypes/showList','<c:message code="label.documents.table.col_title.document_type"/>',select_obj),
                {name:'documentType_name', width:100, editable:true, editrules:{edithidden:true,required:false},jsonmap:'documentType',search:false,editoptions:{
                        /**/
                        dataInit : function (elem) {
                            var value_elem=$(elem).val();
                            $(elem).wrap("<div></div>");
                            $(elem).width='80px';
                            $(elem).ajaxComboBox('${pageContext.request.contextPath}/documenttypes/showList',
                                    {lang: 'en',
                                        db_table: 'nation',
                                        per_page: 20,
                                        navi_num: 10,
                                        select_only: true,
                                        primary_key: 'id',
                                        show_field: 'name',
                                        field:'name',
                                        //recalc_width:false,
                                        button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
                                        init_record: [value_elem],
                                        bind_to:'personIDkey_setup',
                                    }).bind('personIDkey_setup', function() {
                                        //$('#documentType_key').val($('#documentType_name_primary_key').val());
                                        documentType_id=$('#documentType_name_primary_key').val();
                                    });
                        }/**/
                        },formatter:function(cellvalue, options, rowObject ){
                            if(cellvalue===null || cellvalue===undefined)return '';
                            return '<p pkey='+cellvalue.id+'>'+cellvalue.name+'</p>'
                        },unformat:function(cellvalue, options, cellObject ){
                            var element=$(cellObject).html();
                            return $(element).attr('pkey');
                        }
                    },
                {name:'from_counterperty', width:100, editable:true, editrules:{edithidden:true,required:false},jsonmap:'counterparty_from.shortName',search:false,editoptions:{
                    /**/
                    dataInit : function (elem) {
                        var value_elem=$(elem).val();
                        $(elem).wrap("<div></div>");
                        if(value_elem===null || value_elem===undefined || !(value_elem)){
                            value_elem=counterprty_from_id;
                        }
                        //counterprty_from_id=value_elem;
                        $(elem).width='80px';
                        $(elem).ajaxComboBox('${pageContext.request.contextPath}/counterparts/showList',
                                {lang: 'en',
                                    db_table: 'nation',
                                    per_page: 20,
                                    navi_num: 10,
                                    select_only: true,
                                    primary_key: 'id',
                                    show_field: 'shortName',
                                    field:'shortName',
                                    //recalc_width:false,
                                    button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
                                    init_record: [value_elem],
                                    bind_to:'key_setup',
                                }).bind('key_setup', function() {
                                    counterprty_from_id=$('#from_counterperty_primary_key').val();
                                });
                    }/**/
                },formatter:function(cellvalue, options, rowObject ){
                    if(cellvalue===null || cellvalue===undefined)return '';
                    return '<p pkey='+cellvalue.id+'>'+cellvalue.shortName+'</p>'
                },unformat:function(cellvalue, options, cellObject ){
                    var element=$(cellObject).html();
                    return $(element).attr('pkey');
                }
                },
                {name:'from_person', width:100, editable:true, editrules:{edithidden:true,required:false},jsonmap:'person_from.surname',search:false,editoptions:{
                    /**/
                    dataInit : function (elem) {
                        var value_elem=$(elem).val();
                        person_from_id=value_elem;
                        $(elem).wrap("<div></div>");
                        $(elem).width='80px';
                        $(elem).ajaxComboBox('${pageContext.request.contextPath}/persons/showList',
                                {lang: 'en',
                                    db_table: 'nation',
                                    per_page: 20,
                                    navi_num: 10,
                                    postData:{'firmID':function(){return counterprty_from_id}},
                                    select_only: true,
                                    primary_key: 'id',
                                    show_field: 'surname',
                                    field:'surname',
                                    //recalc_width:false,
                                    button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
                                    init_record: [value_elem],
                                    bind_to:'key_setup',
                                }).bind('key_setup', function() {
                                    person_from_id=$('#from_person_primary_key').val();
                                });
                    }/**/
                },formatter:function(cellvalue, options, rowObject ){
                    if(cellvalue===null || cellvalue===undefined)return '';
                    return '<p pkey='+cellvalue.id+'>'+cellvalue.surname+'</p>'
                },unformat:function(cellvalue, options, cellObject ){
                    var element=$(cellObject).html();
                    return $(element).attr('pkey');
                }
                },
                {name:'to_counterperty', width:100, editable:true, editrules:{edithidden:true,required:false},jsonmap:'counterparty_from.shortName',search:false,editoptions:{
                    /**/
                    dataInit : function (elem) {
                        var value_elem=$(elem).val();
                        $(elem).wrap("<div></div>");
                        $(elem).width='80px';
                        //counterprty_to_id=value_elem;
                        if(value_elem===null || value_elem===undefined || !(value_elem)){
                            value_elem=counterprty_to_id;
                        }
                        $(elem).ajaxComboBox('${pageContext.request.contextPath}/counterparts/showList',
                                {lang: 'en',
                                    db_table: 'nation',
                                    per_page: 20,
                                    navi_num: 10,
                                    select_only: true,
                                    primary_key: 'id',
                                    show_field: 'shortName',
                                    field:'shortName',
                                    //recalc_width:false,
                                    button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
                                    init_record: [value_elem],
                                    bind_to:'key_setup',
                                }).bind('key_setup', function() {
                                    counterprty_to_id=$('#to_counterperty_primary_key').val();
                                });
                    }/**/
                },formatter:function(cellvalue, options, rowObject ){
                    if(cellvalue===null || cellvalue===undefined)return '';
                    return '<p pkey='+cellvalue.id+'>'+cellvalue.shortName+'</p>'
                },unformat:function(cellvalue, options, cellObject ){
                    var element=$(cellObject).html();
                    return $(element).attr('pkey');
                }
                },
                {name:'to_person', width:100, editable:true, editrules:{edithidden:true,required:false},jsonmap:'person_from.surname',search:false,editoptions:{
                    /**/
                    dataInit : function (elem) {
                        var value_elem=$(elem).val();
                        $(elem).wrap("<div></div>");
                        $(elem).width='80px';
                        person_to_id=value_elem;
                        $(elem).ajaxComboBox('${pageContext.request.contextPath}/persons/showList',
                                {lang: 'en',
                                    db_table: 'nation',
                                    per_page: 20,
                                    navi_num: 10,
                                    postData:{'firmID':function(){return counterprty_to_id}},
                                    select_only: true,
                                    primary_key: 'id',
                                    show_field: 'surname',
                                    field:'surname',
                                    //recalc_width:false,
                                    button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
                                    init_record: [value_elem],
                                    bind_to:'key_setup',
                                }).bind('key_setup', function() {
                                    person_to_id=$('#from_person_primary_key').val();
                                });
                    }/**/
                },formatter:function(cellvalue, options, rowObject ){
                    if(cellvalue===null || cellvalue===undefined)return '';
                    return '<p pkey='+cellvalue.id+'>'+cellvalue.surname+'</p>'
                },unformat:function(cellvalue, options, cellObject ){
                    var element=$(cellObject).html();
                    return $(element).attr('pkey');
                }
                },
                {name:'description',index:'description', width:100, editable:true, editrules:{required:false}, search:false,edittype:'textarea'},
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
                    pager:subgrid_pager_sec_id,
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
                jQuery("#"+subgrid_table_sec_id).jqGrid({
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
                $("#"+subgrid_table_sec_id).jqGrid('navGrid','#'+subgrid_pager_id,
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

                //список лиц на утверждение
                jQuery("#"+subgrid_table_scr_id).jqGrid({
                    pager:subgrid_pager_scr_id,
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
                $("#"+subgrid_table_scr_id).jqGrid('navGrid','#'+subgrid_pager_scr_id,
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
