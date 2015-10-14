package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.directories.Position;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface PositionRepository extends JpaRepository<Position,Long>{
    Page<Position> findByNameContains(String name, Pageable pager);
    List<Position> findByNameContains(String name);
    List<Position> findByNameContains(String name,Sort sort);
}
