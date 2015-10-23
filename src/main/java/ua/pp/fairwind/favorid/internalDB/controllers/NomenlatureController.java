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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.CombinedTemplate;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.NomenclatureTypes;
import ua.pp.fairwind.favorid.internalDB.repository.CombinedTemplatesRepository;
import ua.pp.fairwind.favorid.internalDB.repository.NomenclatureRepository;
import ua.pp.fairwind.favorid.internalDB.repository.NomenclatureTypeRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Сергей on 19.10.2015.
 */
@Controller
@RequestMapping("/nomenclature")
public class NomenlatureController {
    @Autowired
    NomenclatureRepository nomenclatureRepository;
    @Autowired
    CombinedTemplatesRepository combinedTemplatesRepository;
    @Autowired
    NomenclatureTypeRepository nomenclatureTypeRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "nomenclature_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Nomenclature> getTable(HttpServletRequest request){
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
                return new JGridRowsResponse<>(nomenclatureRepository.find(filterName, pageRequest));
            } else
                return new JGridRowsResponse<>(nomenclatureRepository.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(nomenclatureRepository.find(filterName));
            } else
                return new JGridRowsResponse<>(nomenclatureRepository.findAll());
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,Nomenclature nomenclature,BindingResult result,HttpServletResponse response)throws IOException {
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":
                nomenclatureRepository.save(nomenclature);
                response.setStatus(200);
                break;
            case "edit":
                Nomenclature fromDB=nomenclatureRepository.getOne(nomenclature.getId());
                if(fromDB!=null) {
                    nomenclatureRepository.save(nomenclature);
                    if(nomenclature.getVersion()>=fromDB.getVersion()) {
                        nomenclatureRepository.save(nomenclature);
                        response.setStatus(200);
                    } else {
                        response.sendError(406,"ANOTHER TRANSACTION MODIFICATION");
                    }
                } else {
                    response.sendError(406,"NO CONTACT TYPE FOUND");
                }

                break;
            case "del":
                nomenclatureRepository.delete(nomenclature.getId());
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edittamplate", method = {RequestMethod.POST,RequestMethod.GET})
    public void editorTemplate(@RequestParam String oper,@RequestParam long nmid,@RequestParam(required = false) Long nomenclature_id,@RequestParam(required = false) Long version,@RequestParam(required = false) Long count,@RequestParam(required = false) Long id,HttpServletResponse response)throws IOException {
        Nomenclature nomenclature=nomenclatureRepository.findOne(nmid);
        switch (oper) {
            case "add": {
                Nomenclature nomenclature_for_constract = nomenclature_id != null ? nomenclatureRepository.findOne(nomenclature_id) : null;
                CombinedTemplate combined = new CombinedTemplate();
                combined.setCount(count == null ? 0 : count);
                combined.setNomenclature(nomenclature_for_constract);
                combined.setParent(nomenclature);
                combinedTemplatesRepository.save(combined);
                nomenclatureRepository.save(nomenclature);
                response.setStatus(200);
            }
            break;
            case "edit": {
                CombinedTemplate combined = combinedTemplatesRepository.findOne(id);
                Nomenclature nomenclature_for_constract = nomenclature_id != null ? nomenclatureRepository.findOne(nomenclature_id) : null;
                if (combined != null) {
                    if (combined.getVersion() <= version) {
                        combined.setCount(count == null ? 0 : count);
                        combined.setNomenclature(nomenclature_for_constract);
                        combinedTemplatesRepository.save(combined);
                        response.setStatus(200);
                    } else {
                        response.sendError(406, "ANOTHER TRANSACTION MODIFICATION");
                    }
                } else {
                    response.sendError(406, "NO TEMPLATE FOUND");
                }
            }
            break;
            case "del": {
                CombinedTemplate combined = combinedTemplatesRepository.findOne(id);
                if (combined != null) {
                    combined.setParent(null);
                    nomenclatureRepository.save(nomenclature);
                    combinedTemplatesRepository.delete(id);
                }
                response.setStatus(200);
                }
                break;
            default:
                response.sendError(406, "UNKNOWN OPERATION");
        }
    }


    @Transactional(readOnly = true)
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleClientList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword) {
        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"name");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            page_num= page_num<1?1:page_num;
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<Nomenclature> page;
            if (qword != null && qword.length > 0) {
                page = nomenclatureRepository.find(qword[0], pager);
            } else {
                page = nomenclatureRepository.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Nomenclature ft=null;
                if(key!=null) {
                    ft = nomenclatureRepository.findOne(key);
                }
                return ft;
            } else {
                List<Nomenclature> page;
                if (qword != null && qword.length > 0) {
                    page = nomenclatureRepository.find(qword[0],sort);
                } else {
                    page = nomenclatureRepository.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/tamplates", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<CombinedTemplate> getTemplates(HttpServletRequest request,@RequestParam long id){
        Nomenclature nm=nomenclatureRepository.getOne(id);
        if(nm==null){
            return null;
        }
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
                return new JGridRowsResponse<>(combinedTemplatesRepository.find(filterName,nm, pageRequest));
            } else
                return new JGridRowsResponse<>(combinedTemplatesRepository.find(nm,pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(combinedTemplatesRepository.find(filterName,nm));
            } else
                return new JGridRowsResponse<>(combinedTemplatesRepository.find(nm));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/types", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<NomenclatureTypes> getTypes(HttpServletRequest request,@RequestParam long id){
        Nomenclature nm=nomenclatureRepository.getOne(id);
        if(nm==null){
            return null;
        }
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
                return new JGridRowsResponse<>(nomenclatureTypeRepository.find(filterName,nm, pageRequest));
            } else
                return new JGridRowsResponse<>(nomenclatureTypeRepository.find(nm,pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(nomenclatureTypeRepository.find(filterName,nm));
            } else
                return new JGridRowsResponse<>(nomenclatureTypeRepository.find(nm));
        }
    }
}
