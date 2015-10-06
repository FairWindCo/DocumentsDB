package ua.pp.fairwind.favorid.internalDB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
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
    Set<Contact> contacts=new HashSet<>();
    @OneToMany(mappedBy = "counterparty")
    @JsonManagedReference
    Set<Person> persons=new HashSet<>();
    @OneToMany(mappedBy = "counterparty")
    @JsonManagedReference
    Set<Agreement> agriments =new HashSet<>();
    @Version
    private long version;

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(Contact contact){
        contacts.remove(contact);
    }

    public void addPerson(Person person){
        persons.add(person);
    }

    public void removePerson(Person person){
        persons.remove(person);
    }

    public void addPerson(Agreement agreement){
        agriments.add(agreement);
    }

    public void removePerson(Agreement agreement){
        agriments.remove(agreement);
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
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Set<Agreement> getAgriments() {
        return agriments;
    }

    public void setAgriments(Set<Agreement> counterparties) {
        this.agriments = counterparties;
    }
}
