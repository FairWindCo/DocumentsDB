package ua.pp.fairwind.favorid.internalDB.jgrid;

import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 17.10.2015.
 */
public class Utils {
    public static Long getLongParameter(String name,HttpServletRequest request){
        Long val=null;
        String value=request.getParameter(name);
        if(value!=null && !value.isEmpty()){
            if("null".equals(value)||"undifined".equals(value))return null;
            try {
                val = Long.parseLong(value);
            }catch (NumberFormatException e){
                //do nothing
            }
        }
        return val;
    }

    public static Sort getJComboSortOrder(HttpServletRequest request){
        Sort sort=null;
        String order_string=request.getParameter("order_by");
        if(order_string!=null && !order_string.isEmpty()){
            sort=formSortFromSortDescription(order_string);
        }
        if(sort==null) {
            String[] values = request.getParameterValues("order_by");
            if (values != null && values.length > 0) {
                sort = formSortFromSortDescription(values);
            }
        }
        if(sort==null) {
            String[] values = request.getParameterValues("order_by[]");
            if (values != null && values.length > 0) {
                sort = formSortFromSortDescription(values);
            }
        }
        if(sort==null) {
            order_string = request.getParameter("order_by[]");
            if (order_string != null && !order_string.isEmpty()) {
                sort = formSortFromSortDescription(order_string);
            }
        }
        return sort;
    }

    public static Sort formSortFromSortDescription(String[] sortdescription){
        if(sortdescription!=null && sortdescription.length>0) {
            ArrayList<Sort.Order> listorder = new ArrayList<>();
            for (String declaration : sortdescription) {
                String[] oneDescription=declaration.split(" ");
                if (oneDescription != null) {
                    if (oneDescription.length > 1) {
                        switch (oneDescription[1]) {
                            case "ASC":
                                listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[0]));
                            case "DESC":
                                listorder.add(new Sort.Order(Sort.Direction.DESC, oneDescription[0]));
                            default:
                                listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[0]));
                        }
                    } else if (oneDescription.length == 1) {
                        listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[0]));
                    }
                    if (listorder.size() > 0) {
                        return new Sort(listorder.toArray(new Sort.Order[listorder.size()]));
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static Sort formSortFromSortDescription(String[][] sortdescription){
        if(sortdescription!=null && sortdescription.length>0) {
            ArrayList<Sort.Order> listorder = new ArrayList<>();
            for (String[] oneDescription : sortdescription) {
                if (oneDescription != null) {
                    if (oneDescription.length > 1) {
                        switch (oneDescription[0]) {
                            case "ASC":
                                listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[1]));
                            case "DESC":
                                listorder.add(new Sort.Order(Sort.Direction.DESC, oneDescription[1]));
                            default:
                                listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[1]));
                        }
                    } else if (oneDescription.length == 1) {
                        listorder.add(new Sort.Order(Sort.Direction.ASC, oneDescription[0]));
                    }
                    if (listorder.size() > 0) {
                        return new Sort(listorder.toArray(new Sort.Order[listorder.size()]));
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static Sort formSortFromSortDescription(String sortdescription){

        if(sortdescription!=null && !sortdescription.isEmpty()){
            sortdescription=sortdescription.trim();
            if(sortdescription.contains(",")) {
                String[] sortorders = sortdescription.split(",");
                ArrayList<Sort.Order> listorder=new ArrayList<>();
                for(String sortorder:sortorders){
                    Sort.Order order=formOrder(sortdescription);
                    if(order!=null){
                        listorder.add(order);
                    }
                }
                if(listorder.size()>0){
                    return new Sort(listorder.toArray(new Sort.Order[listorder.size()]));
                }
            } else {
                Sort.Order order=formOrder(sortdescription);
                if(order!=null){
                    return new Sort(order);
                }
            }

        }
        return null;
    }



    public static Sort.Order formOrder(String orderDeclaration){
        if(orderDeclaration==null || orderDeclaration.isEmpty()) return null;
        String[] delaration=orderDeclaration.trim().split(" ");
        if(delaration==null || delaration.length!=2){
            return null;
        } else {
            switch (delaration[1]){
                case "ASC" :return new Sort.Order(Sort.Direction.ASC ,delaration[0]);
                case "DESC":return new Sort.Order(Sort.Direction.DESC,delaration[0]);
                default:
                    return new Sort.Order(Sort.Direction.ASC ,delaration[0]);
            }
        }
    }



    public static void main(String[] args) {
        System.out.println(formSortFromSortDescription("filesTypeName%20ASC"));
        System.out.println(formSortFromSortDescription("filesTypeName ASC"));
    }

    public static Long getLongFromString(String str){
        if(str==null || str.isEmpty()) return null;
        try {
            return new Long(str);
        } catch (NumberFormatException e){
            return null;
        }
    }

    public static Set<Long> getIDsFromRequest(HttpServletRequest request,String paramName){
        if(request==null || paramName==null) return null;
        Set<Long> set=null;
        String idsString=request.getParameter(paramName);
        if(idsString!=null){
            set=getIdFromString(idsString);
        }
        if(set==null) {
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length > 0) {
                set = new HashSet<>();
                for (String id : values) {
                    try {
                        Long val = new Long(id);
                        set.add(val);
                    } catch (NumberFormatException e) {
                        //do nothing
                    }
                }
                if (set.size() == 0) set = null;
            }
        }
        if(set==null) {
            String[] values = request.getParameterValues(paramName+"[]");
            if (values != null && values.length > 0) {
                set = new HashSet<>();
                for (String id : values) {
                    try {
                        Long val = new Long(id);
                        set.add(val);
                    } catch (NumberFormatException e) {
                        //do nothing
                    }
                }
                if (set.size() == 0) set = null;
            }
        }
        if(set==null) {
            idsString=request.getParameter(paramName+"[]");
            if(idsString!=null){
                set=getIdFromString(idsString);
            }
        }
        return set;
    }


    public static Set<Long> getIdFromString(String s){
        if(s==null || s.length()==0)return null;
        String[] ids=s.split(",");
        if(ids==null | ids.length==0)return null;
        Set<Long> set=new HashSet<>();
        for (String id:ids){
            try {
                Long val=new Long(id);
                set.add(val);
            }catch (NumberFormatException e){
                //do nothing
            }
        }
        if(set.size()==0)return null;
        return set;
    }

}
