package ua.pp.fairwind.favorid.internalDB.model.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedBy;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 16.10.2015.
 */
@Entity
@Table(name = "MESSAGES")
public class Message {
    @Id
    @GeneratedValue
    Long id;
    String messageText;
    Date creationDate;
    Date actual;
    @OneToMany(mappedBy = "message")
    Set<MessageRecipient> recipientSet=new HashSet<>();
    @ManyToOne
    @CreatedBy
    @JsonIgnore
    @JoinColumn(name = "created_user_id")
    User creationUser;

    @JsonSerialize
    public String userName(){
        return creationUser.getFIO();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getActual() {
        return actual;
    }

    public void setActual(Date actual) {
        this.actual = actual;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    public Set<MessageRecipient> getRecipientSet() {
        return  Collections.unmodifiableSet(recipientSet);
    }
}
