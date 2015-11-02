package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.messages.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface MessageRepository extends JpaRepository<Message,Long>{
    Page<Message> findByCreationUser(User creationUser,Pageable pager);
    List<Message> findByCreationUser(User creationUser);
    @Query("select m from Message m join m.recipientSet r where r.recipient=:person")
    Page<Message> getForMe(@Param("person")Person person,Pageable pager);
    @Query("select m from Message m join m.recipientSet r where r.recipient=:person")
    List<Message> getForMe(@Param("person")Person person);
    @Query("select m from Message m join m.recipientSet r where r.recipient=:person and (m.actual is null or m.actual>=:now) and r.validationDate is null")
    Page<Message> getActualForMe(@Param("person")Person person,@Param("now")Date date,Pageable pager);
    @Query("select m from Message m join m.recipientSet r where r.recipient=:person and (m.actual is null or m.actual>=:now) and r.validationDate is null")
    List<Message> getActualForMe(@Param("person")Person person,@Param("now")Date date);
}
