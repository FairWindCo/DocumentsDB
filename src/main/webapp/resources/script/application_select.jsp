<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
/**
 * Created by Сергей on 02.11.2015.
 */

function fairwind_date_column(fieldname, label, options) {
var _def_param = {
format_src: 'd.m.Y H:i:s',
format_dst: 'd.m.Y H:i:s',
format:'d.m.yy',
default_value:"Now",
size:50,
search:false,
editable:true
}
var opt= $.extend(_def_param,options);
var index=fieldname;
if(opt.index!==null||p[t.index!==undefined])index=opt.index;

var columnd_def={name:fieldname,index:opt.index,width:opt.size, editable:opt.editable, label:label,formatter:'date',search:opt.search,
formatoptions:{
srcformat:opt.format_src,
newformat:opt.format_dst,
defaultValue:null
},
};
if(opt.editable){
columnd_def['editoptions']={
dataInit:function(el){
var value=$(el).val();
if(value==undefined||value==null)value=opt.default_value;
$(el).datepicker({
dateFormat:opt.format,
currentText: value,
});
},
};
}
var column= $.extend(columnd_def,opt.columnt_options);
return column;
}

function fairwind_check_boolean(value) {
if (value === undefined || value === null) return false;
if (value === 'true' | value === 'TRUE') return true;
if (value === 'false' | value === 'FALSE') return false;
return value.valueOf() & true;
}

function fairwind_subscribe_click_handler(event, id, url, elemtn) {
event = event || window.event;
if (url !== null && url !== undefined && id !== null && id !== undefined) {
$.ajax(url, {
data: {
id: id
},
method: 'POST',
success: function (data, status, jqXHR) {
$(elemtn).replaceWith('<div class="panel center-block bg-success" style="height: 100%;text-align: center;"><span class="glyphicon glyphicon-flag" style="position: relative;top: 0;transform: translateY(50%);"></span></div>');
},
error: function (jqXHR, status, errorThrown) {
alert(errorThrown);
}
})
}
//alert('ID='+id+' URL:'+url);
}
function fairwind_commite_click_handler(event, id, url, elemtn) {
event = event || window.event;
if (url !== null && url !== undefined && id !== null && id !== undefined) {
$.ajax(url, {
data: {
id: id
},
method: 'POST',
success: function (data, status, jqXHR) {
$(elemtn).replaceWith('<div class="panel center-block bg-success" style="height: 100%;text-align: center;"><span class="glyphicon glyphicon-check" style="position: relative;top: 0;transform: translateY(50%);"></span></div>');
},
error: function (jqXHR, status, errorThrown) {
alert(errorThrown);
}
})
}
//alert('ID='+id+' URL:'+url);
}
function fairwind_detail_click_handler(event, id, url, elemtn) {
event = event || window.event;
if (url !== null && url !== undefined && id !== null && id !== undefined) {
$.ajax(url, {
data: {
id: id
},
method: 'GET',
success: function (data, status, jqXHR) {
var dd = $("<div/>");
var content = $("<div/>");
content.html(data);
content.appendTo(dd);
dd.dialog({
modal: true,
closeOnEscape: true,
autoResize: true,
height: 'auto',
width: 'auto',
}).dialog('open');
},
error: function (jqXHR, status, errorThrown) {
alert(errorThrown);
}
})
}
//alert('ID='+id+' URL:'+url);
}
function fairwind_subscribe_create(url_short_path, options) {
var url = '${pageContext.request.contextPath}' + url_short_path;

var fairwind_subscribe = function (cellvalue, options, rowObject) {
if (rowObject.subscribed !== null && rowObject.subscribed !== undefined) {
if (rowObject.subscribed) {
return '<div class="panel center-block bg-success" style="height: 100%;text-align: center;"><span class="glyphicon glyphicon-flag" style="position: relative;top: 0;transform: translateY(50%);"></span></div>';
}
}


if (rowObject.canSubscribe !== null && rowObject.canSubscribe !== undefined) {
if (fairwind_check_boolean(rowObject.canSubscribe)) {
var element = $('<div onclick="fairwind_subscribe_click_handler(event,' + rowObject.id + ',\'' + url + '\',this)" ' +
'class="btn btn-success" pkey=' + rowObject.id + ' style="height: 100%;text-align: center;">' +
'<span class="glyphicon glyphicon-pencil"></span>' +
'</div>');
return element[0].outerHTML;
}
}

return '<div class="panel" style="height: 100%;text-align: center;"><span class="glyphicon glyphicon-lock" style="position: relative;top: 0;transform: translateY(50%);"></span></div>';
}

return fairwind_subscribe;
}
function fairwind_commite_create(url_short_path, options) {
var url = '${pageContext.request.contextPath}' + url_short_path;

var fairwind_subscribe = function (cellvalue, options, rowObject) {
if (rowObject.executed !== null && rowObject.executed !== undefined) {
if (rowObject.executed) {
return '<div class="panel center-block bg-success" style="height: 100%;text-align: center;"><span class="glyphicon glyphicon-check" style="position: relative;top: 0;transform: translateY(50%);"></span></div>';
}
}

if (rowObject.canCommite !== null && rowObject.canCommite !== undefined) {
if (fairwind_check_boolean(rowObject.canCommite)) {
var element = $('<div onclick="fairwind_commite_click_handler(event,' + rowObject.id + ',\'' + url + '\',this)" ' +
'class="btn btn-success" pkey=' + rowObject.id + ' >' +
'<span class="glyphicon glyphicon-edit"></span>' +
'</div>');

return element[0].outerHTML;
}
}
return '<div class="panel" style="height: 100%;text-align: center;"><span class="glyphicon glyphicon-remove-sign" style="position: relative;top: 0;transform: translateY(50%);"></span></div>';

}

return fairwind_subscribe;
}
function fairwind_detail_create(url_short_path, options) {
var url = '${pageContext.request.contextPath}' + url_short_path;

var fairwind_subscribe = function (cellvalue, options, rowObject) {
var element = $('<div onclick="fairwind_detail_click_handler(event,' + rowObject.id + ',\'' + url + '\',this)" ' +
'class="btn btn-default" pkey=' + rowObject.id + ' >' +
'<span class="glyphicon glyphicon-open"></span>' +
'</div>');
return element[0].outerHTML;
}

return fairwind_subscribe;
}


var fairwind_select_column = function (fieldname, url_short_path, label, modification_object, options) {
var _def_param = {
id: 'id',
show_field: 'name',
requared: true,
search: false,
width: 100,
useformater:true,
useunformater:true
}
if (options !== null && options !== undefined) {
if (options.post_parameter_name !== null && options.post_parameter_name !== undefined) {
if (options.master_select_element === null || options.master_select_element === undefined) {
options.master_select_element = options.post_parameter_name;
}
_def_param.postdata_function = {};
_def_param.postdata_function[options.post_parameter_name] = function () {
if (modification_object[options.master_select_element] !== null && modification_object[options.master_select_element] !== undefined) {
return modification_object[options.master_select_element];
} else {
return '';
}
}
}
} else {
options = {};
}

var opt = $.extend(_def_param, options);
if (opt.select_params === null || opt.select_params === undefined) {
opt.select_params = {
show_field: opt.show_field,
id: opt.id,
postdata: opt.postdata_function,
parameter_name: opt.parameter_name,
sub_filds: opt.sub_filds
}

}


var fieldname_index;
if (options.fieldname_index !== null && options.fieldname_index !== undefined) {
fieldname_index = options.fieldname_index;
} else {
fieldname_index = fieldname;
}
var columnt_def = {
name: fieldname,
index: fieldname_index,
width: opt.width,
editable: true,
editrules: {required: opt.requared},
search: opt.search,
edittype: 'text', label: label,
editoptions: {
dataInit: fairwind_select(url_short_path, modification_object, opt.select_params, opt.select_plugin_options)
},
};
if(opt.useformater) {
columnt_def['formatter'] = function (cellvalue, options, rowObject) {
if (cellvalue === null || cellvalue === undefined)return '';
return '<p pkey=' + cellvalue[opt.id] + '>' + cellvalue[opt.show_field] + '</p>'
};
if (opt.useformater) {
columnt_def['unformat'] = function (cellvalue, options, cellObject) {
var element = $(cellObject).html();
return $(element).attr('pkey');
}

}
}

return columnt_def;
}


var fairwind_select = function (url_short_path, modification_object, params, special_options) {
var _def_param = {
id: 'id',
show_field: 'name',
}
var param = $.extend(_def_param, params);
if (param.sub_filds !== null && param.sub_filds !== undefined) {
var list_name = param.show_field + ',' + param.sub_filds;
} else {
var list_name = param.show_field;
}


return function (elem) {
var id = elem.id;
var value_elem = $(elem).val();
var box_name = id + '_box';
var pk_name = '#' + id + '_primary_key';

var modification_parameter_name;
if (param.parameter_name !== null && param.parameter_name !== undefined) {
modification_parameter_name = param.parameter_name;
} else {
modification_parameter_name = id + '_id';
}
modification_object[modification_parameter_name]=value_elem;
$(elem).wrap("<div id='" + box_name + "'></div>");
$(elem).width = '80px';

var options = {
lang: 'en',
db_table: 'nation',
per_page: 20,
navi_num: 10,
select_only: true,
primary_key: param.name,
postData: param.postdata,
show_field: list_name,
field: param.show_field,
//recalc_width:false,
button_img: '${pageContext.request.contextPath}/resources/images/btn.png',
init_record: [value_elem],
bind_to: 'setupkey',
};

options = $.extend(options, special_options);

$(elem).ajaxComboBox('${pageContext.request.contextPath}' + url_short_path
, options
).bind('setupkey', function () {
modification_object[modification_parameter_name] = $(pk_name).val();
});
}
}