package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.directories.ContactType;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface ContactTypeRepository extends JpaRepository<ContactType,Long>{
    Page<ContactType> findByNameContains(String name,Pageable pager);
    List<ContactType> findByNameContains(String name);
    List<ContactType> findByNameContains(String name,Sort sort);
}
