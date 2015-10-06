package ua.pp.fairwind.favorid.internalDB.model.administrative;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

        @JsonManagedReference
        @ManyToOne
        @JoinColumn(name = "person_id")
        private Person person;
        @Version
        private long versionId;

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
}
