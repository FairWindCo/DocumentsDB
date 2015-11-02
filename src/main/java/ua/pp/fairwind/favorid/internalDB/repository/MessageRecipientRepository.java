package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.messages.MessageRecipient;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface MessageRecipientRepository extends JpaRepository<MessageRecipient,Long>{
    Page<MessageRecipient> findByMessageId(long messageid,Pageable pager);
    List<MessageRecipient> findByMessageId(long messageid);
}
