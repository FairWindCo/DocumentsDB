package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.jgrid.Utils;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.Task;
import ua.pp.fairwind.favorid.internalDB.model.directories.TaskType;
import ua.pp.fairwind.favorid.internalDB.model.messages.MessageRecipient;
import ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy;
import ua.pp.fairwind.favorid.internalDB.repository.PersonRepository;
import ua.pp.fairwind.favorid.internalDB.repository.TaskRepository;
import ua.pp.fairwind.favorid.internalDB.repository.TaskTypeRepository;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by ������ on 14.10.2015.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    TaskTypeRepository taskTypeRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "tasks/my";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(Model model) {
        return "tasks/manage";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Task> getTable(HttpServletRequest request){
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
        String filterName=request.getParameter("description");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContains(filterName, pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContains(filterName));
            } else
                return new JGridRowsResponse<>(taskRepository.findAll());
        }
    }

    @Transactional(readOnly = true)
        @RequestMapping(value = "/controlledTask", method = RequestMethod.POST)
        @ResponseBody
        public JGridRowsResponse<Task> getTableControlledTask(HttpServletRequest request){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        String filterName=request.getParameter("description");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContainsAndCreationUser(filterName, userDetail.getUserP(), pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findByCreationUser(userDetail.getUserP(), pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContainsAndCreationUser(filterName, userDetail.getUserP()));
            } else
                return new JGridRowsResponse<>(taskRepository.findByCreationUser(userDetail.getUserP()));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/responsibleTask", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Task> getTableResponsibleTask(HttpServletRequest request){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        String filterName=request.getParameter("description");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContainsAndResponsible(filterName, userDetail.getUserPerson(), pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findByResponsible(userDetail.getUserPerson(), pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContainsAndResponsible(filterName, userDetail.getUserPerson()));
            } else
                return new JGridRowsResponse<>(taskRepository.findByResponsible(userDetail.getUserPerson()));
        }
    }

    public boolean updateTask(Task task,HttpServletRequest request,HttpServletResponse response) throws IOException {
        Long taskTypeId= Utils.getLongParameter("taskTypeId", request);
        Long responsible_id= Utils.getLongParameter("responsible_id", request);
        task.setDescription(request.getParameter("description"));
        task.setStartDate(Utils.getDateParameter("startDate", "dd-MM-yyyy", request));
        task.setDedLineDate(Utils.getDateParameter("dedLineDate", "dd-MM-yyyy", request));
        if(taskTypeId!=null) {
            TaskType typeTask = taskTypeRepository.findOne(taskTypeId);
            if(typeTask!=null){
                task.setTaskType(typeTask);
            } else {
                response.sendError(404,"TASK TYPE NOT FOUND!");
                return false;
            }
        } else {
            response.sendError(404,"TASK TYPE NOT FOUND!");
            return false;
        }
        if(responsible_id!=null) {
            Person responsePerson = personRepository.findOne(responsible_id);
            if(response!=null){
                task.setResponsible(responsePerson);
            } else {
                response.sendError(404,"TASK TYPE NOT FOUND!");
                return false;
            }
        } else {
            response.sendError(404,"PERSON NOT FOUND!");
            return false;
        }
        task.setEndDate(null);
        task.setEndControlDate(null);
        return true;
    }


    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,@RequestParam Long id,HttpServletRequest request,HttpServletResponse response)throws IOException {
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (oper){
            case "add": {
                Task task = new Task();
                if (updateTask(task, request, response)) {
                    task.setCreationUser(userDetail.getUserP());
                    taskRepository.save(task);
                    response.setStatus(200);
                }
            }break;
            case "edit":
                Task fromDB= taskRepository.getOne(id);
                if(fromDB!=null) {
                    if((fromDB.getCreationUser()==null) || ((!userDetail.getUserP().equals(fromDB.getCreationUser())&&(!(userDetail.getUserP().getUserID().equals(fromDB.getCreationUser().getUserID())))))) {
                        response.sendError(403, "FORBIDDEN!");
                        return;
                    }
                    Long version= Utils.getLongParameter("version",request);
                    if(version!=null && version>=fromDB.getVersion()) {
                        updateTask(fromDB,request,response);
                        fromDB.setModificationDate(new Date());
                        taskRepository.save(fromDB);
                        response.setStatus(200);
                    } else {
                        response.sendError(406,"ANOTHER TRANSACTION MODIFICATION");
                    }
                } else {
                    response.sendError(406,"NO CONTACT TYPE FOUND");
                }

                break;
            case "del":
                Task fromDBs= taskRepository.getOne(id);
                if(fromDBs!=null && (userDetail.getUserP().equals(fromDBs.getCreationUser())&&((userDetail.getUserP().getUserID().equals(fromDBs.getCreationUser().getUserID()))))) {
                    taskRepository.delete(id);
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
    @RequestMapping(value = "/executeTask", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Task> getTableExecuteTask(HttpServletRequest request){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContainsAndExecutors(filterName, userDetail.getUserPerson(), pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findByExecutors(userDetail.getUserPerson(), pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByDescriptionContainsAndExecutors(filterName, userDetail.getUserPerson()));
            } else
                return new JGridRowsResponse<>(taskRepository.findByExecutors(userDetail.getUserPerson()));
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/editRecipient", method = {RequestMethod.POST,RequestMethod.GET})
    public void editorRecipients(@RequestParam String oper,@RequestParam(required = false) Long id,@RequestParam long taskId,@RequestParam(required = false) Long recipientID,HttpServletResponse response)throws IOException {
        Task mes=taskRepository.findOne(taskId);
        if(mes==null){
            response.sendError(404,"Message not found!");
        }
        switch (oper){
            case "add": {
                Person person=personRepository.findOne(recipientID);
                if(person!=null) {
                    MessageRecipient recipint = new MessageRecipient();
                    recipint.setRecipient(person);
                    mes.addExecutor(person);
                    taskRepository.save(mes);
                    response.setStatus(200);
                } else {
                    response.sendError(404,"Person not found!");
                }
            }break;
            case "validate":{

            }break;
            case "del": {
                Person recipint = personRepository.findOne(id);
                mes.removeExecutor(recipint);
                taskRepository.save(mes);
                response.setStatus(200);
            }break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/excutors", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Person> getExecutors(@RequestParam long taskId,HttpServletRequest request){
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
            return new JGridRowsResponse<>(taskRepository.getExecutors(taskId, pageRequest));
        } else {
            return new JGridRowsResponse<>(taskRepository.getExecutors(taskId));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/executorPersons", method = RequestMethod.GET)
    @ResponseBody
    public Object userPersonsList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword,@RequestParam long taskid,HttpServletRequest request) {
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
                page = personRepository.findTaskUsersProxyBySurname("%" + qword[0] + "%", taskid, pager);
            } else {
                page = personRepository.findTaskUsersProxy(taskid, pager);
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
                    page = personRepository.findTaskUsersProxyBySurname("%" + qword[0] + "%", taskid, sort);
                } else {
                    page = personRepository.findTaskUsersProxy(taskid, sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }


}
