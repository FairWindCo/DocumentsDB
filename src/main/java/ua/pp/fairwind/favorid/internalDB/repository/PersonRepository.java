package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.Contact;
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


    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u join u.userRoles r where r.name='ROLE_SUBSCRIBE_DOCUMENT' and u.person.surname like :surname ")
    Page<PersonProxy> findSubscriberProxyBySurname(@Param("surname")String surname,Pageable pageRequest);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u join u.userRoles r where r.name='ROLE_SUBSCRIBE_DOCUMENT' and u.person.surname like :surname ")
    List<PersonProxy> findSubscriberProxyBySurname(@Param("surname")String fio,Sort sort);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u join u.userRoles r where r.name='ROLE_SUBSCRIBE_DOCUMENT'")
    Page<PersonProxy> findSubscriberProxy(Pageable pageRequest);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u join u.userRoles r where r.name='ROLE_SUBSCRIBE_DOCUMENT'")
    List<PersonProxy> findSubscriberProxy(Sort sort);


    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u where u.person.surname like :surname ")
    Page<PersonProxy> findUsersProxyBySurname(@Param("surname")String surname,Pageable pageRequest);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u where u.person.surname like :surname ")
    List<PersonProxy> findUsersProxyBySurname(@Param("surname")String fio,Sort sort);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u")
    Page<PersonProxy> findUsersProxy(Pageable pageRequest);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u")
    List<PersonProxy> findUsersProxy(Sort sort);


    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u where u.person not in (select distinct r.recipient from Message m join m.recipientSet r where m.id=:messageid) and  u.person.surname like :surname ")
    Page<PersonProxy> findMessageUsersProxyBySurname(@Param("surname")String surname,@Param("messageid")long messageid,Pageable pageRequest);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u where u.person not in (select distinct r.recipient from Message m join m.recipientSet r where m.id=:messageid) and  u.person.surname like :surname ")
    List<PersonProxy> findMessageUsersProxyBySurname(@Param("surname")String fio,@Param("messageid")long messageid,Sort sort);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u where u.person not in (select distinct r.recipient from Message m join m.recipientSet r where m.id=:messageid)")
    Page<PersonProxy> findMessageUsersProxy(@Param("messageid")long messageid,Pageable pageRequest);
    @Query("Select distinct new ua.pp.fairwind.favorid.internalDB.model.proxy.PersonProxy(u.person.id,u.person.surname,u.person.firstName,u.person.middleName,u.person.date_of_birth) from User u where u.person not in (select distinct r.recipient from Message m join m.recipientSet r where m.id=:messageid)")
    List<PersonProxy> findMessageUsersProxy(@Param("messageid")long messageid,Sort sort);



    @Query("select c from Contact c,Person  p where p.id=:ID and c member of p.contacts")
    Page<Contact> getContacts(@Param("ID") long id, Pageable pager);
    @Query("select c from Contact c,Person  p where p.id=:ID and c member of p.contacts")
    List<Contact> getContacts(@Param("ID") long id);
}
