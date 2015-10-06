package ua.pp.fairwind.favorid.internalDB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.directories.DocumentType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "DOCUMENTS")
public class Document {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "documentType_ID")
    DocumentType documentType;
    String number;
    String name;
    String description;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "counterparty_from_ID")
    Counterparty counterparty_from;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "counterparty_to_ID")
    Counterparty counterparty_to;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "person_from_ID")
    Person person_from;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "person_to_ID")
    Person person_to;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "agreement_ID")
    Agreement agreement;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "parent_id")
    Document parent;

    @OneToMany
    @JsonManagedReference
    Set<Document> atachments=new HashSet<>();

    @OneToMany
    @JsonManagedReference
    Set<DocumentFile> documentFiles=new HashSet<>();

    Date creationDate=new Date();
    @LastModifiedDate
    Date modificationDate;
    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "created_user_id")
    User creationUser;
    @ManyToOne
    @LastModifiedBy
    @JoinColumn(name = "modify_user_id")
    User modificationUser;

    @Version
    private long version;
}
