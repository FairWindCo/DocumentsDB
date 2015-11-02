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
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.jgrid.Utils;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.messages.Message;
import ua.pp.fairwind.favorid.internalDB.model.messages.MessageRecipient;
import ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy;
import ua.pp.fairwind.favorid.internalDB.repository.MessageRecipientRepository;
import ua.pp.fairwind.favorid.internalDB.repository.MessageRepository;
import ua.pp.fairwind.favorid.internalDB.repository.PersonRepository;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Сергей on 31.10.2015.
 */
@Controller
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageRecipientRepository messageRecipientRepository;
    @Autowired
    PersonRepository personRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "messages/manage";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public String listMy(Model model) {
        return "messages/my";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/actual", method = RequestMethod.GET)
    public String listActual(Model model) {
        return "messages/actual_for_me";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/for_me", method = RequestMethod.GET)
    public String listForMe(Model model) {
        return "messages/for_me";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Message> getTable(HttpServletRequest request){
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
        if(pageRequest!=null){
                return new JGridRowsResponse<>(messageRepository.findAll(pageRequest));
        } else {
                return new JGridRowsResponse<>(messageRepository.findAll());
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/actual_for_me_listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Message> getActialForMeMessages(HttpServletRequest request){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            return new JGridRowsResponse<>();
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
        if(pageRequest!=null){
            return new JGridRowsResponse<>(messageRepository.getActualForMe(userDetail.getUserPerson(), new Date(), pageRequest));
        } else {
            return new JGridRowsResponse<>(messageRepository.getActualForMe(userDetail.getUserPerson(), new Date()));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/for_me_listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Message> getForMeMessages(HttpServletRequest request){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            return new JGridRowsResponse<>();
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
        if(pageRequest!=null){
            return new JGridRowsResponse<>(messageRepository.getForMe(userDetail.getUserPerson(), pageRequest));
        } else {
            return new JGridRowsResponse<>(messageRepository.getForMe(userDetail.getUserPerson()));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/my_listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Message> getMyMessages(HttpServletRequest request){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserP()==null){
            return new JGridRowsResponse<>();
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
        if(pageRequest!=null){
            return new JGridRowsResponse<>(messageRepository.findByCreationUser(userDetail.getUserP(), pageRequest));
        } else {
            return new JGridRowsResponse<>(messageRepository.findByCreationUser(userDetail.getUserP()));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/recipients", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<MessageRecipient> getMessageRecipitnts(@RequestParam long messageId,HttpServletRequest request){
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
        if(pageRequest!=null){
            return new JGridRowsResponse<>(messageRecipientRepository.findByMessageId(messageId, pageRequest));
        } else {
            return new JGridRowsResponse<>(messageRecipientRepository.findByMessageId(messageId));
        }
    }


    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,Message message,BindingResult result,HttpServletResponse response)throws IOException {
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            response.sendError(403,"FORBIDDEN!");
            return;
        }
        switch (oper){
            case "add":
                message.setCreationDate(new Date());
                message.setCreationUser(userDetail.getUserP());
                messageRepository.save(message);
                response.setStatus(200);
                break;
            case "edit":
                Message fromDB= messageRepository.getOne(message.getId());
                if(fromDB!=null) {
                    fromDB.setMessageText(message.getMessageText());
                    fromDB.setActual(message.getActual());
                    fromDB.getRecipientSet().forEach(messageRecipient -> {
                        messageRecipient.setValidationDate(null);
                        messageRecipientRepository.save(messageRecipient);
                    });
                    messageRepository.save(fromDB);
                    response.setStatus(200);
                } else {
                    response.sendError(406,"NO CONTACT TYPE FOUND");
                }

                break;
            case "del":
                messageRepository.delete(message.getId());
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/editRecipient", method = {RequestMethod.POST,RequestMethod.GET})
    public void editorRecipients(@RequestParam String oper,@RequestParam(required = false) Long id,@RequestParam long messgeId,@RequestParam(required = false) Long recipientID,HttpServletResponse response)throws IOException {
        Message mes=messageRepository.findOne(messgeId);
        if(mes==null){
            response.sendError(404,"Message not found!");
        }
        switch (oper){
            case "add": {
                Person person=personRepository.findOne(recipientID);
                if(person!=null) {
                    MessageRecipient recipint = new MessageRecipient();
                    recipint.setRecipient(person);
                    mes.addMessageRecicpient(recipint);
                    messageRecipientRepository.save(recipint);
                    messageRepository.save(mes);
                    response.setStatus(200);
                } else {
                    response.sendError(404,"Person not found!");
                }
            }break;
            case "validate":{

            }break;
            case "del": {
                MessageRecipient recipint = messageRecipientRepository.findOne(id);
                mes.removeMessageRecicpient(recipint);
                messageRecipientRepository.delete(id);
                messageRepository.save(mes);
                response.setStatus(200);
            }break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/validate", method = {RequestMethod.POST,RequestMethod.GET})
    public void validateRecipients(@RequestParam(required = false) long messgeId,HttpServletResponse response)throws IOException {
        Message mes=messageRepository.findOne(messgeId);
        if(mes==null){
            response.sendError(404,"Message not found!");
        }
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            response.sendError(404,"No recipient found!");
        }
        mes.getRecipientSet().forEach(rec->{
            if(rec.getRecipient().getId().equals(userDetail.getUserPerson().getId())){
                rec.setValidationDate(new Date());
                messageRecipientRepository.save(rec);
            }
        });
        response.setStatus(200);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/messagePersons", method = RequestMethod.GET)
    @ResponseBody
    public Object userPersonsList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword,@RequestParam long messgaeId,HttpServletRequest request) {
        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        //Sort sort=new Sort(Sort.Direction.ASC,"recipient.surname");
        Sort sort= Utils.getJComboSortOrder(request);
        if(sort==null){
            sort=new Sort(Sort.Direction.ASC,"person.surname");
        }
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            page_num= page_num<1?1:page_num;
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<PersonProxy> page;
            if (qword != null && qword.length > 0) {
                page = personRepository.findMessageUsersProxyBySurname("%" + qword[0] + "%", messgaeId,pager);
            } else {
                page = personRepository.findMessageUsersProxy(messgaeId,pager);
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
                    page = personRepository.findMessageUsersProxyBySurname("%" + qword[0] + "%",messgaeId, sort);
                } else {
                    page = personRepository.findMessageUsersProxy(messgaeId,sort);
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
