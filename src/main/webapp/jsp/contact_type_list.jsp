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
</div>
<div style="margin-left:20px">
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {

        $("#jqGrid").jqxGrid({
            url: '${pageContext.request.contextPath}/contacttypes/listing',
            mtype: "POST",
            styleUI : 'Bootstrap',
            datatype: "jsonp",
            colModel: [
                { label: 'ID', name: 'id', key: true, width: 75 },
                { label: 'Name', name: 'name', width: 150 },
                { label: 'Format', name: 'numberFormat', width: 100 },
                { label: 'Version', name: 'version', width: 50 },
            ],
            viewrecords: true,
            height: 250,
            rowNum: 20,
            pager: "#jqGridPager"
        });
    });

</script>
</html>
