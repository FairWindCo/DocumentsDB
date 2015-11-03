package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.Task;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface TaskRepository extends JpaRepository<Task,Long>{
    Page<Task> findByDescriptionContains(String name,Pageable pageRequest);
    List<Task> findByDescriptionContains(String name);

    Page<Task> findByDescriptionContainsAndCreationUser(String name,User created,Pageable pageRequest);
    List<Task> findByDescriptionContainsAndCreationUser(String name,User created);
    Page<Task> findByCreationUser(User created,Pageable pageRequest);
    List<Task> findByCreationUser(User created);

    Page<Task> findByDescriptionContainsAndResponsible(String name,Person responsible,Pageable pageRequest);
    List<Task> findByDescriptionContainsAndResponsible(String name,Person responsible);
    Page<Task> findByResponsible(Person responsible,Pageable pageRequest);
    List<Task> findByResponsible(Person responsible);

    Page<Task> findByDescriptionContainsAndExecutors(String name,Person executor,Pageable pageRequest);
    List<Task> findByDescriptionContainsAndExecutors(String name,Person executor);
    Page<Task> findByExecutors(Person executor,Pageable pageRequest);
    List<Task> findByExecutors(Person executor);

    @Query("select p from Task t join t.executors p where t.id=:id")
    Page<Person> getExecutors(@Param(value = "id")long id,Pageable pageRequest);
    @Query("select p from Task t join t.executors p where t.id=:id")
    List<Person> getExecutors(@Param(value = "id")long id);
}
