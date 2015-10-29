<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 04.08.2015
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<c:url value="/resources/script/ajax_combo/jquery.ajax-combobox.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/resources/script/ajax_combo/jquery.ajax-combobox.css"/>">

<style>
  .FormGrid {
    overflow:visible !important;
  }

  .ui-jqdialog.modal-content{
    overflow:visible !important;
  }

</style>

<script>
  var select_object={};

  create_select_column=function(fieldname,url_short_path,label,sendObject,options){
     var _option={
         post_element_name:fieldname+'_id',
         post_internal_field:fieldname+'_id',
         select_object:select_object,
     }
     _option= $.extend(_option,options);

     if(sendObject[_option.post_element_name]===null && sendObject[_option.post_element_name]===undefined){
         sendObject[_option.post_element_name]=function(){
             return _option.select_object[_option.post_internal_field];
         }
     }

      return fairwind_select_column(fieldname,url_short_path,label,_option.select_object,_option);
  }

  var fairwind_select_column=function(fieldname,url_short_path,label,modification_object,options){
    var _def_param = {
      id: 'id',
      show_field: 'name',
      requared:true,
      search:true,
      width:100,
    }
      if(options!==null && options!==undefined){
        if(options.post_parameter_name!==null && options.post_parameter_name!==undefined){
          if(options.master_select_element===null || options.master_select_element===undefined){
            options.master_select_element=opt.post_parameter_name;
          }
          _def_param.postdata_function={};
          _def_param.postdata_function[options.post_parameter_name]=function(){
              if(modification_object[options.master_select_element]!==null && modification_object[options.master_select_element]!==undefined) {
                  return modification_object[options.master_select_element];
              } else {
                  return '';
              }
          }
        }
      } else {
          options={};
      }

    var opt=$.extend(_def_param,options);
    if(opt.select_params===null || opt.select_params===undefined){
      opt.select_params={
        show_field: opt.show_field,
        id:opt.id,
        postdata:opt.postdata_function,
        parameter_name:opt.parameter_name,
        sub_filds:opt.sub_filds
      }

    }



    var fieldname_index;
    if(options.fieldname_index!==null && options.fieldname_index!==undefined){
      fieldname_index=options.fieldname_index;
    } else {
      fieldname_index=fieldname;
    }
    var columnt_def={
              name:fieldname,
              index:fieldname_index,
              width:opt.width,
              editable:true,
              editrules:{required:opt.requared},
              search:opt.search,
              edittype:'text',label:label,
              editoptions:{
                  dataInit:fairwind_select(url_short_path,modification_object,opt.select_params,opt.select_plugin_options)
              },
              formatter:function(cellvalue, options, rowObject ){
                  if(cellvalue===null || cellvalue===undefined)return '';
                  return '<p pkey='+cellvalue[opt.id]+'>'+cellvalue[opt.show_field]+'</p>'
              },
              unformat:function(cellvalue, options, cellObject ){
                  var element=$(cellObject).html();
                  return $(element).attr('pkey');
              }
            };
    return columnt_def;
  }



  var fairwind_select=function(url_short_path,modification_object,params,special_options) {
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


    return function(elem){
      var id=elem.id;
      var value_elem=$(elem).val();
      var box_name=id+'_box';
      var pk_name='#'+id+'_primary_key';

      var modification_parameter_name;
      if(param.parameter_name!==null && param.parameter_name!==undefined){
          modification_parameter_name=param.parameter_name;
      } else {
          modification_parameter_name=id+'_id';
      }
      $(elem).wrap("<div id='"+box_name+"'></div>");
      $(elem).width='80px';

      var options={lang: 'en',
        db_table: 'nation',
        per_page: 20,
        navi_num: 10,
        select_only: true,
        primary_key: param.name,
        postData:param.postdata,
        show_field: list_name,
        field:param.show_field,
        //recalc_width:false,
        button_img:'${pageContext.request.contextPath}/resources/images/btn.png',
        init_record: [value_elem],
        bind_to:'setupkey',
      };

      options=$.extend(options,special_options);

      $(elem).ajaxComboBox('${pageContext.request.contextPath}'+url_short_path
              ,options
              ).bind('setupkey', function() {
                  modification_object[modification_parameter_name]=$(pk_name).val();
              });
    }
  }
</script>