package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import ua.pp.fairwind.favorid.internalDB.model.directories.DocumentType;
import ua.pp.fairwind.favorid.internalDB.repository.DocumentTypeRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Сергей on 07.10.2015.
 */
@Controller
@RequestMapping("/documenttypes")
public class DocumentTypeController {
    @Autowired
    DocumentTypeRepository repositoryDocumentType;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "document_type_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<DocumentType> getTable(HttpServletRequest request){
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
                rows = request.getParameter("rows") == null ? 10 : Integer.parseInt(request.getParameter("rows"));
                if(request.getParameter("sidx")!=null){
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
                return new JGridRowsResponse<>(repositoryDocumentType.findByNameContains(filterName, pageRequest));
            } else
            return new JGridRowsResponse<>(repositoryDocumentType.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(repositoryDocumentType.findByNameContains(filterName));
            } else
            return new JGridRowsResponse<>(repositoryDocumentType.findAll());
        }
    }


    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,DocumentType documentType,BindingResult result,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":
                repositoryDocumentType.save(documentType);
                response.setStatus(200);
                break;
            case "edit":
                DocumentType fromDB= repositoryDocumentType.getOne(documentType.getId());
                if(fromDB!=null) {
                    repositoryDocumentType.save(documentType);
                    if(documentType.getVersion()>=fromDB.getVersion()) {
                        repositoryDocumentType.save(documentType);
                        response.setStatus(200);
                    } else {
                        response.sendError(406,"ANOTHER TRANSACTION MODIFICATION");
                    }
                } else {
                    response.sendError(406,"NO CONTACT TYPE FOUND");
                }

                break;
            case "del":
                repositoryDocumentType.delete(documentType.getId());
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

}
