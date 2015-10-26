package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Movement;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface MovementRepository extends JpaRepository<Movement,Long>{
}
