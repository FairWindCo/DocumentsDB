package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.MOVEMENT_TYPES;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Movement;

import java.util.List;


/**
 * Created by Сергей on 07.10.2015.
 */

public interface MovementRepository extends JpaRepository<Movement,Long>{
    Page<Movement> findByTypeMovement(MOVEMENT_TYPES typeMovement,Pageable pager);
    List<Movement> findByTypeMovement(MOVEMENT_TYPES typeMovement);

    @Query("select m.documents from Movement m where m.id=:movement")
    Page<Document> documents(@Param(value = "movement")long id,Pageable pager);
    @Query("select m.documents from Movement m where m.id=:movement")
    List<Document> documents(@Param(value = "movement")long id);
}
