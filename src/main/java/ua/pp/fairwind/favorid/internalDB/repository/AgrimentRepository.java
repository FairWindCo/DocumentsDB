package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.favorid.internalDB.model.Agreement;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface AgrimentRepository extends JpaRepository<Agreement,Long>{
    @Query("select a from Agreement a where a.number like %:search% or a.name like %:search%")
    Page<Agreement> findAgreement(@Param("search")String search,Pageable pager);
    @Query("select a from Agreement a where a.number like %:search% or a.name like %:search%")
    List<Agreement> findAgreement(@Param("search")String search,Sort sort);
    @Query("select a from Agreement a")
    Page<Agreement> findAgreement(Pageable pager);
    @Query("select a from Agreement a")
    List<Agreement> findAgreement(Sort sort);


    @Query("select a from Agreement a where (a.number like %:search% or a.name like %:search%) and a.counterparty.id=:id")
    Page<Agreement> findAgreement(@Param("id") long id,@Param("search")String search,Pageable pager);
    @Query("select a from Agreement a where (a.number like %:search% or a.name like %:search%) and a.counterparty.id=:id")
    List<Agreement> findAgreement(@Param("id") long id,@Param("search")String search,Sort sort);
    @Query("select a from Agreement a where a.counterparty.id=:id")
    Page<Agreement> findAgreement(@Param("id") long id,Pageable pager);
    @Query("select a from Agreement a where a.counterparty.id=:id")
    List<Agreement> findAgreement(@Param("id") long id,Sort sort);
}
