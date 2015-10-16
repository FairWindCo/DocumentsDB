package ua.pp.fairwind.favorid.internalDB.model.messages;

import ua.pp.fairwind.favorid.internalDB.model.Person;

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
    @JoinColumn(name = "message_ID")
    Message message;
    @ManyToOne
    @JoinColumn(name = "person_ID")
    Person recipient;

    Date validationDate;


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

    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }
}
