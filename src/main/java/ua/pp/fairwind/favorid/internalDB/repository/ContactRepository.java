package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.Contact;

/**
 * Created by ������ on 07.10.2015.
 */

public interface ContactRepository extends JpaRepository<Contact,Long>{
}
