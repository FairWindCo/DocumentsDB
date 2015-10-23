package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.NomenclatureTypes;
import ua.pp.fairwind.favorid.internalDB.repository.NomenclatureTypeRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by ������ on 07.10.2015.
 */
@Controller
@RequestMapping("/nomenclaturetypes")
public class NomenclatureTypeController {
    @Autowired
    NomenclatureTypeRepository repositoryNomenclatureType;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "nomenclature_type_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<NomenclatureTypes> getTable(HttpServletRequest request){
        //Pegeable
        /*
        nd:1444413790954
        rows:10
        page:1
        sidx:id
        sord:asc
        filters:{"groupOp":"AND","rules":[{"field":"name","op":"bw","data":"dfadsfdasfd"}]}
         */
        PageRequest pageRequest=null;
        if(request.getParameter("page")!=null){
            int rows=10;
            int page;
            try {
                page = Integer.parseInt(request.getParameter("page")) - 1;
                page= page<0?0:page;
                rows = request.getParameter("rows") == null ? 10 : Integer.parseInt(request.getParameter("rows"));
                if(request.getParameter("sidx")!=null && !request.getParameter("sidx").isEmpty()){
                    String direction=request.getParameter("sord");
                    pageRequest=new PageRequest(page,rows,"asc".equals(direction)? Sort.Direction.ASC: Sort.Direction.DESC,request.getParameter("sidx"));
                } else {
                    pageRequest=new PageRequest(page,rows);
                }
            }catch (NumberFormatException ex){
                //do nothing
            }

        }/**/
        String filterName=request.getParameter("name");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<NomenclatureTypes>(repositoryNomenclatureType.findByNameContains(filterName, pageRequest));
            } else
            return new JGridRowsResponse<NomenclatureTypes>(repositoryNomenclatureType.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<NomenclatureTypes>(repositoryNomenclatureType.findByNameContains(filterName));
            } else
            return new JGridRowsResponse<NomenclatureTypes>(repositoryNomenclatureType.findAll());
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute NomenclatureTypes nomenclatureType, BindingResult result){
        if(result.hasErrors()){
            return "ERROR:"+result.toString();
        }
        repositoryNomenclatureType.save(nomenclatureType);
        return "Success";
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,NomenclatureTypes nomenclatureType,BindingResult result,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":
                repositoryNomenclatureType.save(nomenclatureType);
                response.setStatus(200);
                break;
            case "edit":
                NomenclatureTypes fromDB= repositoryNomenclatureType.getOne(nomenclatureType.getId());
                if(fromDB!=null) {
                    repositoryNomenclatureType.save(nomenclatureType);
                    if(nomenclatureType.getVersion()>=fromDB.getVersion()) {
                        repositoryNomenclatureType.save(nomenclatureType);
                        response.setStatus(200);
                    } else {
                        response.sendError(406,"ANOTHER TRANSACTION MODIFICATION");
                    }
                } else {
                    response.sendError(406,"NO CONTACT TYPE FOUND");
                }

                break;
            case "del":
                repositoryNomenclatureType.delete(nomenclatureType.getId());
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleClientList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword,@RequestParam long firmID) {
        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"name");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            page_num= page_num<1?1:page_num;
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<NomenclatureTypes> page;
            if (qword != null && qword.length > 0) {
                page = repositoryNomenclatureType.findByNameContains(qword[0], pager);
            } else {
                page = repositoryNomenclatureType.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                NomenclatureTypes ft=null;
                if(key!=null) {
                    ft = repositoryNomenclatureType.findOne(key);
                }
                return ft;
            } else {
                List<NomenclatureTypes> page;
                if (qword != null && qword.length > 0) {
                    page = repositoryNomenclatureType.findByNameContains(qword[0],sort);
                } else {
                    page = repositoryNomenclatureType.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

}
