package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.repository.AgrimentRepository;
import ua.pp.fairwind.favorid.internalDB.repository.CounterpartyRepository;
import ua.pp.fairwind.favorid.internalDB.repository.PersonRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,Counterparty counterparty,BindingResult result,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":
            case "edit":
                counterpartyRepository.save(counterparty);
                response.setStatus(200);
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
