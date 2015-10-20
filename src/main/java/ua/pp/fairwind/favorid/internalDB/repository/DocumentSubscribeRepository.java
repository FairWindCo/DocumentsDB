package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.document.DocumentSubscribe;

import java.util.List;

/**
 * Created by Сергей on 20.10.2015.
 */
public interface DocumentSubscribeRepository extends JpaRepository<DocumentSubscribe,Long> {
    List<DocumentSubscribe> findByDocument(Document document);
    Page<DocumentSubscribe> findByDocument(Document document, Pageable pageRequest);
    List<DocumentSubscribe> findByDocumentAndPersonSurname(Document document, String surname);
    Page<DocumentSubscribe> findByDocumentAndPersonSurname(Document document, String surname, Pageable pageRequest);

}
