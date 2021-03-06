package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Contact;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.proxy.CounterpartProxy;

import java.util.List;

/**
 * Created by ������ on 07.10.2015.
 */

public interface CounterpartyRepository extends JpaRepository<Counterparty,Long>{
    Page<Counterparty> findByShortNameContains(String shortName, Pageable pager);
    List<Counterparty> findByShortNameContains(String ShortName);
    List<Counterparty> findByShortNameContains(String ShortName,Sort sort);
    //@Query("select r from User u join u.userRoles r where u.userID=:userID")
    @Query("select a from Agreement a join a.counterparty c where c.id=:ID")
    Page<Agreement> getAgreements(@Param("ID") long id, Pageable pager);
    @Query("select a from Agreement a join a.counterparty c where c.id=:ID")
    List<Agreement> getAgreements(@Param("ID") long id);
    @Query("select p from Person p join p.counterparty c where c.id=:ID and p.head is null ")
    Page<Person> getPersonsHead(@Param("ID") long id, Pageable pager);
    @Query("select p from Person p join p.counterparty c where c.id=:ID and p.head is null ")
    List<Person> getPersonsHead(@Param("ID") long id);
    @Query("select p from Person p join p.counterparty c where c.id=:ID")
    Page<Person> getPersons(@Param("ID") long id, Pageable pager);
    @Query("select p from Person p join p.counterparty c where c.id=:ID")
    List<Person> getPersons(@Param("ID") long id);
    @Query("select c from Contact c,Counterparty  f where f.id=:ID and c member of f.contacts")
    Page<Contact> getContacts(@Param("ID") long id, Pageable pager);
    @Query("select c from Contact c,Counterparty  f where f.id=:ID and c member of f.contacts")
    List<Contact> getContacts(@Param("ID") long id);

    @Query("select new ua.pp.fairwind.favorid.internalDB.model.proxy.CounterpartProxy(c.id,c.shortName,c.fullName) from Counterparty c where c.shortName like %:search%")
    Page<CounterpartProxy> findCounterpart(@Param("search")String search,Pageable pager);
    @Query("select new ua.pp.fairwind.favorid.internalDB.model.proxy.CounterpartProxy(c.id,c.shortName,c.fullName) from Counterparty c where c.shortName like %:search%")
    List<CounterpartProxy> findCounterpart(@Param("search")String search,Sort sort);
    @Query("select new ua.pp.fairwind.favorid.internalDB.model.proxy.CounterpartProxy(c.id,c.shortName,c.fullName) from Counterparty c")
    Page<CounterpartProxy> findCounterpart(Pageable pager);
    @Query("select new ua.pp.fairwind.favorid.internalDB.model.proxy.CounterpartProxy(c.id,c.shortName,c.fullName) from Counterparty c")
    List<CounterpartProxy> findCounterpart(Sort sort);

}
