package ua.pp.fairwind.favorid.internalDB.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.directories.DocumentType;

import javax.persistence.*;
import java.util.Collections;
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
    @JsonIgnore
    final Set<Document> atachments=new HashSet<>();

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    final Set<DocumentFile> documentFiles=new HashSet<>();

    DOCUMENT_SECURITY_MODEL security_model;
    @OneToMany(mappedBy = "document")
    @JsonIgnore
    final Set<DocumentSecurity> securities=new HashSet<>();

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    final Set<DocumentSubscribe> subscribes=new HashSet<>();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Counterparty getCounterparty_from() {
        return counterparty_from;
    }

    public void setCounterparty_from(Counterparty counterparty_from) {
        this.counterparty_from = counterparty_from;
    }

    public Counterparty getCounterparty_to() {
        return counterparty_to;
    }

    public void setCounterparty_to(Counterparty counterparty_to) {
        this.counterparty_to = counterparty_to;
    }

    public Person getPerson_from() {
        return person_from;
    }

    public void setPerson_from(Person person_from) {
        this.person_from = person_from;
    }

    public Person getPerson_to() {
        return person_to;
    }

    public void setPerson_to(Person person_to) {
        this.person_to = person_to;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public Document getParent() {
        return parent;
    }

    public void setParent(Document parent) {
        this.parent = parent;
    }

    public Set<Document> getAtachments() {
        return Collections.unmodifiableSet(atachments);
    }

    public Set<DocumentFile> getDocumentFiles() {
        return Collections.unmodifiableSet(documentFiles);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    public User getModificationUser() {
        return modificationUser;
    }

    public void setModificationUser(User modificationUser) {
        this.modificationUser = modificationUser;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
