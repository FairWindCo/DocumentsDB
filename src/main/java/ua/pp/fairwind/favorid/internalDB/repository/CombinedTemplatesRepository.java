package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.CombinedTemplate;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface CombinedTemplatesRepository extends JpaRepository<CombinedTemplate,Long>{
    @Query("select ct from CombinedTemplate ct where ct.parent=:nomenclature and  ct.nomenclature.name like %:search%")
    Page<CombinedTemplate> find(@Param(value = "search") String name,@Param(value = "nomenclature")Nomenclature nm, Pageable pager);
    @Query("select ct from CombinedTemplate ct where ct.parent=:nomenclature")
    Page<CombinedTemplate> find(@Param(value = "nomenclature")Nomenclature nm, Pageable pager);
    @Query("select ct from CombinedTemplate ct where ct.parent=:nomenclature and  ct.nomenclature.name like %:search%")
    List<CombinedTemplate> find(@Param(value = "search") String name,@Param(value = "nomenclature")Nomenclature nm);
    @Query("select ct from CombinedTemplate ct where ct.parent=:nomenclature")
    List<CombinedTemplate> find(@Param(value = "nomenclature")Nomenclature nm);
    @Query("select ct from CombinedTemplate ct where ct.parent=:nomenclature and  ct.nomenclature.name like %:search%")
    List<CombinedTemplate> find(@Param(value = "search") String name,@Param(value = "nomenclature")Nomenclature nm, Sort sort);
}
