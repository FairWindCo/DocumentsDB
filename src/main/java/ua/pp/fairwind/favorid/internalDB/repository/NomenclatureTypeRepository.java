package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.NomenclatureTypes;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface NomenclatureTypeRepository extends JpaRepository<NomenclatureTypes,Long>{
    Page<NomenclatureTypes> findByNameContains(String name, Pageable pager);
    List<NomenclatureTypes> findByNameContains(String name);
    List<NomenclatureTypes> findByNameContains(String name, Sort sort);

    @Query("select ct from NomenclatureTypes ct where :nomenclature in ct.nomenclature and ct.name like %:search%")
    Page<NomenclatureTypes> find(@Param(value = "search") String name,@Param(value = "nomenclature")Nomenclature nm, Pageable pager);
    @Query("select ct from NomenclatureTypes ct where :nomenclature in ct.nomenclature")
    Page<NomenclatureTypes> find(@Param(value = "nomenclature")Nomenclature nm, Pageable pager);
    @Query("select ct from NomenclatureTypes ct where :nomenclature in ct.nomenclature and ct.name like %:search%")
    List<NomenclatureTypes> find(@Param(value = "search") String name,@Param(value = "nomenclature")Nomenclature nm);
    @Query("select ct from NomenclatureTypes ct where :nomenclature in ct.nomenclature")
    List<NomenclatureTypes> find(@Param(value = "nomenclature")Nomenclature nm);
    @Query("select ct from NomenclatureTypes ct where :nomenclature in ct.nomenclature and ct.name like %:search%")
    List<NomenclatureTypes> find(@Param(value = "search") String name,@Param(value = "nomenclature")Nomenclature nm, Sort sort);
}
