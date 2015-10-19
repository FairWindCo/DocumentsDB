package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Storehouse;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface StoreHouseRepository extends JpaRepository<Storehouse,Long>{
    @Query("select n from Storehouse n where n.location like %:search% or n.name like %:search% or n.number like %:search%")
    Page<Storehouse> find(@Param(value = "search") String name, Pageable pager);
    @Query("select n from Storehouse n where n.location like %:search% or n.name like %:search% or n.number like %:search%")
    List<Storehouse> find(@Param(value = "search") String name);
    @Query("select n from Storehouse n where n.location like %:search% or n.name like %:search% or n.number like %:search%")
    List<Storehouse> find(@Param(value = "search") String name, Sort sort);
}
