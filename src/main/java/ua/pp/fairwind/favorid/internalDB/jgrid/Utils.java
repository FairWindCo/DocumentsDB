package ua.pp.fairwind.favorid.internalDB.jgrid;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Сергей on 17.10.2015.
 */
public class Utils {
    public static Long getLongParameter(String name,HttpServletRequest request){
        Long val=null;
        String value=request.getParameter(name);
        if(value!=null && !value.isEmpty()){
            try {
                val = Long.parseLong(value);
            }catch (NumberFormatException e){
                //do nothing
            }
        }
        return val;
    }
}
