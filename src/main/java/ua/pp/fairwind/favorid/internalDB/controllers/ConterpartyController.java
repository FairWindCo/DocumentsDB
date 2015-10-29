package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Contact;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.proxy.CounterpartProxy;
import ua.pp.fairwind.favorid.internalDB.repository.AgrimentRepository;
import ua.pp.fairwind.favorid.internalDB.repository.ContactRepository;
import ua.pp.fairwind.favorid.internalDB.repository.CounterpartyRepository;
import ua.pp.fairwind.favorid.internalDB.repository.PersonRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Сергей on 07.10.2015.
 */
@Controller
@RequestMapping("/counterparts")
public class ConterpartyController {
    @Autowired
    CounterpartyRepository counterpartyRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    AgrimentRepository agreementRepository;
    @Autowired
    ContactRepository contactRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "counterparts_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Counterparty> getTable(HttpServletRequest request){
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
        String filterName=request.getParameter("userName");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(counterpartyRepository.findByShortNameContains(filterName, pageRequest));
            } else
            return new JGridRowsResponse<>(counterpartyRepository.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(counterpartyRepository.findByShortNameContains(filterName));
            } else
            return new JGridRowsResponse<>(counterpartyRepository.findAll());
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/agreements", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Agreement> getAgreements(HttpServletRequest request,@RequestParam Long ID){
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
                return new JGridRowsResponse<>(counterpartyRepository.getAgreements(ID, pageRequest));
        } else {
                return new JGridRowsResponse<>(counterpartyRepository.getAgreements(ID));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Person> getPersons(HttpServletRequest request,@RequestParam Long ID){
        PageRequest pageRequest=null;
        if(request.getParameter("nodeid")!=null && !request.getParameter("nodeid").isEmpty()){
            try {
                long nodeid = Integer.parseInt(request.getParameter("nodeid"));
                Person p = personRepository.getOne(nodeid);
                if (p != null) {
                    return new JGridRowsResponse<>(p.getSubordinates());
                }
            }catch (NumberFormatException e){
                //do nohting
            }

            return new JGridRowsResponse<>();
        } else {
            if (request.getParameter("page") != null) {
                int rows = 10;
                int page;
                try {
                    page = Integer.parseInt(request.getParameter("page")) - 1;
                    if (page < 0) page = 0;
                    rows = request.getParameter("rows") == null ? 10 : Integer.parseInt(request.getParameter("rows"));
                    if (request.getParameter("sidx") != null && !request.getParameter("sidx").isEmpty()) {
                        String direction = request.getParameter("sord");
                        pageRequest = new PageRequest(page, rows, "asc".equals(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, request.getParameter("sidx"));
                    } else {
                        pageRequest = new PageRequest(page, rows);
                    }
                } catch (NumberFormatException ex) {
                    //do nothing
                }
            }/**/
            if(pageRequest!=null){
                return new JGridRowsResponse<>(counterpartyRepository.getPersonsHead(ID, pageRequest));
            } else {
                return new JGridRowsResponse<>(counterpartyRepository.getPersonsHead(ID));
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
            return new JGridRowsResponse<>(counterpartyRepository.getContacts(ID, pageRequest));
        } else {
            return new JGridRowsResponse<>(counterpartyRepository.getContacts(ID));
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,Counterparty counterparty,BindingResult result,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":
                counterpartyRepository.save(counterparty);
                response.setStatus(200);
                break;
            case "edit":
                Counterparty cpt = counterpartyRepository.findOne(counterparty.getId());
                if (counterparty != null) {
                        if(cpt.getVersion() <= counterparty.getVersion()) {
                            cpt.setFullName(counterparty.getFullName());
                            cpt.setShortName(counterparty.getShortName());
                            counterpartyRepository.save(cpt);
                            response.setStatus(200);
                        } else {
                            response.sendError(400, "ANOTHER TRANSACTION MODIFICATION!");
                        }
                } else {
                    response.sendError(404, "NO Counterpart WITH ID " + counterparty.getId() + " FOUND");
                }

                break;
            case "del":
                counterpartyRepository.delete(counterparty.getId());
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/editcontact", method = {RequestMethod.POST,RequestMethod.GET})
    public void contacteditor(@RequestParam String oper,@RequestParam long ID,Contact contact,BindingResult result,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":{
                Counterparty counterparty= counterpartyRepository.findOne(ID);
                if(counterparty!=null) {
                    counterparty.addContact(contact);
                    contactRepository.save(contact);
                    counterpartyRepository.save(counterparty);
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
                Counterparty counterparty = counterpartyRepository.findOne(ID);
                if (counterparty != null) {
                    Contact agr=contactRepository.findOne(contact.getId());
                    if(agr!=null) {
                        counterparty.removeContact(agr);
                        contactRepository.delete(agr);
                        counterpartyRepository.save(counterparty);
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



    @Transactional(readOnly = false)
    @RequestMapping(value = "/editagreements", method = {RequestMethod.POST,RequestMethod.GET})
    public void agrimentsEditor(@RequestParam String oper,@RequestParam long ID,Agreement agreement,BindingResult result,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":{
                Counterparty counterparty= counterpartyRepository.findOne(ID);
                if(counterparty!=null) {
                    counterparty.addAgreement(agreement);
                    agreementRepository.save(agreement);
                    counterpartyRepository.save(counterparty);
                    response.setStatus(200);
                } else{
                    response.sendError(404, "NO COUNTERPART WITH ID " +ID+" FOUND");
                }
            }break;
            case "edit": {
                Agreement agr = agreementRepository.findOne(agreement.getId());
                if (agr != null && agr.getVersion() <= agreement.getVersion()) {
                    agr.setNumber(agreement.getNumber());
                    agr.setName(agreement.getName());
                    agr.setStartDate(agreement.getStartDate());
                    agr.setEndDate(agreement.getEndDate());
                    agr.setPlanEndDate(agreement.getPlanEndDate());
                    agreementRepository.save(agr);
                    response.setStatus(200);
                } else {
                    response.sendError(404, "NO Agreement WITH ID " + agreement.getId() + " FOUND");
                }
            }break;
            case "del": {
                Counterparty counterparty = counterpartyRepository.findOne(ID);
                if (counterparty != null) {
                    Agreement agr=agreementRepository.findOne(agreement.getId());
                    if(agr!=null) {
                        counterparty.removeAgreement(agr);
                        agreementRepository.delete(agr);
                        counterpartyRepository.save(counterparty);
                        response.setStatus(200);
                    } else {
                        response.sendError(404, "NO Agreement WITH ID " + agreement.getId() + " FOUND");
                    }
                } else {
                    response.sendError(404, "NO COUNTERPART WITH ID " + ID + " FOUND");
                }
            }break;
            default:
                response.sendError(406, "UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/editperson", method = {RequestMethod.POST,RequestMethod.GET})
    public void personsEditor(@RequestParam String oper,@RequestParam long ID,Person person,BindingResult result,HttpServletRequest request,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":{
                Counterparty counterparty= counterpartyRepository.findOne(ID);
                if(counterparty!=null) {
                    counterparty.addPerson(person);
                    String headIDS=request.getParameter("headID_primary_key");
                    if(headIDS!=null && !headIDS.isEmpty()){
                        try {
                            Long headid=Long.getLong(headIDS);
                            Person headPerson=personRepository.findOne(headid);
                            person.setHead(headPerson);
                        } catch (NumberFormatException e){
                            //do nothing
                        }
                    }
                    personRepository.save(person);
                    counterpartyRepository.save(counterparty);
                    response.setStatus(200);
                } else{
                    response.sendError(404, "NO COUNTERPART WITH ID " +ID+" FOUND");
                }
            }break;
            case "edit": {
                Person prsn = personRepository.findOne(person.getId());
                if (prsn != null && prsn.getVersion() <= person.getVersion()) {
                    prsn.setSurname(person.getSurname());
                    prsn.setFirstName(person.getFirstName());
                    prsn.setMiddleName(person.getMiddleName());
                    prsn.setComments(person.getComments());
                    prsn.setDate_of_birth(person.getDate_of_birth());
                    String headIDS=request.getParameter("headID_primary_key");
                    if(headIDS!=null && !headIDS.isEmpty()){
                        try {
                            Long headid=Long.getLong(headIDS);
                            Person headPerson=personRepository.findOne(headid);
                            person.setHead(headPerson);
                        } catch (NumberFormatException e){
                            //do nothing
                        }
                    }
                    personRepository.save(prsn);
                    response.setStatus(200);
                } else {
                    response.sendError(404, "NO Agreement WITH ID " + person.getId() + " FOUND");
                }
            }break;
            case "del": {
                Counterparty counterparty = counterpartyRepository.findOne(ID);
                if (counterparty != null) {
                    Person prsn=personRepository.findOne(person.getId());
                    if(prsn!=null) {
                        counterparty.removePerson(prsn);
                        personRepository.delete(prsn);
                        counterpartyRepository.save(counterparty);
                        response.setStatus(200);
                    } else {
                        response.sendError(404, "NO Agreement WITH ID " + person.getId() + " FOUND");
                    }
                } else {
                    response.sendError(404, "NO COUNTERPART WITH ID " + ID + " FOUND");
                }
            }break;
            default:
                response.sendError(406, "UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleClientList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword) {
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"shortName");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            page_num= page_num<1?1:page_num;
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<CounterpartProxy> page;
            if (qword != null && qword.length > 0) {
                page = counterpartyRepository.findCounterpart(qword[0], pager);
            } else {
                page = counterpartyRepository.findCounterpart(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Counterparty ft=null;
                if(key!=null) {
                    ft = counterpartyRepository.findOne(key);
                }
                return ft;
            } else {
                List<CounterpartProxy> page;
                if (qword != null && qword.length > 0) {
                    page = counterpartyRepository.findCounterpart("%" + qword[0] + "%", sort);
                } else {
                    page = counterpartyRepository.findCounterpart(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/showListAgreements", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleAgrimentsList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword,@RequestParam(required = false) Long counterpart_id) {
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"name");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            page_num= page_num<1?1:page_num;
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<Agreement> page;
            if (qword != null && qword.length > 0) {
                if(counterpart_id!=null) {
                    page = agreementRepository.findAgreement(counterpart_id,qword[0], pager);
                } else {
                    page = agreementRepository.findAgreement(qword[0], pager);
                }
            } else {
                if(counterpart_id!=null) {
                    page = agreementRepository.findAgreement(counterpart_id,pager);
                } else {
                    page = agreementRepository.findAgreement(pager);
                }
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Agreement ft=null;
                if(key!=null) {
                    ft = agreementRepository.findOne(key);
                }
                return ft;
            } else {
                List<Agreement> page;
                if (qword != null && qword.length > 0) {
                    if(counterpart_id!=null) {
                        page = agreementRepository.findAgreement(counterpart_id,qword[0], sort);
                    } else {
                        page = agreementRepository.findAgreement(qword[0], sort);
                    }
                } else {
                    if(counterpart_id!=null) {
                        page = agreementRepository.findAgreement(counterpart_id,sort);
                    } else {
                        page = agreementRepository.findAgreement(sort);
                    }
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
