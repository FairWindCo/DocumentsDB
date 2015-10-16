package ua.pp.fairwind.favorid.internalDB.model.document;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ua.pp.fairwind.favorid.internalDB.model.Person;

import javax.persistence.*;

/**
 * Created by Сергей on 16.10.2015.
 */
@Entity
@Table(name = "DOCUMENTS_SECURITY")
public class DocumentSecurity {
    @Id
    @GeneratedValue
    Long id;



    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "person_ID")
    Person person;

    SECURITY_PERMISSION permission;
    SECURITY_ACTION action;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "document_id")
    Document document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public SECURITY_PERMISSION getPermission() {
        return permission;
    }

    public void setPermission(SECURITY_PERMISSION permission) {
        this.permission = permission;
    }

    public SECURITY_ACTION getAction() {
        return action;
    }

    public void setAction(SECURITY_ACTION action) {
        this.action = action;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
