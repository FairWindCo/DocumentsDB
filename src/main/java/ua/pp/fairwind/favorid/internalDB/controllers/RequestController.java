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
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.requests.REQUEST_TYPES;
import ua.pp.fairwind.favorid.internalDB.model.requests.Request;
import ua.pp.fairwind.favorid.internalDB.model.requests.RequestItems;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Units;
import ua.pp.fairwind.favorid.internalDB.repository.*;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Сергей on 19.10.2015.
 */
@Controller
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    CounterpartyRepository counterpartyRepository;
    @Autowired
    AgrimentRepository agrimentRepository;
    @Autowired
    RequestItemRepository requestItemRepository;
    @Autowired
    NomenclatureRepository nomenclatureRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        return "request_list";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_purchase", method = RequestMethod.GET)
    public String listPurchase(Model model) {
        return "request_list_purchase";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_shipment", method = RequestMethod.GET)
    public String listShipment(Model model) {
        return "request_list_shipment";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_production", method = RequestMethod.GET)
    public String listProdaction(Model model) {
        return "request_list_prodaction";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_repair", method = RequestMethod.GET)
    public String listRepair(Model model) {
        return "request_list_repair";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Request> getTable(HttpServletRequest request){
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
                return new JGridRowsResponse<>(requestRepository.findAll(pageRequest));
        } else {
                return new JGridRowsResponse<>(requestRepository.findAll());
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing_type", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Request> getTypedTable(HttpServletRequest request,@RequestParam int type){
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
        REQUEST_TYPES rtype=REQUEST_TYPES.fromInteger(type);
        if(pageRequest!=null && rtype!=null){
            return new JGridRowsResponse<>(requestRepository.findByTypeRequest(rtype, pageRequest));
        } else {
            return new JGridRowsResponse<>(requestRepository.findByTypeRequest(rtype));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<RequestItems> getStates(HttpServletRequest request,@RequestParam long id){
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
                return new JGridRowsResponse<>(requestRepository.getState(id,filterName,pageRequest));
            } else
                return new JGridRowsResponse<>(requestRepository.getState(id, pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(requestRepository.getState(id, filterName));
            } else
                return new JGridRowsResponse<>(requestRepository.getState(id));
        }
    }

    public boolean update(HttpServletRequest req,Request request){
        Long version=Utils.getLongParameter("version",req);
        if(version!=null){
            if(version.longValue()<request.getVersion()){
                return false;
            }
        } else {
            if(request.getVersion()>0){
                return false;
            }
        }
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null){
            request.setResponsiblePerson(userDetail.getUserPerson());
        } else {
            return false;
        }
        Long counterparty_id=Utils.getLongParameter("counterpart_id",req);
        Long requesttype=Utils.getLongParameter("requesttype",req);
        Long agreement_id=Utils.getLongParameter("agreement_id", req);
        Long parent_request_id=Utils.getLongParameter("parent_request_id", req);
        if(counterparty_id!=null){
            Counterparty counterparty=counterpartyRepository.findOne(counterparty_id);
            request.setCounterparty(counterparty);
        }
        if(agreement_id!=null){
            Agreement agreement=agrimentRepository.findOne(agreement_id);
            request.setCounterparty(agreement.getCounterparty());
            request.setAgreement(agreement);
        }
        if(parent_request_id!=null){
            Request main_Request=requestRepository.findOne(parent_request_id);
            request.setParentRequest(main_Request);
        }
        request.setOperationDate(new Date());
        if(requesttype!=null) {
            request.setTypeRequest(REQUEST_TYPES.fromInteger(requesttype.intValue()));
        } else {
            request.setTypeRequest(REQUEST_TYPES.PURCHASE);
        }
        request.setComments(req.getParameter("comments"));
        if(request.getParentRequest()!=null) {
            if (request.getTypeRequest() == REQUEST_TYPES.SHIPMENT && request.getParentRequest().getTypeRequest()==REQUEST_TYPES.PRODUCTION) {
                request.getParentRequest().getItems().forEach(item -> {
                    RequestItems newitem = item.cloneForRequest();
                    request.addItem(newitem);
                });
            } else if (request.getTypeRequest() == REQUEST_TYPES.PURCHASE && request.getParentRequest().getTypeRequest()==REQUEST_TYPES.PRODUCTION) {
                request.getParentRequest().getItems().forEach(item -> {
                    if (item.getNomenclature().isCombined() && item.getNomenclature().getTemplates() != null) {
                        item.getNomenclature().getTemplates().forEach(templ -> {
                            if (templ.getNomenclature() != null) {
                                RequestItems newitem = new RequestItems();
                                newitem.setNomenclature(templ.getNomenclature());
                                newitem.setCount(templ.getCount());
                                newitem.setUnits(templ.getUnits());
                                request.addItem(newitem);
                            }
                        });
                    } else {
                        RequestItems newitem = item.cloneForRequest();
                        request.addItem(newitem);
                    }
                });

            } else if (request.getTypeRequest() == REQUEST_TYPES.PRODUCTION && request.getParentRequest().getTypeRequest()==REQUEST_TYPES.SHIPMENT) {
                request.getParentRequest().getItems().forEach(item -> {
                    RequestItems newitem = item.cloneForRequest();
                    request.addItem(newitem);
                });
            }
        }
        return true;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/items", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<RequestItems> getItemTable(HttpServletRequest request){
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
                return new JGridRowsResponse<>(requestItemRepository.findByNomenclatureNameContains(filterName, pageRequest));
            } else
                return new JGridRowsResponse<>(requestItemRepository.findAll(pageRequest));
        } else {
            if(filterName!=null && !filterName.isEmpty()){
                return new JGridRowsResponse<>(requestItemRepository.findByNomenclatureNameContains(filterName));
            } else
                return new JGridRowsResponse<>(requestItemRepository.findAll());
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void subscribe(@RequestParam long id,HttpServletResponse response)throws IOException {
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Request request = requestRepository.findOne(id);
        if (request == null) {
            response.sendError(404, "REQUEST NOT FOUND!");
            return;
        }
        if (request.isExecuted() || request.getApprovedPerson() != null) {
            response.sendError(403, "FORBIDDEN!");
            return;
        }
        request.setApprovedDate(new Date());
        request.setApprovedPerson(userDetail.getUserPerson());
        requestRepository.save(request);
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,@RequestParam long id,HttpServletRequest req,HttpServletResponse response)throws IOException {
        switch (oper){
            case "add":
                Request request=new Request();
                if(update(req, request)) {
                    requestRepository.save(request);
                    response.setStatus(200);
                }
                break;
            case "edit":
                Request fromDB= requestRepository.getOne(id);
                if(fromDB.isExecuted() || fromDB.getApprovedPerson()!=null){
                    response.sendError(403,"FORBIDDEN!");
                    return;
                }
                if(fromDB!=null) {
                    if(!update(req, fromDB)){
                        response.sendError(406,"ANOTHER TRANSACTION MODIFICATION");
                        return;
                    }
                    requestRepository.save(fromDB);
                    response.setStatus(200);
                } else {
                    response.sendError(406,"NO CONTACT TYPE FOUND");
                }

                break;
            case "del":
                requestRepository.delete(id);
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    public boolean updateItem(HttpServletRequest req,RequestItems item,Request parent){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long nomenclature_id=Utils.getLongParameter("counterparty_id",req);
        Long unittype=Utils.getLongParameter("unit",req);
        Long count=Utils.getLongParameter("count", req);

        if(nomenclature_id!=null){
            Nomenclature nomenclature=nomenclatureRepository.findOne(nomenclature_id);
            if(nomenclature==null) return false;
            item.setNomenclature(nomenclature);
        }
        item.setLastUpdate(new Date());
        if(unittype!=null) {
            item.setUnits(Units.fromInteger(unittype.intValue()));
        } else {
            item.setUnits(Units.COUNT);
        }
        item.setComments(req.getParameter("comments"));
        if(count!=null){
            item.setCount(count.longValue());
        } else {
            item.setCount(1L);
        }
        item.setRequest(parent);
        return true;
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST,RequestMethod.GET})
    public void itemEditor(@RequestParam String oper,@RequestParam long id,@RequestParam long requestid,HttpServletRequest req,HttpServletResponse response)throws IOException {
        Request request = requestRepository.findOne(requestid);
        if (request == null) {
            response.sendError(404, "REQUEST NOT FOUND!");
            return;
        }
        if (request.isExecuted() || request.getApprovedPerson() != null) {
            response.sendError(403, "FORBIDDEN!");
            return;
        }
        switch (oper){
            case "add": {
                RequestItems item = new RequestItems();
                updateItem(req, item, request);
                requestRepository.save(request);
                requestRepository.save(request);
                response.setStatus(200);
                }break;
            case "edit": {
                RequestItems item=requestItemRepository.findOne(id);
                if (item != null) {
                    if(!updateItem(req, item, request)){
                        response.sendError(406, "ANOTHER TRANSACTION MODIFICATION");
                    }
                    requestItemRepository.save(item);
                    requestRepository.save(request);
                    response.setStatus(200);
                } else {
                    response.sendError(406, "NO ITEM OF REQUEST FOUND");
                }

            }break;
            case "del":
                requestRepository.delete(id);
                requestRepository.save(request);
                response.setStatus(200);
                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleClientList(@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword) {
        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"name");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            page_num= page_num<1?1:page_num;
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<Request> page;
            if (qword != null && qword.length > 0) {
                page = requestRepository.find(qword[0], pager);
            } else {
                page = requestRepository.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Request ft=null;
                if(key!=null) {
                    ft = requestRepository.findOne(key);
                }
                return ft;
            } else {
                List<Request> page;
                if (qword != null && qword.length > 0) {
                    page = requestRepository.find(qword[0],sort);
                } else {
                    page = requestRepository.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }
}
