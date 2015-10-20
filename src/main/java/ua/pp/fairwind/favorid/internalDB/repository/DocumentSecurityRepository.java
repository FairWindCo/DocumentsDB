package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.document.DocumentSecurity;

import java.util.List;

/**
 * Created by Сергей on 20.10.2015.
 */
public interface DocumentSecurityRepository extends JpaRepository<DocumentSecurity,Long> {
    List<DocumentSecurity> findByDocument(Document document);
    Page<DocumentSecurity> findByDocument(Document document,Pageable pageRequest);
    List<DocumentSecurity> findByDocumentAndPersonSurname(Document document,String surname);
    Page<DocumentSecurity> findByDocumentAndPersonSurname(Document document,String surname,Pageable pageRequest);

}
