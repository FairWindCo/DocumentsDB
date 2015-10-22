package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.jgrid.Utils;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.directories.DocumentType;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.document.DocumentFile;
import ua.pp.fairwind.favorid.internalDB.model.document.DocumentSecurity;
import ua.pp.fairwind.favorid.internalDB.model.document.DocumentSubscribe;
import ua.pp.fairwind.favorid.internalDB.model.proxy.DocumentProxy;
import ua.pp.fairwind.favorid.internalDB.repository.*;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    DocumentTypeRepository documentTypeRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    CounterpartyRepository counterpartyRepository;
    @Autowired
    DocumentSecurityRepository documentSecurityRepository;
    @Autowired
    DocumentSubscribeRepository documentSubscribeRepository;
    @Autowired
    DocumentFileRepository documentFileRepository;


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

    @Transactional(readOnly = true)
    @RequestMapping(value = "/security_listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<DocumentSecurity> getSecurityTable(HttpServletRequest request,HttpServletResponse response,@RequestParam Long document_id){
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
        Document document=documentRepository.findOne(document_id);
        if(document!=null) {
            String filterName = request.getParameter("surname");
            if (pageRequest != null) {
                if (filterName != null && !filterName.isEmpty()) {
                    return new JGridRowsResponse<>(documentSecurityRepository.findByDocumentAndPersonSurname(document,filterName, pageRequest));
                } else
                    return new JGridRowsResponse<>(documentSecurityRepository.findByDocument(document,pageRequest));
            } else {
                if (filterName != null && !filterName.isEmpty()) {
                    return new JGridRowsResponse<>(documentSecurityRepository.findByDocumentAndPersonSurname(document,filterName));
                } else
                    return new JGridRowsResponse<>(documentSecurityRepository.findByDocument(document));
            }
        } else {
            response.setStatus(404);
            return null;
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/subscriber_listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<DocumentSubscribe> getSubscriberTable(HttpServletRequest request,HttpServletResponse response,@RequestParam Long document_id){
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
        Document document=documentRepository.findOne(document_id);
        if(document!=null) {
            String filterName = request.getParameter("surname");
            if (pageRequest != null) {
                if (filterName != null && !filterName.isEmpty()) {
                    return new JGridRowsResponse<>(documentSubscribeRepository.findByDocumentAndPersonSurname(document,filterName, pageRequest));
                } else
                    return new JGridRowsResponse<>(documentSubscribeRepository.findByDocument(document,pageRequest));
            } else {
                if (filterName != null && !filterName.isEmpty()) {
                    return new JGridRowsResponse<>(documentSubscribeRepository.findByDocumentAndPersonSurname(document,filterName));
                } else
                    return new JGridRowsResponse<>(documentSubscribeRepository.findByDocument(document));
            }
        } else {
            response.setStatus(404);
            return null;
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/file_listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<DocumentFile> getFiles(HttpServletRequest request,HttpServletResponse response,@RequestParam Long document_id){
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
        Document document=documentRepository.findOne(document_id);
        if(document!=null) {
            String filterName = request.getParameter("fileName");
            if (pageRequest != null) {
                if (filterName != null && !filterName.isEmpty()) {
                    return new JGridRowsResponse<>(documentFileRepository.findByDocumentAndFileName(document, filterName, pageRequest));
                } else
                    return new JGridRowsResponse<>(documentFileRepository.findByDocument(document,pageRequest));
            } else {
                if (filterName != null && !filterName.isEmpty()) {
                    return new JGridRowsResponse<>(documentFileRepository.findByDocumentAndFileName(document, filterName));
                } else
                    return new JGridRowsResponse<>(documentFileRepository.findByDocument(document));
            }
        } else {
            response.setStatus(404);
            return null;
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,Document document,BindingResult result,HttpServletRequest request,HttpServletResponse response)throws IOException {
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        UserDetailsAdapter userDetail=(UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null){
            response.sendError(403, "FORBIDDEN!");
            return;
        }
        switch (oper){
            case "add":
                if(userDetail.hasRole("ROLE_ADD_DOCUMENTS")) {
                    //document.setCreationUser(userDetail.getUserP());
                    Long typeId= Utils.getLongParameter("documentType_key",request);
                    if(typeId!=null){
                        DocumentType dt=documentTypeRepository.findOne(typeId);
                        if(dt!=null)document.setDocumentType(dt);
                    }
                    Long from_firm=Utils.getLongParameter("counterprty_from_id",request);
                    if(from_firm!=null){
                        Counterparty from=counterpartyRepository.findOne(from_firm);
                        if(from!=null)document.setCounterparty_from(from);
                    }
                    Long to_firm=Utils.getLongParameter("counterprty_to_id",request);
                    if(to_firm!=null){
                        Counterparty to=counterpartyRepository.findOne(to_firm);
                        if(to!=null)document.setCounterparty_to(to);
                    }
                    Long from_person=Utils.getLongParameter("person_from_id",request);
                    if(from_person!=null){
                        Person from=personRepository.findOne(from_person);
                        if(from!=null)document.setPerson_from(from);
                    }
                    Long to_person=Utils.getLongParameter("person_to_id",request);
                    if(to_person!=null){
                        Person to=personRepository.findOne(to_person);
                        if(to!=null)document.setPerson_to(to);
                    }
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
                            Long typeId= Utils.getLongParameter("documentType_key",request);
                            if(typeId!=null){
                                DocumentType dt=documentTypeRepository.findOne(typeId);
                                if(dt!=null)cpt.setDocumentType(dt);
                            }
                            Long from_firm=Utils.getLongParameter("counterprty_from_id",request);
                            if(from_firm!=null){
                                Counterparty from=counterpartyRepository.findOne(from_firm);
                                if(from!=null)cpt.setCounterparty_from(from);
                            }
                            Long to_firm=Utils.getLongParameter("counterprty_to_id",request);
                            if(to_firm!=null){
                                Counterparty to=counterpartyRepository.findOne(to_firm);
                                if(to!=null)cpt.setCounterparty_to(to);
                            }
                            Long from_person=Utils.getLongParameter("person_from_id",request);
                            if(from_person!=null){
                                Person from=personRepository.findOne(from_person);
                                if(from!=null)cpt.setPerson_from(from);
                            }
                            Long to_person=Utils.getLongParameter("person_to_id",request);
                            if(to_person!=null){
                                Person to=personRepository.findOne(to_person);
                                if(to!=null)cpt.setPerson_to(to);
                            }
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

    private String form_file_path(MultipartFile file,Document document){
        StringBuilder path_bulder=new StringBuilder("E:/DOCUMENTS/");
        if(document.getDocumentType()!=null&&document.getDocumentType().getName()!=null&&!document.getDocumentType().getName().isEmpty()){
            path_bulder.append(document.getDocumentType().getName());
            path_bulder.append("/");
        }
        if(document.getName()!=null && !document.getName().isEmpty()){
            path_bulder.append(document.getName());
            path_bulder.append("/");
        }
        path_bulder.append(file.getOriginalFilename());
        return path_bulder.toString();
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/editafile", method = {RequestMethod.POST,RequestMethod.GET})
    public void fileEditor(@RequestParam String oper,@RequestParam Long document_id,@RequestParam Long id,@RequestParam MultipartFile file,HttpServletResponse response)throws IOException {
        UserDetailsAdapter userDetail=(UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null){
            response.sendError(403, "FORBIDDEN!");
            return;
        }
        Document document=documentRepository.findOne(document_id);
        User creator=document.getCreationUser();
        if(document==null) {
            response.sendError(404, "NOT FOUND DOCUMENT ID:"+document_id);
            return;
        }
        switch (oper){
            case "add":
                if(file==null){
                    response.sendError(400, "BAD REQUEST! NO FILE SEND!");
                }
                if(userDetail.hasRole("ROLE_ADD_DOCUMENTS")||(creator!=null && creator.equals(userDetail.getUserP()))) {
                        DocumentFile fileInfo=new DocumentFile();
                        fileInfo.setFileName(file.getOriginalFilename());
                        fileInfo.setMimeType(file.getContentType());
                        fileInfo.setSize(file.getSize());
                        fileInfo.setDocument(document);
                        String path=form_file_path(file,document);
                        fileInfo.setFilePath(path);
                        File filesystem=new File(path);
                        if(filesystem.exists()){
                            response.sendError(406, "FILE EXIST: "+path);
                            return;
                        } else {
                            try {
                                Path pathToFile = Paths.get(path);
                                Files.createDirectories(pathToFile.getParent());
                                Files.createFile(pathToFile);
                            }catch (IOException ioe){
                                response.sendError(406, "ERROR: "+ioe.getLocalizedMessage());
                                return;
                            }
                        }
                        try(FileOutputStream stream=new FileOutputStream(path)) {
                            stream.write(file.getBytes());
                            stream.flush();
                        }catch (IOException io){
                            response.sendError(406, "ERROR: "+io.getLocalizedMessage());
                            return;
                        }
                        documentFileRepository.save(fileInfo);
                        documentRepository.save(document);
                        response.setStatus(200);
                } else {
                    response.sendError(403, "FORBIDDEN!");
                }
                break;
            case "edit":
                if(userDetail.hasRole("ROLE_EDIT_DOCUMENTS")||(creator!=null && creator.equals(userDetail.getUserP()))) {
                    DocumentFile oldFileInfo=documentFileRepository.findOne(id);
                    if(oldFileInfo!=null) {
                        File oldFile=new File(oldFileInfo.getFilePath());
                        oldFileInfo.setDocument(null);
                        documentFileRepository.delete(oldFileInfo);
                        response.setStatus(200);
                    } else {
                        response.sendError(404, "NOT FOUND FILE ID:"+id);
                        return;
                    }
                      DocumentFile fileInfo=new DocumentFile();
                        fileInfo.setFileName(file.getOriginalFilename());
                        fileInfo.setMimeType(file.getContentType());
                        fileInfo.setSize(file.getSize());
                        fileInfo.setDocument(document);
                        String path=form_file_path(file,document);
                        fileInfo.setFilePath(path);
                        File filesystem=new File(path);
                        if(filesystem.exists()){
                            response.sendError(406, "FILE EXIST: "+path);
                            return;
                        } else {
                            try {
                                Path pathToFile = Paths.get(path);
                                Files.createDirectories(pathToFile.getParent());
                                Files.createFile(pathToFile);
                            }catch (IOException ioe){
                                response.sendError(406, "ERROR: "+ioe.getLocalizedMessage());
                                return;
                            }
                        }
                        try(FileOutputStream stream=new FileOutputStream(path)) {
                            stream.write(file.getBytes());
                            stream.flush();
                        }catch (IOException io){
                            response.sendError(406, "ERROR: "+io.getLocalizedMessage());
                            return;
                        }
                        documentFileRepository.save(fileInfo);
                        documentRepository.save(document);
                        response.setStatus(200);
                } else {
                    response.sendError(403, "FORBIDDEN!");
                }
                break;
            case "del":
                if(userDetail.hasRole("ROLE_DELETE_DOCUMENTS")||(creator!=null && creator.equals(userDetail.getUserP()))) {
                    DocumentFile fileInfo=documentFileRepository.findOne(id);
                    if(fileInfo!=null) {
                        fileInfo.setDocument(null);
                        File oldFile=new File(fileInfo.getFilePath());
                        oldFile.delete();
                        documentFileRepository.delete(fileInfo);
                        response.setStatus(200);
                    } else {
                        response.sendError(404, "NOT FOUND FILE ID:"+id);
                    }
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

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }
}
