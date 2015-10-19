package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface NomenclatureRepository extends JpaRepository<Nomenclature,Long>{
    @Query("select n from Nomenclature n where n.code like %:search% or n.name like %:search% or n.country like %:search% or n.manufacturer like %:search%")
    Page<Nomenclature> find(@Param(value = "search")String name, Pageable pager);
    @Query("select n from Nomenclature n where n.code like %:search% or n.name like %:search% or n.country like %:search% or n.manufacturer like %:search%")
    List<Nomenclature> find(@Param(value = "search")String name);
    @Query("select n from Nomenclature n where n.code like %:search% or n.name like %:search% or n.country like %:search% or n.manufacturer like %:search%")
    List<Nomenclature> find(@Param(value = "search")String name, Sort sort);
}
