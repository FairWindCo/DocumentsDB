package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.MovementElements;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface MovementElementRepository extends JpaRepository<MovementElements,Long>{
    List<MovementElements> findByMovementId(long id);
    Page<MovementElements> findByMovementId(long id,Pageable pageRequest);
}
