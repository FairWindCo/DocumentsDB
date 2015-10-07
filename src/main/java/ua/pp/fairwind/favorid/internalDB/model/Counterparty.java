package ua.pp.fairwind.favorid.internalDB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
public class Counterparty {
    @Id
    @GeneratedValue
    Long id;
    String short_name;
    String full_name;
    @OneToMany
    @JsonManagedReference
    final Set<Contact> contacts=new HashSet<>();
    @OneToMany(mappedBy = "counterparty")
    @JsonManagedReference
    final Set<Person> persons=new HashSet<>();
    @OneToMany(mappedBy = "counterparty")
    @JsonManagedReference
    final Set<Agreement> agriments =new HashSet<>();
    @Version
    private long version;

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(Contact contact){
        contacts.remove(contact);
    }

    public void addPerson(Person person){
        if(person!=null){
            person.setCounterparty(this);
        }
    }

    public void removePerson(Person person){
        if(person!=null){
            person.setCounterparty(null);
        }
    }

    public void addAgreement(Agreement agreement){
        if(agreement!=null) {
            if(agriments.add(agreement)) {
                agreement.counterparty = this;
            }
        }
    }

    public void removeAgreement(Agreement agreement){
        if(agreement!=null){
            if(agriments.remove(agreement)){
                agreement.counterparty=null;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Set<Contact> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    public Set<Person> getPersons() {
        return Collections.unmodifiableSet(persons);
    }

    public Set<Agreement> getAgriments() {
        return Collections.unmodifiableSet(agriments);
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }




}
