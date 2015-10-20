package ua.pp.fairwind.favorid.internalDB.model.document;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ua.pp.fairwind.favorid.internalDB.model.Person;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Сергей on 20.10.2015.
 */
@Entity
@Table(name = "DOCUMENTS_SUBSCRIBE")
public class DocumentSubscribe {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "person_ID")
    Person person;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "subscribed_document_ID")
    Document document;
    Date subscribed;
    @Version
    long version;

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

    public Date getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Date subscribed) {
        this.subscribed = subscribed;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        if(this.document!=null){
            this.document.subscribes.remove(this);
        }
        if(document!=null) {
            this.document = document;
            this.document.subscribes.add(this);
        }
    }
}
