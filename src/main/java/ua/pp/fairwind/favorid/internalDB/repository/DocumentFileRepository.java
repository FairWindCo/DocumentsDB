package ua.pp.fairwind.favorid.internalDB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.document.DocumentFile;

import java.util.List;

/**
 * Created by Сергей on 20.10.2015.
 */
public interface DocumentFileRepository extends JpaRepository<DocumentFile,Long> {
    List<DocumentFile> findByDocument(Document document);
    Page<DocumentFile> findByDocument(Document document, Pageable pageRequest);
    List<DocumentFile> findByDocumentAndFileName(Document document, String name);
    Page<DocumentFile> findByDocumentAndFileName(Document document, String name, Pageable pageRequest);

}
