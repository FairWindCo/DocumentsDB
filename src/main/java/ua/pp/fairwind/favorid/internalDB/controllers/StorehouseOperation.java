package ua.pp.fairwind.favorid.internalDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import ua.pp.fairwind.favorid.internalDB.jgrid.Utils;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.*;
import ua.pp.fairwind.favorid.internalDB.repository.*;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Сергей on 26.10.2015.
 */
@Controller
@RequestMapping("/storehouse_operation")
public class StorehouseOperation {
    @Autowired
    MovementRepository movementRepository;
    @Autowired
    MovementElementRepository movementElementRepository;
    @Autowired
    NomenclatureRepository nomenclatureRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    CounterpartyRepository counterpartyRepository;
    @Autowired
    StoreHouseRepository storeHouseRepository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "storehouse_operation_list";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_special", method = RequestMethod.GET)
    public String list_special(Model model) {
        return "storehouse_operation_list_special";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_arrival", method = RequestMethod.GET)
    public String list_arrival(Model model) {
        return "storehouse_operation_list_arrival";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_shipment", method = RequestMethod.GET)
    public String list_shipment(Model model) {
        return "storehouse_operation_list_shipment";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_move", method = RequestMethod.GET)
    public String list_move(Model model) {
        return "storehouse_operation_list_move";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/list_utilization", method = RequestMethod.GET)
    public String list_utilization(Model model) {
        return "storehouse_operation_list_utilization";
    }


    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Movement> getTable(HttpServletRequest request){
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
                return new JGridRowsResponse<>(movementRepository.findAll(pageRequest));
        } else {
                return new JGridRowsResponse<>(movementRepository.findAll());
        }
    }

    private JGridRowsResponse<Movement> getOperations(HttpServletRequest request,MOVEMENT_TYPES type){
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
            return new JGridRowsResponse<>(movementRepository.findByTypeMovement(type, pageRequest));
        } else {
            return new JGridRowsResponse<>(movementRepository.findByTypeMovement(type));
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/arrivallisting", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Movement> getArrivalTable(HttpServletRequest request){
        return getOperations(request,MOVEMENT_TYPES.ARRIVAL);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/shipmentlisting", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Movement> getShipmentTable(HttpServletRequest request){
        return getOperations(request,MOVEMENT_TYPES.SHIPMENT);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/movelisting", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Movement> getMoveTable(HttpServletRequest request){
        return getOperations(request,MOVEMENT_TYPES.MOVE);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/utilizzationlisting", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Movement> getUtilizTable(HttpServletRequest request){
        return getOperations(request,MOVEMENT_TYPES.UTILIZATION);
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/elements", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<MovementElements> getElemetsTable(HttpServletRequest request,@RequestParam long id){
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
            return new JGridRowsResponse<>(movementElementRepository.findByMovementId(id, pageRequest));
        } else {
            return new JGridRowsResponse<>(movementElementRepository.findByMovementId(id));
        }
    }

    private boolean updateMovement(Movement movement,HttpServletRequest request,MOVEMENT_TYPES movement_type,HttpServletResponse response) throws IOException {
        if(movement==null||movement_type==null){
            response.sendError(406,"UNKNOWN OPERATION");
            return false;
        }
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            response.sendError(400,"NO USER!");
            return false;
        }
        switch (movement_type){
            case ARRIVAL:{
                Long storehouse_to_id= Utils.getLongParameter("storehouse_to_id",request);
                Long counterpart_id= Utils.getLongParameter("counterpart_id", request);
                if(storehouse_to_id==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                Storehouse to=storeHouseRepository.findOne(storehouse_to_id);
                if(to==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                Counterparty counterparty=null;
                if(counterpart_id!=null)
                    counterparty=counterpartyRepository.findOne(counterpart_id);
                movement.setToStorehouse(to);
                movement.setCounterparty(counterparty);
                movement.setResponsiblePerson(userDetail.getUserPerson());
                movement.setOperationDate(new Date());
                movement.setFromStorehouse(null);
                movement.setTypeMovement(movement_type);
                movement.setComments(request.getParameter("comments"));
                movement.setRequestNumber(request.getParameter("requestNumber"));
                return true;
            }
            case SHIPMENT:{
                Long storehouse_from_id= Utils.getLongParameter("storehouse_from_id",request);
                Long counterpart_id= Utils.getLongParameter("counterpart_id", request);
                if(storehouse_from_id==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                Storehouse from=storeHouseRepository.findOne(storehouse_from_id);
                if(from==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                if(counterpart_id==null){
                    response.sendError(400,"NO COUNTERPART!");
                    return false;
                }
                Counterparty counterparty=counterpartyRepository.findOne(counterpart_id);
                if(counterparty==null){
                    response.sendError(400,"NO COUNTERPART!");
                    return false;
                }
                movement.setFromStorehouse(from);
                movement.setCounterparty(counterparty);
                movement.setResponsiblePerson(userDetail.getUserPerson());
                movement.setOperationDate(new Date());
                movement.setToStorehouse(null);
                movement.setTypeMovement(movement_type);
                movement.setComments(request.getParameter("comments"));
                movement.setRequestNumber(request.getParameter("requestNumber"));
                return true;
            }
            case MOVE:{
                Long storehouse_from_id= Utils.getLongParameter("storehouse_from_id",request);
                Long storehouse_to_id= Utils.getLongParameter("storehouse_to_id",request);
                if(storehouse_from_id==null || storehouse_to_id==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                Storehouse to=storeHouseRepository.findOne(storehouse_to_id);
                Storehouse from=storeHouseRepository.findOne(storehouse_from_id);
                if(to==null||from==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                movement.setFromStorehouse(from);
                movement.setToStorehouse(to);
                movement.setResponsiblePerson(userDetail.getUserPerson());
                movement.setOperationDate(new Date());
                movement.setCounterparty(null);
                movement.setTypeMovement(movement_type);
                movement.setComments(request.getParameter("comments"));
                movement.setRequestNumber(request.getParameter("requestNumber"));
                return true;
            }
            case COMBINED:{
                Long storehouse_from_id= Utils.getLongParameter("storehouse_from_id",request);
                Long storehouse_to_id= Utils.getLongParameter("storehouse_to_id",request);
                if(storehouse_from_id==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                Storehouse from=storeHouseRepository.findOne(storehouse_from_id);
                Storehouse to=storeHouseRepository.findOne(storehouse_to_id);
                if(from==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                if(to==null)to=from;
                movement.setFromStorehouse(from);
                movement.setToStorehouse(to);
                movement.setResponsiblePerson(userDetail.getUserPerson());
                movement.setOperationDate(new Date());
                movement.setTypeMovement(movement_type);
                movement.setCounterparty(null);
                movement.setComments(request.getParameter("comments"));
                movement.setRequestNumber(request.getParameter("requestNumber"));
                return true;
            }
            case DEFECTIVE:{
                Long storehouse_from_id= Utils.getLongParameter("storehouse_from_id",request);
                Long storehouse_to_id= Utils.getLongParameter("storehouse_to_id",request);
                if(storehouse_from_id==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                Storehouse from=storeHouseRepository.findOne(storehouse_from_id);
                Storehouse to=storeHouseRepository.findOne(storehouse_to_id);
                if(from==null){
                    response.sendError(400,"NO STOREHOUSE!");
                    return false;
                }
                if(to==null)to=from;
                movement.setFromStorehouse(from);
                movement.setToStorehouse(to);
                movement.setResponsiblePerson(userDetail.getUserPerson());
                movement.setOperationDate(new Date());
                movement.setTypeMovement(movement_type);
                movement.setCounterparty(null);
                movement.setComments(request.getParameter("comments"));
                movement.setRequestNumber(request.getParameter("requestNumber"));
                return true;
            }

        }
        return false;
    }

    private MOVEMENT_TYPES getMovementType(HttpServletRequest request){
        Long movement_type_id= Utils.getLongParameter("typeMovement",request);
        if(movement_type_id!=null){
            return MOVEMENT_TYPES.fromInteger(movement_type_id.intValue());
        }
        return null;
    }



    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void editor(@RequestParam String oper,HttpServletRequest request,HttpServletResponse response)throws IOException {
        switch (oper){
            case "add": {
                MOVEMENT_TYPES types = getMovementType(request);
                if (types == null) {
                    response.sendError(406, "UNKNOWN OPERATION");
                    return;
                }
                Movement movement = new Movement();
                if(updateMovement(movement,request,types,response)) {
                    movementRepository.save(movement);
                    response.setStatus(200);
                }
            }
                break;
            case "edit": {
                Long id= Utils.getLongParameter("id",request);
                MOVEMENT_TYPES types = getMovementType(request);
                if (types == null || id==null) {
                    response.sendError(406, "UNKNOWN OPERATION");
                    return;
                }
                Movement fromDB = movementRepository.getOne(id);
                if(fromDB!=null) {
                    if (!fromDB.isApproved()) {
                        if(updateMovement(fromDB,request,types,response)) {
                            movementRepository.save(fromDB);
                            response.setStatus(200);
                        }
                    } else {
                        response.sendError(403, "FORBIDDEN!");
                    }
                } else {
                    response.sendError(404, "MOVEMENT WIDTH ID="+id+" mot found!");
                }
            }
                break;
            case "del":
                Long id= Utils.getLongParameter("id", request);
                if(id==null){
                    response.sendError(406,"UNKNOWN OPERATION");
                    return;
                }
                Movement fromDB = movementRepository.getOne(id);
                if(fromDB!=null) {
                    if (!fromDB.isApproved()) {
                        movementRepository.delete(id);
                        response.setStatus(200);
                    } else {
                        response.sendError(403, "FORBIDDEN!");
                    }
                } else {
                    response.sendError(404, "MOVEMENT WIDTH ID="+id+" mot found!");
                }

                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/documents", method = RequestMethod.POST)
    @ResponseBody
    public JGridRowsResponse<Document> getDocumentsTable(HttpServletRequest request,@RequestParam long id){
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
            return new JGridRowsResponse<>(movementRepository.documents(id, pageRequest));
        } else {
            return new JGridRowsResponse<>(movementRepository.documents(id));
        }
    }

    @Transactional(readOnly = false)
    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public void nomenclatureeditor(@RequestParam String oper,@RequestParam long moveid,HttpServletRequest request,HttpServletResponse response)throws IOException {
        Movement movement=movementRepository.findOne(moveid);
        if(movement==null){
            response.sendError(404,"Movement not found!");
            return;
        }
        switch (oper){
            case "add": {
                MovementElements element=new MovementElements();
                Long nomenclid=Utils.getLongParameter("nomenclature_id",request);
                if(nomenclid==null) {
                    response.sendError(404,"Nomenclature not found!");
                    return;
                }
                Nomenclature nom=nomenclatureRepository.findOne(nomenclid);
                if(nom==null) {
                    response.sendError(404,"Nomenclature not found!");
                    return;
                }
                Long cnt=Utils.getLongParameter("count",request);
                if(cnt==null){
                    response.sendError(400,"NO COUNT!");
                    return;
                }
                element.setNomenclature(nom);
                element.setComments(request.getParameter("comments"));
                element.setCount(cnt);
                element.setMovement(movement);
                //TO-DO: need units
                //element.setUnits();
                movementElementRepository.save(element);
                movementRepository.save(movement);
            }
            break;
            case "edit": {
                Long id= Utils.getLongParameter("id",request);
                MOVEMENT_TYPES types = getMovementType(request);
                if (types == null || id==null) {
                    response.sendError(406, "UNKNOWN OPERATION");
                    return;
                }
                Movement fromDB = movementRepository.getOne(id);
                if(fromDB!=null) {
                    if (!fromDB.isApproved()) {
                        if(updateMovement(fromDB,request,types,response)) {
                            movementRepository.save(fromDB);
                            response.setStatus(200);
                        }
                    } else {
                        response.sendError(403, "FORBIDDEN!");
                    }
                } else {
                    response.sendError(404, "MOVEMENT WIDTH ID="+id+" mot found!");
                }
            }
            break;
            case "del":
                Long id= Utils.getLongParameter("id", request);
                if(id==null){
                    response.sendError(406,"UNKNOWN OPERATION");
                    return;
                }
                Movement fromDB = movementRepository.getOne(id);
                if(fromDB!=null) {
                    if (!fromDB.isApproved()) {
                        movementRepository.delete(id);
                        response.setStatus(200);
                    } else {
                        response.sendError(403, "FORBIDDEN!");
                    }
                } else {
                    response.sendError(404, "MOVEMENT WIDTH ID="+id+" mot found!");
                }

                break;
            default:
                response.sendError(406,"UNKNOWN OPERATION");
        }
    }

}
