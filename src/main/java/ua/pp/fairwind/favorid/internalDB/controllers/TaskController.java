package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.model.Task;
import ua.pp.fairwind.favorid.internalDB.repository.TaskRepository;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Сергей on 14.10.2015.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "documents_list";
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
        String filterName=request.getParameter("name");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByNameContains(filterName, pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByNameContains(filterName));
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
        String filterName=request.getParameter("name");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByNameContainsAndCreationUser(filterName, userDetail.getUserP(), pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findByCreationUser(userDetail.getUserP(), pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByNameContainsAndCreationUser(filterName, userDetail.getUserP()));
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
        String filterName=request.getParameter("name");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByNameContainsAndResponsible(filterName, userDetail.getUserPerson(), pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findByResponsible(userDetail.getUserPerson(), pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByNameContainsAndResponsible(filterName, userDetail.getUserPerson()));
            } else
                return new JGridRowsResponse<>(taskRepository.findByResponsible(userDetail.getUserPerson()));
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,Task task,BindingResult result,HttpServletResponse response)throws IOException {
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (oper){
            case "add":
                taskRepository.save(task);
                response.setStatus(200);
                break;
            case "edit":
                Task fromDB= taskRepository.getOne(task.getId());
                if(fromDB!=null) {
                    if(!userDetail.getUserP().equals(fromDB.getCreationUser())) {
                        response.sendError(403, "FORBIDDEN!");
                        return;
                    }
                    taskRepository.save(task);
                    if(task.getVersion()>=fromDB.getVersion()) {
                        fromDB.setName(task.getName());
                        fromDB.setDescription(task.getDescription());
                        fromDB.setDedLineDate(task.getDedLineDate());
                        fromDB.setEndDate(task.getEndDate());
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
                Task fromDBs= taskRepository.getOne(task.getId());
                if(fromDBs!=null && userDetail.getUserP().equals(fromDBs.getCreationUser())) {
                    taskRepository.delete(task.getId());
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
                return new JGridRowsResponse<>(taskRepository.findByNameContainsAndExecutors(filterName, userDetail.getUserPerson(), pageRequest));
            } else
                return new JGridRowsResponse<>(taskRepository.findByExecutors(userDetail.getUserPerson(), pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(taskRepository.findByNameContainsAndExecutors(filterName, userDetail.getUserPerson()));
            } else
                return new JGridRowsResponse<>(taskRepository.findByExecutors(userDetail.getUserPerson()));
        }
    }
}
