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
        <li><a href="#"><c:message code="label.message"/></a></li>
    </ol>
    <div class="row">

        <div class="col-lg-12">

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

        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        $("#grid").jqGrid({
            url:'${pageContext.request.contextPath}/messages/my_listing',
            editurl:'${pageContext.request.contextPath}/messages/edit',
            datatype: 'json',
            mtype: 'POST',
            styleUI : 'Bootstrap',
            colModel:[
                {name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
                {name:'userName',width:50, editable:false, editrules:{required:false}, search:false,sort:false,label:'<c:message code="label.messages.creationUser"/>'},
                {name:'messageText',index:'comments', width:200, editable:true, editrules:{required:true}, edittype:'textarea',label:'<c:message code="label.messages.text"/>'},
                {name:'actual',index:'actual',width:100, editable:true, label:'<c:message code="label.messages.actualDate"/>',formatter:'date',
                    formatoptions:{
                        srcformat:'d.m.Y H:i:s',
                        newformat:'d.m.Y H:i:s',
                        defaultValue:null
                    },
                    editoptions:{
                        dataInit:function(el){
                            $(el).datepicker({dateFormat:'dd-mm-yy'});
                        },
                    }
                },
                {name:'creationDate',editable:false, label:'<c:message code="label.messages.creationDate"/>',formatter:'date',
                    formatoptions:{
                        srcformat:'d.m.Y H:i:s',
                        newformat:'d.m.Y H:i:s',
                        defaultValue:null
                    }
                },
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
            caption:"<c:message code="label.message.my"/>",
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

                var sub_select_obj={};

                var subGrisData={
                    'recipientID':function(){
                        return sub_select_obj.fio_id;
                    }
                }

                var parent_data=jQuery("#grid").jqGrid("getRowData",row_id);
                var can_edit=true;
                if(parent_data!==undefined && parent_data!==null){
                    if(parent_data.subscribed!==undefined && parent_data.subscribed!==null){
                        if($.parseJSON(parent_data.subscribed)) {
                            can_edit =false;
                        }
                    }
                }

                subgrid_table_id = subgrid_id+"_t";
                subgrid_pager_id = subgrid_id+"_p"

                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"'></div>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    pager:subgrid_pager_id,
                    url:"${pageContext.request.contextPath}/messages/recipients?messageId="+row_id,
                    editurl:"${pageContext.request.contextPath}/messages/editRecipient?messgeId="+row_id,
                    datatype: "json",
                    mtype: 'POST',
                    width:800,
                    caption:"<c:message code="label.message.recipients"/>",
                    emptyrecords: "<c:message code="label.emptyrecords"/>",
                    styleUI : 'Bootstrap',
                    colModel: [
                        {name:'id',index:'id', width:50, editable:false, editoptions:{readonly:true, size:10}, hidden:true,label:'<c:message code="label.id"/>'},
                        fairwind_select_column('fio','/messages/messagePersons?messgaeId='+row_id,'<c:message code="label.message.recipient"/>',sub_select_obj,{
                            show_field:'fio',
                            useformater:false,
                            select_plugin_options:{
                                order_by: 'person.surname'
                            }
                        }),
                        {name:'validationDate',index:'validationDate', width:70, editable:false, label:'<c:message code="label.message.validationDate"/>'},
                    ],
                    id: "id",
                    height: '100%',
                    rowNum:20,
                    sortname: 'recipient.surname',
                    sortorder: "asc"
                });
                $("#"+subgrid_table_id).jqGrid('navGrid','#'+subgrid_pager_id,
                        {edit:false, add:true, del:true, search:false},
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
                    <sec:authorize ifAnyGranted="ROLE_MESSAGE_ADMINISTRATOR">
                    edit:true,
                    add:true,
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
                {/*DEL PARAM*/}
        );

        jQuery("#grid").jqGrid('filterToolbar',{stringResult: false,searchOnEnter:true});

    });
</script>
</html>
