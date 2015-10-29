package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.requests.REQUEST_TYPES;
import ua.pp.fairwind.favorid.internalDB.model.requests.Request;
import ua.pp.fairwind.favorid.internalDB.model.requests.RequestItems;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface RequestRepository extends JpaRepository<Request,Long>{
    Page<Request> findByTypeRequest(REQUEST_TYPES type,Pageable pager);
    List<Request> findByTypeRequest(REQUEST_TYPES type);

    @Query("select r from RequestItems r where r.request.id=:requestId")
    Page<RequestItems> getState(@Param(value = "requestId") long requestId, Pageable pager);
    @Query("select r from RequestItems r where r.request.id=:requestId")
    List<RequestItems> getState(@Param(value = "storehouseId") long requestId);
    @Query("select r from RequestItems r where r.request.id=:requestId")
    List<RequestItems> getState(@Param(value = "storehouseId") long requestId, Sort sort);


    @Query("select r from RequestItems r where r.request.id=:requestId and r.nomenclature.name like %:name%")
    Page<RequestItems> getState(@Param(value = "requestId") long requestId,@Param(value = "name")String name, Pageable pager);
    @Query("select r from RequestItems r where r.request.id=:requestId and r.nomenclature.name like %:name%")
    List<RequestItems> getState(@Param(value = "storehouseId") long requestId,@Param(value = "name")String name);
    @Query("select r from RequestItems r where r.request.id=:requestId and r.nomenclature.name like %:name%")
    List<RequestItems> getState(@Param(value = "storehouseId") long requestId,@Param(value = "name")String name, Sort sort);


    @Query("select r from Request r where r.id = :requestId or r.counterparty.shortName like %:requestId% or r.agreement.name like %:requestId%")
    Page<Request> find(@Param(value = "requestId") String requestId, Pageable pager);
    @Query("select r from Request r where r.id = :requestId or r.counterparty.shortName like %:requestId% or r.agreement.name like %:requestId%")
    List<Request> find(@Param(value = "storehouseId") String requestId);
    @Query("select r from Request r where r.id = :requestId or r.counterparty.shortName like %:requestId% or r.agreement.name like %:requestId%")
    List<Request> find(@Param(value = "storehouseId") String requestId, Sort sort);


}
