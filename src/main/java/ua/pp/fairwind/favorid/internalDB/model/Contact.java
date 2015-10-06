package ua.pp.fairwind.favorid.internalDB.model;

import ua.pp.fairwind.favorid.internalDB.model.directories.ContactType;

import javax.persistence.*;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "CONTACTS")
public class Contact {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "contact_type_id")
    ContactType contactType;
    String contact;
    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
