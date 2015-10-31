package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.messages.Message;
import ua.pp.fairwind.favorid.internalDB.model.messages.MessageRecipient;

/**
 * Created by ������ on 07.10.2015.
 */

public interface MessageRecipientRepository extends JpaRepository<MessageRecipient,Long>{
}
