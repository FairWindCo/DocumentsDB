package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.administrative.Role;

import java.util.List;

/**
 * Created by Сергей on 10.10.2015.
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    Page<Role> findByNameContains(String name,Pageable pager);
    List<Role> findByNameContains(String name);

}
