package ua.pp.fairwind.favorid.internalDB.model.administrative;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.pp.fairwind.favorid.internalDB.model.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "USERS")
public class User {
        @Id
        @Column(name = "USER_ID")
        @GeneratedValue
        private Long userID;
        @Column(name = "USERNAME",nullable = false)
        private String userName;
        @Column(name = "PASSWORD")
        private String passwordHash;
        @Column(name = "ENABLED")
        private boolean enabled;
        @JsonIgnore
        @ManyToMany(fetch = FetchType.EAGER)
        private Set<Role> userRoles=new HashSet<>();

        @ManyToOne
        @JsonIgnore
        @JoinColumn(name = "person_id")
        private Person person;
        @Version
        private long versionId;

    @JsonSerialize
    public String getFIO(){
        if(person==null) return null;
        return person.getSurname()==null?"":person.getSurname()+" "+person.getFirstName()==null?"":person.getFirstName()+" "+person.getMiddleName()==null?"":person.getMiddleName();
    }

    @JsonSerialize
    public Long getPersonID(){
        return person==null?null:person.getId();
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (versionId != user.versionId) return false;
        return !(userID != null ? !userID.equals(user.userID) : user.userID != null);

    }

    @Override
    public int hashCode() {
        int result = userID != null ? userID.hashCode() : 0;
        result = 31 * result + (int) (versionId ^ (versionId >>> 32));
        return result;
    }
}
