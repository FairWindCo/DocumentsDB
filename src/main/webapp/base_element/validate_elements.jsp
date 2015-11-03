<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 02.11.2015
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<script>
    function fairwind_validate_click_handler(event, id, url,postfield,elemtn) {
        event = event || window.event;
        if (url !== null && url !== undefined && id !== null && id !== undefined) {
            var data={
            }
            data[postfield]=id;
            $.ajax(url, {
                data: data,
                method: 'POST',
                success: function (data, status, jqXHR) {
                    $(elemtn).replaceWith('<div class="panel center-block bg-success" style="height: 100%;text-align: center;"><span class="glyphicon glyphicon-flag" style="position: relative;top: 0;transform: translateY(50%);"></span></div>');
                },
                error: function (jqXHR, status, errorThrown) {
                    alert(errorThrown);
                }
            })
        }
    }

    function fairwind_validate_create(url_short_path,options) {
        fairwind_check_boolean=function(value) {
            if (value === undefined || value === null) return false;
            if (value === 'true' | value === 'TRUE') return true;
            if (value === 'false' | value === 'FALSE') return false;
            return value.valueOf() & true;
        };

        var _def_options={
            postfield:'id',
            check_ability:true,
            check_field:'canValidate',
            state_filed:'validated',
            valid_state_icon:'glyphicon-flag',
            invalid_state_icon:'glyphicon-pencil',
            lock_state_icon:'glyphicon-lock'
        };

        var opt=$.extend(_def_options,options);

        var url = '${pageContext.request.contextPath}' + url_short_path;

        var fairwind_validate = function (cellvalue, options, rowObject) {

            if (rowObject[opt.state_filed] !== null && rowObject[opt.state_filed] !== undefined && fairwind_check_boolean(rowObject[opt.state_filed])) {
                return '<div class="panel center-block bg-success" style="height: 100%;text-align: center;"><span class="glyphicon '+opt.valid_state_icon+'" style="position: relative;top: 0;transform: translateY(50%);"></span></div>';
            }

            if(opt.check_ability){
                if (rowObject[opt.check_field]!== null && rowObject[opt.check_field]!== undefined && fairwind_check_boolean(rowObject[opt.check_field])) {
                    var element = $('<div onclick="fairwind_validate_click_handler(event,' + rowObject.id + ',\'' + url + '\',\''+opt.postfield+'\',this)" ' +
                            'class="btn btn-success" pkey=' + rowObject.id + ' style="height: 100%;text-align: center;">' +
                            '<span class="glyphicon '+opt.invalid_state_icon+'"></span>' +
                            '</div>');
                    return element[0].outerHTML;
                }

                return '<div class="panel" style="height: 100%;text-align: center;"><span class="glyphicon ' +opt.lock_state_icon+'" style="position: relative;top: 0;transform: translateY(50%);"></span></div>';
            } else {
                var element = $('<div onclick="fairwind_validate_click_handler(event,' + rowObject.id + ',\'' + url + '\',this)" ' +
                        'class="btn btn-success" pkey=' + rowObject.id + ' style="height: 100%;text-align: center;">' +
                        '<span class="glyphicon '+opt.invalid_state_icon+'"></span>' +
                        '</div>');
                return element[0].outerHTML;
            }
        }
    return fairwind_validate;
    }
</script>