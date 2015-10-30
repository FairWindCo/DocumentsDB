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
        <li><a href="#"><c:message code="label.requests"/></a></li>
    </ol>
    <div class="row">

        <div class="col-lg-12">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><c:message code="label.requests.view"/></h3>
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
        var getColumnIndexByName = function(grid,columnName) {
            var cm = grid.jqGrid('getGridParam','colModel'), i=0,l=cm.length;
            for (; i<l; i+=1) {
                if (cm[i].name===columnName) {
                    return i; // return the index
                }
            }
            return -1;
        };

        var select_object={

        }

        var GridData={documentType_key:function(){return select_object.documentType_key},
            counterpart_id:function(){return select_object.counterparty_id},
            agreement_id:function(){return select_object.agreement_id},
        };

        $("#grid").jqGrid({
            url:'${pageContext.request.contextPath}/requests/listing',
            editurl:'${pageContext.request.contextPath}/requests/edit',
            datatype: 'json',
            mtype: 'POST',
            styleUI : 'Bootstrap',
            colModel:[
                {name: "act", width: 20,label:'', editable:false,search:false},
                {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
                {name:'typeRequest',index:'typeRequest', width:100, editable:true, editrules:{required:true}, search:true,edittype:'select',label:'<c:message code="label.requests.type"/>',
                    editoptions:{value:{0:'<c:message code="label.PURCHASE"/>',
                        1:'<c:message code="label.RSHIPMENT"/>',
                        2:'<c:message code="label.PRODUCTION"/>',
                        3:'<c:message code="label.REPAIR"/>',
                    },
                        dataEvents: [
                            { type: 'click', data: { i: 7 }, fn: function(e) {
                                select_object.documentType_key=e.currentTarget.value;
                            } },
                        ]
                    }
                },
                fairwind_select_column('counterparty','/counterparts/showList','<c:message code="label.requests.counterparty"/>',select_object,{
                    show_field:'shortName'
                }),
                fairwind_select_column('agreement','/counterparts/showListAgreements','<c:message code="label.requests.agreements"/>',select_object,{
                    post_parameter_name:'counterpart_id',
                    master_select_element:'counterparty_id'
                }),
                {name:'responsiblePerson',index:'responsiblePerson', width:100, editable:false, editrules:{required:false}, search:false,label:'<c:message code="label.storehouse.responsiblePerson"/>',jsonmap:'responsiblePerson.surname'},
                {name:'comments',index:'comments', width:100, editable:true, editrules:{required:false}, search:false,edittype:'textarea',label:'<c:message code="label.storehouse.comments"/>'},
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
            caption:"<c:message code="label.storehouse.view"/>",
            emptyrecords: "<c:message code="label.emptyrecords"/>",
            loadonce: false,
            loadComplete: function() {
                var grid = $(this),
                        iCol = getColumnIndexByName(grid,'act'); // 'act' - name of the actions column
                grid.children("tbody")
                        .children("tr.jqgrow")
                        .children("td:nth-child("+(iCol+1)+")")
                        .each(function() {
                            $("<div>",
                                    {
                                        title: "Custom",
                                        mouseover: function() {
                                            $(this).addClass('ui-state-hover');
                                        },
                                        mouseout: function() {
                                            $(this).removeClass('ui-state-hover');
                                        },
                                        click: function(e) {
                                            alert("'Custom' button is clicked in the rowis="+
                                                    $(e.target).closest("tr.jqgrow").attr("id") +" !");
                                        }
                                    }
                            ).css({"margin-left": "5px", float:"left"})
                                    .addClass("ui-pg-div ui-inline-custom")
                                    .append('<span class="ui-icon ui-icon-document"></span>')
                                    .appendTo($(this));
                        });
            },
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

                var sub_select_obj={};

                var subGrisData={
                    'nomenclature_id':function(){
                        return sub_select_obj.nomenclature_id;
                    }
                }

                subgrid_table_id = subgrid_id+"_t";
                subgrid_pager_id = subgrid_id+"_p"

                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"'></div>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    pager:subgrid_pager_id,
                    url:"${pageContext.request.contextPath}/requests/state?id="+row_id,
                    editurl:"${pageContext.request.contextPath}/requests/editItem?requestid="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.storehouse.nomenclature_list"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colModel: [
                        {name:'id',index:'id', width:50, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
                        {name:'nomenclature',index:'nomenclature',jsonmap:'nomenclature.code', width:70, editable:false, search:true,label:'<c:message code="label.nomenclature.template.code"/>'},
                        //{name:'nomenclature',index:'nomenclature', width:500, editable:false, search:true,label:'<c:message code="label.nomenclature.template.name"/>'},
                        fairwind_select_column('nomenclature','/nomenclature/showList','<c:message code="label.requests.nomenclature"/>',select_object),
                        {name:'nomenclature',index:'nomenclature',jsonmap:'nomenclature.manufacturer', width:100, editable:false, search:true,label:'<c:message code="label.nomenclature.template.manufactured"/>'},
                        {name:'count',index:'count', width:30, editable:true, editrules:{required:true}, editoptions:{defaultValue:'1'},search:false,label:'<c:message code="label.nomenclature.template.count"/>'},
                        {name:'units',index:'units', width:50, editable:true, editrules:{required:true}, search:true,edittype:'select',label:'<c:message code="label.requests.type"/>',
                            editoptions:{
                                defaultValue:3,
                                value:{
                                0:'<c:message code="label.KILOGRAMS"/>',
                                1:'<c:message code="label.GRAMS"/>',
                                2:'<c:message code="label.TONS"/>',
                                3:'<c:message code="label.COUNT"/>',
                                4:'<c:message code="label.LITRES"/>',
                                5:'<c:message code="label.MILLILITRES"/>',
                                6:'<c:message code="label.METERS"/>',
                                7:'<c:message code="label.MILLIMETERS"/>',
                                },
                            }
                        },
                        {name:'comments',index:'comments', width:100, editable:true, label:'<c:message code="label.requests.items.comments"/>'},
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
                            editData:subGrisData,
                                    closeAfterEdit: true,
                        },
                        {/*ADD PARAM*/
                            editData:subGrisData,
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
                    <sec:authorize ifAnyGranted="ROLE_STOREHOUSE_MASTER">
                    edit:true,
                    add:true,
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
