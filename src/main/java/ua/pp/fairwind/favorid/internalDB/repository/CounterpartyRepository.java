package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.administrative.Role;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface CounterpartyRepository extends JpaRepository<Counterparty,Long>{
    Page<Counterparty> findByShort_nameContains(String name, Pageable pager);
    List<Counterparty> findByShort_nameContains(String name);
    //@Query("select r from User u join u.userRoles r where u.userID=:userID")
    @Query("select a from Counterparty c join c.agriments a where c.id=:ID")
    Page<Agreement> getAgreements(@Param("ID") long id, Pageable pager);
    @Query("select a from Counterparty c join c.agriments a where c.id=:ID")
    List<Agreement> getAgreements(@Param("ID") long id);
    @Query("select p from Counterparty c join c.persons p where c.id=:ID")
    Page<Person> getPersons(@Param("ID") long id, Pageable pager);
    @Query("select p from Counterparty c join c.persons p where c.id=:ID")
    List<Person> getPersons(@Param("ID") long id);
}
