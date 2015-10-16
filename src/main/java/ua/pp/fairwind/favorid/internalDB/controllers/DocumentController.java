package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
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
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.proxy.DocumentProxy;
import ua.pp.fairwind.favorid.internalDB.repository.DocumentRepository;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ������ on 14.10.2015.
 */
@Controller
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    SecurityContext securityContext;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "documents_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Document> getTable(HttpServletRequest request){
        PageRequest pageRequest=null;
        if(request.getParameter("page")!=null){
            int rows=10;
            int page;
            try {
                page = Integer.parseInt(request.getParameter("page")) - 1;
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
                return new JGridRowsResponse<>(documentRepository.findByName(filterName, pageRequest));
            } else
                return new JGridRowsResponse<>(documentRepository.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(documentRepository.findByName(filterName));
            } else
                return new JGridRowsResponse<>(documentRepository.findAll());
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,Document document,BindingResult result,HttpServletResponse response)throws IOException {
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        UserDetailsAdapter userDetail=(UserDetailsAdapter)securityContext.getAuthentication().getDetails();
        if(userDetail==null){
            response.sendError(403, "FORBIDDEN!");
            return;
        }
        switch (oper){
            case "add":
                if(userDetail.hasRole("ROLE_ADD_DOCUMENTS")) {
                    //document.setCreationUser(userDetail.getUserP());
                    documentRepository.save(document);
                    response.setStatus(200);
                } else {
                    response.sendError(403, "FORBIDDEN!");
                }
                break;
            case "edit":
                if(userDetail.hasRole("ROLE_EDIT_DOCUMENTS")) {
                    Document cpt = documentRepository.findOne(document.getId());
                    if (document != null) {
                        if (cpt.getVersion() <= document.getVersion()) {
                            cpt.setNumber(document.getNumber());
                            cpt.setName(document.getName());
                            cpt.setDescription(document.getDescription());
                            documentRepository.save(cpt);
                            response.setStatus(200);
                        } else {
                            response.sendError(400, "ANOTHER TRANSACTION MODIFICATION!");
                        }
                    } else {
                        response.sendError(404, "NO Counterpart WITH ID " + document.getId() + " FOUND");
                    }
                } else {
                    response.sendError(403, "FORBIDDEN!");
                }
                break;
            case "del":
                if(userDetail.hasRole("ROLE_DELETE_DOCUMENTS")) {
                    documentRepository.delete(document.getId());
                    response.setStatus(200);
                } else {
                    response.sendError(403, "FORBIDDEN!");
                }
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleClientList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword) {
        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"surname");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<DocumentProxy> page;
            if (qword != null && qword.length > 0) {
                page = documentRepository.findProxy(qword[0], pager);
            } else {
                page = documentRepository.findProxy(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Document ft=null;
                if(key!=null) {
                    ft = documentRepository.findOne(key);
                }
                return ft;
            } else {
                List<DocumentProxy> page;
                if (qword != null && qword.length > 0) {
                    page = documentRepository.findProxy(qword[0], sort);
                } else {
                    page = documentRepository.findProxy(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }
}
