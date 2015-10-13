package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.model.Contact;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy;
import ua.pp.fairwind.favorid.internalDB.repository.ContactRepository;
import ua.pp.fairwind.favorid.internalDB.repository.PersonRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Сергей on 12.10.2015.
 */
@Controller
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ContactRepository contactRepository;

    @Transactional(readOnly = true)
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleClientList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword,@RequestParam long firmID) {
        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"surname");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<PersonProxy> page;
            if (qword != null && qword.length > 0) {
                page = personRepository.findProxyBySurname("%" + qword[0] + "%", firmID, pager);
            } else {
                page = personRepository.findProxyByFirm(firmID, pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Person ft=null;
                if(key!=null) {
                    ft = personRepository.findOne(key);
                }
                return ft;
            } else {
                List<PersonProxy> page;
                if (qword != null && qword.length > 0) {
                    page = personRepository.findProxyBySurname("%" + qword[0] + "%", firmID,sort);
                } else {
                    page = personRepository.findProxyByFirm(firmID,sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Contact> getContacts(HttpServletRequest request,@RequestParam Long ID){
        PageRequest pageRequest=null;
        if(request.getParameter("page")!=null){
            int rows=10;
            int page;
            try {
                page = Integer.parseInt(request.getParameter("page")) - 1;
                if(page<0)page=0;
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
        if(pageRequest!=null){
            return new JGridRowsResponse<>(personRepository.getContacts(ID, pageRequest));
        } else {
            return new JGridRowsResponse<>(personRepository.getContacts(ID));
        }
    }


    @Transactional(readOnly = false)
    @RequestMapping(value = "/editcontact", method = {RequestMethod.POST,RequestMethod.GET})
    public void contacteditor(@RequestParam String oper,@RequestParam long ID,Contact contact,BindingResult result,HttpServletResponse response)throws IOException {
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":{
                Person person= personRepository.findOne(ID);
                if(person!=null) {
                    person.addContact(contact);
                    contactRepository.save(contact);
                    personRepository.save(person);
                    response.setStatus(200);
                } else{
                    response.sendError(404, "NO COUNTERPART WITH ID " +ID+" FOUND");
                }
            }break;
            case "edit": {
                Contact agr = contactRepository.findOne(contact.getId());
                if (agr != null && agr.getVersion() <= contact.getVersion()) {
                    agr.setContact(contact.getContact());
                    agr.setContactType(contact.getContactType());
                    contactRepository.save(agr);
                    response.setStatus(200);
                } else {
                    response.sendError(404, "NO Agreement WITH ID " + contact.getId() + " FOUND");
                }
            }break;
            case "del": {
                Person person= personRepository.findOne(ID);
                if (person != null) {
                    Contact agr=contactRepository.findOne(contact.getId());
                    if(agr!=null) {
                        person.removeContact(agr);
                        contactRepository.delete(agr);
                        personRepository.save(person);
                        response.setStatus(200);
                    } else {
                        response.sendError(404, "NO Agreement WITH ID " + contact.getId() + " FOUND");
                    }
                } else {
                    response.sendError(404, "NO COUNTERPART WITH ID " + ID + " FOUND");
                }
            }break;
            default:
                response.sendError(406, "UNKNOWN OPERATION");
        }
    }
}
