package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Сергей on 07.10.2015.
 */
@Controller
public class IndexController {

    @Secured("ROLE_USER")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Model model) {
        return "index";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/notfound", method = RequestMethod.GET)
    public String eror404(Model model) {
        return "error404";
    }

    @RequestMapping(value = "/error403", method = RequestMethod.GET)
    public String eror403(Model model) {
        return "error403";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        return "login";
    }
}
