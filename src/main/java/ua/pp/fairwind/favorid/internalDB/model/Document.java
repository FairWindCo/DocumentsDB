package ua.pp.fairwind.favorid.internalDB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.directories.DocumentType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 06.10.2015.
 */
public class Document {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    DocumentType documentType;
    String number;
    String name;
    String description;

    @ManyToOne
    @JsonManagedReference
    Counterparty counterparty_from;
    @ManyToOne
    @JsonManagedReference
    Counterparty counterparty_to;
    @ManyToOne
    @JsonManagedReference
    Person person_from;
    @ManyToOne
    @JsonManagedReference
    Person person_to;
    @JsonManagedReference
    @ManyToOne
    Agreement agreement;

    @ManyToOne
    @JsonManagedReference
    Document parent;
    @ManyToOne
    @JsonManagedReference
    Set<Document> atachments=new HashSet<>();
    @ManyToOne
    @JsonManagedReference
    Set<DocumentFile> documentFiles=new HashSet<>();

    Date creationDate=new Date();
    @LastModifiedDate
    Date modificationDate;
    @ManyToOne
    @CreatedBy
    User creationUser;
    @ManyToOne
    @LastModifiedBy
    User modificationUser;

    @Version
    private long version;
}
