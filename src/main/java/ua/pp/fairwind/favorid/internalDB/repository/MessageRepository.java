package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.Contact;
import ua.pp.fairwind.favorid.internalDB.model.messages.Message;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface MessageRepository extends JpaRepository<Message,Long>{
}
