package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.pp.fairwind.favorid.internalDB.model.Person;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 13.10.2015.
 */
@Entity
@Table(name = "STOREHOUSES")
public class Storehouse {
    @Id
    @GeneratedValue
    Long id;
    String number;
    String name;
    String location;
    @ManyToOne
    @JoinColumn(name = "responsible_person_ID")
    Person responsiblePerson;
    String comments;
    @OneToMany
    @JsonIgnore
    Set<Safekeeping> safekeeping=new HashSet<>();
    @Version
    long version=0;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void addSafekeeping(Safekeeping safekeeping){
        if(safekeeping!=null) {
            if (this.safekeeping.add(safekeeping)) {
                if(safekeeping.storehouse!=null){
                    safekeeping.storehouse.safekeeping.remove(safekeeping);
                }
                safekeeping.storehouse = this;
            }
        }
    }

    public void removeSafekeeping(Safekeeping safekeeping){
        if(safekeeping!=null) {
            if (this.safekeeping.remove(safekeeping)) {
                safekeeping.storehouse = null;
            }
        }
    }

    public Set<Safekeeping> getSafekeeping() {
        return Collections.unmodifiableSet(safekeeping);
    }
}
