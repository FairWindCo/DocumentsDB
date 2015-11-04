package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.document.DOCUMENT_CLASS;
import ua.pp.fairwind.favorid.internalDB.model.document.DocumentType;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */

public interface DocumentTypeRepository extends JpaRepository<DocumentType,Long>{
    Page<DocumentType> findByNameContains(String name, Pageable pager);
    List<DocumentType> findByNameContains(String name);
    List<DocumentType> findByNameContains(String name,Sort sort);
    Page<DocumentType> findByNameContainsAndDocumentClass(String name,DOCUMENT_CLASS documentclass, Pageable pager);
    List<DocumentType> findByNameContainsAndDocumentClass(String name,DOCUMENT_CLASS documentclass);
    List<DocumentType> findByNameContainsAndDocumentClass(String name,DOCUMENT_CLASS documentclass,Sort sort);
    Page<DocumentType> findByDocumentClass(DOCUMENT_CLASS documentclass, Pageable pager);
    List<DocumentType> findByDocumentClass(DOCUMENT_CLASS documentclass);
    List<DocumentType> findByDocumentClass(DOCUMENT_CLASS documentclass,Sort sort);
}
