package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.model.directories.ContactType;
import ua.pp.fairwind.favorid.internalDB.repository.ContactTypeRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Сергей on 07.10.2015.
 */
@Controller
@RequestMapping("/contacttypes")
public class ContactTypeController {
    @Autowired
    ContactTypeRepository repositoryContactType;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "contact_type_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<ContactType> getTable(HttpServletRequest request){
        /*
        rows:10
        page:1
        sidx:id
        sord:asc
        */
        PageRequest paget=null;
        if(request.getParameter("page")!=null) {
            int page=0;
            int rowcount=10;
            try {
                page = Integer.parseInt(request.getParameter("page"))-1;
                rowcount=request.getParameter("rows")!=null?Integer.parseInt(request.getParameter("rows")):10;
                if(request.getParameter("sidx")!=null && request.getParameter("sord")!=null) {
                    String sortField=request.getParameter("sidx");
                    String sort=request.getParameter("sord");
                    paget = new PageRequest(page, rowcount,"asc".equals(sort)? Sort.Direction.ASC: Sort.Direction.DESC,sortField);
                } else {
                    paget = new PageRequest(page, rowcount);
                }
            }catch (Exception e){

            }
        }
        if(paget!=null){
            return new JGridRowsResponse<ContactType>(repositoryContactType.findAll(paget));
        }else{
            return new JGridRowsResponse<ContactType>(repositoryContactType.findAll());
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@ModelAttribute ContactType contactType, BindingResult result){
        if(result.hasErrors()){
            return "ERROR:"+result.toString();
        }
        repositoryContactType.save(contactType);
        return "Success";
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void modify(@RequestParam String oper,@ModelAttribute ContactType contactType, BindingResult result,HttpServletResponse response) throws Exception{
        if (result.hasErrors()) {
            response.sendError(400, result.toString());
        }
        System.out.println("TEST METHOD EXEC!");
        switch (oper) {
            case "edit":
            case "add": {
                repositoryContactType.save(contactType);
                response.setStatus(200);
            }break;
            case "del": {
                repositoryContactType.delete(contactType.getId());
                response.setStatus(200);
            }break;
            default:
                System.out.println("NO METHOD FOUND!");
                response.sendError(406, "Unknown command!");
        }
    }
}
