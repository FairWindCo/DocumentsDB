package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.requests.RequestItems;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface RequestItemRepository extends JpaRepository<RequestItems,Long>{
    Page<RequestItems> findByNomenclatureNameContains(String name,Pageable pager);
    List<RequestItems> findByNomenclatureNameContains(String name);
}
