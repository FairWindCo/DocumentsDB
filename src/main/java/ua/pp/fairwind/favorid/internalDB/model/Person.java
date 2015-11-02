package ua.pp.fairwind.favorid.internalDB.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import ua.pp.fairwind.favorid.internalDB.model.directories.Position;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "PERSONS")
public class Person {
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "surname")
    String surname;
    @Column(name = "middle_name")
    String middleName;
    @Column(name = "first_name")
    String firstName;
    @DateTimeFormat(pattern = "dd-mm-YYYY")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    Date date_of_birth;
    String comments;
    @ManyToOne
    @JoinColumn(name = "position_id")
    Position position;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "counterparty_id")
    Counterparty counterparty;
    @OneToMany
    @JsonManagedReference
    final Set<Contact> contacts=new HashSet<>();

    @OneToMany(mappedBy = "head")
    @JsonBackReference
    @JsonIgnore
    final Set<Person> subordinates=new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "head_of_id")
    @JsonManagedReference
    Person head;
    @Version
    private long version;

    @JsonSerialize
    public long getLevel(){
        return getLevelNode(head);
    }

    @JsonSerialize
    public Long getParentId(){
        return head==null?null:head.id;
    }

    @JsonSerialize
    public boolean isLeaf(){
        return (subordinates.size()==0);
    }

    private long getLevelNode(Person node){
        if(node==null)return 0;
        return getLevelNode(node.getHead())+1;
    }

    public String getFio(){
        return (surname==null?"":surname)+" "+(firstName==null?"":firstName)+" "+(middleName==null?"":middleName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middle_name) {
        this.middleName = middle_name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        if(counterparty!=null) {
            this.counterparty = counterparty;
            counterparty.persons.add(this);
        } else {
            this.counterparty.persons.remove(this);
            this.counterparty = counterparty;
        }
    }

    public Set<Contact> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }


    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(Contact contact){
        contacts.remove(contact);
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setHead(Person person){
        if(person!=null){
            person.subordinates.add(this);
            this.head=person;
        } else {
            if(this.head!=null){
                if(this.head.subordinates.remove(this)){
                    this.head=null;
                }
            }
        }
    }

    public void addSubordinate(Person person){
        if(person!=null){
            person.setHead(this);
        }
    }

    public void removeSubordinate(Person person){
        if(person!=null){
            person.setHead(null);
        }
    }

    public Person getHead() {
        return head;
    }

    public Set<Person> getSubordinates() {
        return Collections.unmodifiableSet(subordinates);
    }
}
