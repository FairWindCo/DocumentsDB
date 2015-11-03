package ua.pp.fairwind.favorid.internalDB.model.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.proxy.MyDateSerializer;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Сергей on 16.10.2015.
 */
@Entity
@Table(name = "MESSAGES_RECIPIENT")
public class MessageRecipient {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "message_ID")
    Message message;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "person_ID")
    Person recipient;

    Date validationDate;

    public String getFio(){
        if(recipient==null) return "";
        return (recipient.getSurname()==null?"":recipient.getSurname())+" "+(recipient.getFirstName()==null?"":recipient.getFirstName())+" "+(recipient.getMiddleName()==null?"":recipient.getMiddleName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }
    @JsonSerialize(using=MyDateSerializer.class)
    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }
}
