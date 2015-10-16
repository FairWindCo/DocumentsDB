package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.proxy.DocumentProxy;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface DocumentRepository extends JpaRepository<Document,Long>{
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.DocumentProxy(d.id,d.documentType.name,d.number,d.name,d.counterparty_from.shortName,d.counterparty_to.shortName,d.person_from.surname,d.person_to.surname,d.creationDate) from Document d " +
            "where d.number like %:serach% or  d.name like %:serach% or d.counterparty_from.shortName like %:serach% or d.counterparty_to.shortName like %:serach%")
    Page<DocumentProxy> findProxy(@Param("serach") String serach, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.DocumentProxy(d.id,d.documentType.name,d.number,d.name,d.counterparty_from.shortName,d.counterparty_to.shortName,d.person_from.surname,d.person_to.surname,d.creationDate) from Document d " +
            "where d.number like %:serach% or  d.name like %:serach% or d.counterparty_from.shortName like %:serach% or d.counterparty_to.shortName like %:serach%")
    List<DocumentProxy> findProxy(@Param("serach") String serach, Sort sort);
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.DocumentProxy(d.id,d.documentType.name,d.number,d.name,d.counterparty_from.shortName,d.counterparty_to.shortName,d.person_from.surname,d.person_to.surname,d.creationDate) from Document d ")
    Page<DocumentProxy> findProxy(Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.DocumentProxy(d.id,d.documentType.name,d.number,d.name,d.counterparty_from.shortName,d.counterparty_to.shortName,d.person_from.surname,d.person_to.surname,d.creationDate) from Document d ")
    List<DocumentProxy> findProxy(Sort sort);

    Page<Document> findByName(String name,Pageable pageRequest);
    List<Document> findByName(String name);
}
