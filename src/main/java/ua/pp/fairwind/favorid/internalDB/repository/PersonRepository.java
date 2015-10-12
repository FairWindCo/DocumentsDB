package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface PersonRepository extends JpaRepository<Person,Long>{
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(p.id,p.surname,p.firstName,p.middleName,p.date_of_birth) from Person p where p.surname like :surname and p.counterparty.id=:firmID")
    Page<PersonProxy> findProxyBySurname(@Param("surname")String surname,@Param("firmID")long firmIDPageable, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(p.id,p.surname,p.firstName,p.middleName,p.date_of_birth) from Person p where p.surname like :surname and p.counterparty.id=:firmID")
    List<PersonProxy> findProxyBySurname(@Param("surname")String fio,@Param("firmID")long firmID,Sort sort);
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(p.id,p.surname,p.firstName,p.middleName,p.date_of_birth) from Person p where p.counterparty.id=:firmID")
    Page<PersonProxy> findProxyByFirm(@Param("firmID")long firmIDPageable, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(p.id,p.surname,p.firstName,p.middleName,p.date_of_birth) from Person p where p.counterparty.id=:firmID")
    List<PersonProxy> findProxyByFirm(@Param("firmID")long firmID,Sort sort);
}
