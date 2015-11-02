package ua.pp.fairwind.favorid.internalDB.model.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.proxy.MyDateSerializer;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

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
    @JsonIgnore
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

    @JsonSerialize
    public boolean isValidated(){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            return false;
        }
        for(MessageRecipient in:recipientSet){
            if(in.getValidationDate()!=null) {
                if (userDetail.getUserPerson().equals(in.getRecipient())) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonSerialize
    public boolean isCanValidate(){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            return false;
        }
        for(MessageRecipient in:recipientSet){
            if(in.getValidationDate()==null) {
                if (userDetail.getUserPerson().equals(in.getRecipient())) {
                    return true;
                }
            }
        }
        return false;
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

    @JsonSerialize(using=MyDateSerializer.class)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    @JsonSerialize(using=MyDateSerializer.class)
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

    public void addMessageRecicpient(MessageRecipient mesrec){
        if(mesrec!=null){
            if(mesrec.message!=null){
                mesrec.message.recipientSet.remove(mesrec);
            }
            mesrec.message=this;
            recipientSet.add(mesrec);
        }
    }

    public void removeMessageRecicpient(MessageRecipient mesrec){
        if(mesrec!=null){
            mesrec.message=null;
            recipientSet.remove(mesrec);
        }
    }
}
