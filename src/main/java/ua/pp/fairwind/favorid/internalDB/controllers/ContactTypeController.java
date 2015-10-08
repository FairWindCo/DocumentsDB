package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.favorid.internalDB.jgrid.JGridRowsResponse;
import ua.pp.fairwind.favorid.internalDB.model.directories.ContactType;
import ua.pp.fairwind.favorid.internalDB.repository.ContactTypeRepository;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Сергей on 07.10.2015.
 */
@Controller
@RequestMapping("/contacttypes")
public class ContactTypeController {
    @Autowired
    ContactTypeRepository repositoryContactType;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "contact_type_list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<ContactType> getTable(HttpServletRequest request){
        return new JGridRowsResponse<ContactType>(repositoryContactType.findAll());
    }

}
