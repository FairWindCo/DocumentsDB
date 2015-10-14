package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.directories.TaskType;

import java.util.List;

/**
 * Created by ������ on 07.10.2015.
 */

public interface TaskTypeRepository extends JpaRepository<TaskType,Long>{
    Page<TaskType> findByNameContains(String name, Pageable pager);
    List<TaskType> findByNameContains(String name);
    List<TaskType> findByNameContains(String name,Sort sort);
}
