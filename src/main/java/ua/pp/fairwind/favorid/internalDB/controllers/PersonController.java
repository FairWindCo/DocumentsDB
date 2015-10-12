package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.favorid.internalDB.jgrid.JSComboExpenseResp;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy;
import ua.pp.fairwind.favorid.internalDB.repository.PersonRepository;

import java.util.List;

/**
 * Created by Сергей on 12.10.2015.
 */
@Controller
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

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
}
