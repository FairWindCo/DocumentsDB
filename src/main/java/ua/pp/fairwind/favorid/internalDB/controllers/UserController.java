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
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.administrative.Role;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.repository.PersonRepository;
import ua.pp.fairwind.favorid.internalDB.repository.RoleRepository;
import ua.pp.fairwind.favorid.internalDB.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by Сергей on 07.10.2015.
 */
@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PersonRepository personRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "user_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<User> getTable(HttpServletRequest request){
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
        String filterName=request.getParameter("userName");
        if(pageRequest!=null){
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(userRepository.findByUserNameContains(filterName, pageRequest));
            } else
            return new JGridRowsResponse<>(userRepository.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(userRepository.findByUserNameContains(filterName));
            } else
            return new JGridRowsResponse<>(userRepository.findAll());
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Role> getRoleTable(HttpServletRequest request,@RequestParam Long userID){
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
                return new JGridRowsResponse<>(userRepository.getUserRoles(userID, pageRequest));
        } else {
                return new JGridRowsResponse<>(userRepository.getUserRoles(userID));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/avaibleroles", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public List<Role> getAvaibleRoles(@RequestParam long userID){
        return userRepository.getAvaibleUserRoles(userID);
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,User user,BindingResult result,HttpServletRequest request,HttpServletResponse response)throws IOException{
        if(result.hasErrors()){
            response.sendError(400,result.toString());
            return;
        }
        switch (oper){
            case "add":
                String pk=request.getParameter("personIDkey");
                if(pk!=null){
                    try {
                        Long pid=Long.getLong(pk);
                        Person person=personRepository.findOne(pid);
                        if(person!=null){
                            user.setPerson(person);
                        }
                    }catch (NumberFormatException e){
                        //do nothing
                    }
                }
                userRepository.save(user);
                response.setStatus(200);
                break;
            case "edit":
                User usr=userRepository.getOne(user.getUserID());
                if(usr!=null) {
                    if( usr.getVersionId() <= user.getVersionId()) {
                        usr.setEnabled(user.isEnabled());
                        usr.setPasswordHash(user.getPasswordHash());
                        usr.setUserName(user.getUserName());
                        String pkey=request.getParameter("personIDkey");
                        if(pkey!=null) {
                            try {
                                Long pid = Long.getLong(pkey);
                                Person person = personRepository.findOne(pid);
                                if (person != null) {
                                    usr.setPerson(person);
                                }
                            } catch (NumberFormatException e) {
                                //do nothing
                            }
                        }
                        userRepository.save(usr);
                        response.setStatus(200);
                    } else {
                        response.sendError(400, "ANOTHER TRANSACTION MODIFICATION");
                    }
                } else {
                    response.sendError(404, "NO USER WITH ID " + user.getUserID() + " FOUND");
                }
                break;
            case "del":
                userRepository.delete(user.getUserID());
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }



    @Transactional(readOnly = false)
    @RequestMapping(value = "/editroles", method = {RequestMethod.POST,RequestMethod.GET})
    public void rulesEditor(@RequestParam String oper,@RequestParam long userID,@RequestParam long role_id,HttpServletResponse response)throws IOException{
        switch (oper){
            case "add":{
                Role role=roleRepository.getOne(role_id);
                User user=userRepository.findOne(userID);
                if(user!=null && role!=null) {
                    user.getUserRoles().add(role);
                    userRepository.save(user);
                }
                response.setStatus(200);}
                break;
            case "del":{
                Role role=roleRepository.getOne(role_id);
                User user=userRepository.findOne(userID);
                if(user!=null && role!=null) {
                    user.getUserRoles().remove(role);
                    userRepository.save(user);
                }
                response.setStatus(200);}
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

}
