package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.Task;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface TaskRepository extends JpaRepository<Task,Long>{
    Page<Task> findByNameContains(String name,Pageable pageRequest);
    List<Task> findByNameContains(String name);

    Page<Task> findByNameContainsAndCreationUser(String name,User created,Pageable pageRequest);
    List<Task> findByNameContainsAndCreationUser(String name,User created);
    Page<Task> findByCreationUser(User created,Pageable pageRequest);
    List<Task> findByCreationUser(User created);

    Page<Task> findByNameContainsAndResponsible(String name,Person responsible,Pageable pageRequest);
    List<Task> findByNameContainsAndResponsible(String name,Person responsible);
    Page<Task> findByResponsible(Person responsible,Pageable pageRequest);
    List<Task> findByResponsible(Person responsible);

    Page<Task> findByNameContainsAndExecutors(String name,Person executor,Pageable pageRequest);
    List<Task> findByNameContainsAndExecutors(String name,Person executor);
    Page<Task> findByExecutors(Person executor,Pageable pageRequest);
    List<Task> findByExecutors(Person executor);
}
