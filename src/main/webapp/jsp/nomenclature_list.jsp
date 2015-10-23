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
        $("#grid").jqGrid({
            url:'${pageContext.request.contextPath}/nomenclature/listing',
            editurl:'${pageContext.request.contextPath}/nomenclature/edit',
            datatype: 'json',
            mtype: 'POST',
            styleUI : 'Bootstrap',
            colModel:[
                {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
                {name:'code',index:'code', width:100, editable:true, editrules:{required:true},search:true,label:'<c:message code="label.nomenclature.code"/>'},
                {name:'name',index:'name', width:100, editable:true, editrules:{required:true},search:true,label:'<c:message code="label.nomenclature.name"/>'},
                {name:'manufacturer',index:'manufacturer', width:100, editable:true, editrules:{required:false}, search:false,label:'<c:message code="label.nomenclature.manufacturer"/>'},
                {name:'country',index:'country', width:100, editable:true, editrules:{required:false}, search:false,label:'<c:message code="label.nomenclature.country"/>'},
                {name:'description',index:'description', width:100, editable:true, editrules:{required:false}, search:false,edittype:'textarea',label:'<c:message code="label.nomenclature.description"/>'},
                {name:'version',index:'version', width:100, editable:true, editrules:{readonly:true}, editoptions:{defaultValue:'0'}, hidden:true,label:'<c:message code="label.version"/>'},
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
                var subgrid_table_type_id;
                var subgrid_pager_types_id;

                var nomenclature_id;
                var nomenclature_type_id;
                var nomenclature_type_id2;

                var GridData={nomenclature_id:function(){return nomenclature_id},
                    nomenclature_type_id:function(){return nomenclature_type_id},
                };
                var typesData={
                    nomenclature_type_id:function(){return nomenclature_type_id2},
                };
                subgrid_table_id = subgrid_id+"_t";
                subgrid_pager_id = subgrid_id+"_p"
                subgrid_table_type_id = subgrid_id+"_typet";
                subgrid_pager_types_id = subgrid_id+"_typep"

                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"'></div><table id='"+subgrid_table_type_id+"' class='scroll'></table><div id='"+subgrid_pager_types_id+"'></div>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    pager:subgrid_pager_id,
                    url:"${pageContext.request.contextPath}/nomenclature/tamplates?id="+row_id,
                    editurl:"${pageContext.request.contextPath}/nomenclature/edittamplate?nmid="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.documents.files.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colModel: [
                        {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
                        {name:'nomenclature',index:'nomenclature', width:500, editable:true, editrules:{required:true},search:true,label:'<c:message code="label.nomenclature.template.name"/>',jsonmap:'nomenclature',search:false,editoptions:{
                            /**/
                            dataInit : function (elem) {
                                var value_elem=$(elem).val();
                                $(elem).wrap("<div></div>");
                                $(elem).width='80px';
                                $(elem).ajaxComboBox('${pageContext.request.contextPath}/nomenclature/showList',
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
                                            bind_to:'setupkey',
                                        }).bind('setupkey', function() {
                                            //$('#documentType_key').val($('#documentType_name_primary_key').val());
                                            nomenclature_id=$('#nomenclature_primary_key').val();
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
                        {name:'nomenclaturetype',index:'nomenclatureType.name', width:500, editable:true, editrules:{required:true},search:true,label:'<c:message code="label.nomenclature.template.type"/>',jsonmap:'nomenclatureType',search:false,editoptions:{
                            /**/
                            dataInit : function (elem) {
                                var value_elem=$(elem).val();
                                $(elem).wrap("<div></div>");
                                $(elem).width='80px';
                                $(elem).ajaxComboBox('${pageContext.request.contextPath}/nomenclaturetypes/showList',
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
                                            bind_to:'setupkey',
                                        }).bind('setupkey', function() {
                                            //$('#documentType_key').val($('#documentType_name_primary_key').val());
                                            nomenclature_type_id=$('#nomenclaturetype_primary_key').val();
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
                        {name:'count',index:'count', width:100, editable:true, editrules:{required:true}, editoptions:{defaultValue:'1'},search:false,label:'<c:message code="label.nomenclature.template.count"/>'},
                        {name:'version',index:'version', width:100, editable:true, editrules:{readonly:true}, editoptions:{defaultValue:'0'}, hidden:true,label:'<c:message code="label.version"/>'},
                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'nomenclature.name',
                    sortorder: "asc"
                });
                $("#"+subgrid_table_id).jqGrid('navGrid','#'+subgrid_pager_id,
                        {edit:true, add:true, del:true, search:false},
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
                        }
                );

                jQuery("#"+subgrid_table_type_id).jqGrid({
                    pager:subgrid_pager_types_id,
                    url:"${pageContext.request.contextPath}/nomenclature/types?id="+row_id,
                    editurl:"${pageContext.request.contextPath}/nomenclature/types?nmid="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.documents.files.title"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colModel: [
                        {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
                        {name:'nomenclaturetype',index:'nomenclatureType.name', width:500, editable:true, editrules:{required:true},search:true,label:'<c:message code="label.nomenclature.template.type"/>',jsonmap:'nomenclatureType',search:false,editoptions:{
                            /**/
                            dataInit : function (elem) {
                                var value_elem=$(elem).val();
                                $(elem).wrap("<div></div>");
                                $(elem).width='80px';
                                $(elem).ajaxComboBox('${pageContext.request.contextPath}/nomenclaturetypes/showList',
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
                                            bind_to:'setupkey',
                                        }).bind('setupkey', function() {
                                            //$('#documentType_key').val($('#documentType_name_primary_key').val());
                                            nomenclature_type_id2=$('#nomenclaturetype_primary_key').val();
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
                        {name:'version',index:'version', width:100, editable:true, editrules:{readonly:true}, editoptions:{defaultValue:'0'}, hidden:true,label:'<c:message code="label.version"/>'},
                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'nomenclatureType.name',
                    sortorder: "asc"
                });
                $("#"+subgrid_table_type_id).jqGrid('navGrid','#'+subgrid_pager_types_id,
                        {edit:true, add:true, del:true, search:false},
                        {/*MOD PARAM*/
                            editData:typesData,
                            closeAfterEdit: true,
                        },
                        {/*ADD PARAM*/
                            editData:typesData,
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
