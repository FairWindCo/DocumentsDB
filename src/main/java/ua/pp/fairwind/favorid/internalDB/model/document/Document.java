package ua.pp.fairwind.favorid.internalDB.model.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "DOCUMENTS")
public class Document {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "documentType_ID")
    DocumentType documentType;
    String number;
    String name;
    String description;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "counterparty_ID")
    Counterparty counterparty;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "person_ID")
    Person person;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "agreement_ID")
    Agreement agreement;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "parent_id")
    Document parent;

    @OneToMany
    @JsonIgnore
    final Set<Document> atachments=new HashSet<>();

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    final Set<DocumentFile> documentFiles=new HashSet<>();

    DOCUMENT_SECURITY_MODEL security_model;
    @OneToMany(mappedBy = "document")
    @JsonIgnore
    final Set<DocumentSecurity> securities=new HashSet<>();

    @OneToMany(mappedBy = "document")
    @JsonIgnore
    final Set<DocumentSubscribe> subscribes=new HashSet<>();

    Date creationDate=new Date();
    @LastModifiedDate
    Date modificationDate;
    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "created_user_id")
    User creationUser;
    @ManyToOne
    @LastModifiedBy
    @JoinColumn(name = "modify_user_id")
    User modificationUser;

    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person= person;
    }


    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public Document getParent() {
        return parent;
    }

    public void setParent(Document parent) {
        this.parent = parent;
    }

    public Set<Document> getAtachments() {
        return Collections.unmodifiableSet(atachments);
    }

    public Set<DocumentFile> getDocumentFiles() {
        return Collections.unmodifiableSet(documentFiles);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    public User getModificationUser() {
        return modificationUser;
    }

    public void setModificationUser(User modificationUser) {
        this.modificationUser = modificationUser;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void addSubscriber(DocumentSubscribe subscriber){
        if(subscriber!=null){
            if(subscriber.document!=null){
                subscriber.document.subscribes.remove(subscriber);
            }
            subscriber.document=this;
            subscribes.add(subscriber);
        }
    }

    public void removeSubscriber(DocumentSubscribe subscriber){
        if(subscriber!=null){
            subscriber.document=null;
            subscribes.remove(subscriber);
        }
    }

    public void addSecurity(DocumentSecurity security){
        if(security!=null){
            if(security.document!=null){
                security.document.securities.remove(security);
            }
            security.document=this;
            securities.add(security);
        }
    }

    public void removeSecurity(DocumentSecurity security){
        if(security!=null){
            security.document=null;
            securities.remove(security);
        }
    }

    public void addAtachment(Document atacment){
        if(atacment!=null){
            atachments.add(atacment);
        }
    }

    public void removeAtachment(Document atacment){
        if(atacment!=null){
            atachments.remove(atacment);
        }
    }

    public boolean checkPermission(SECURITY_ACTION action){
        if(security_model==DOCUMENT_SECURITY_MODEL.ACCESS_FOR_ALL)return true;
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            return false;
        }

        if(security_model==DOCUMENT_SECURITY_MODEL.ACCESS_FOR_OWNER){
            if(creationUser!=null && creationUser.getPerson()!=null){
                Person creator=creationUser.getPerson();
                if(userDetail.getUserPerson().equals(creator) || userDetail.getUserPerson().getId().equals(creator.getId())){
                    return true;
                }
                return false;
            } else {
                return true;
            }
        } else {
            boolean res=false;
            if (securities.isEmpty()) return true;
            for (DocumentSecurity in : securities) {
                if (in.getPerson() == null) {
                    if (in.getPerson()!=null && (userDetail.getUserPerson().equals(in.getPerson()) || userDetail.getUserPerson().getId().equals(in.getPerson().getId()))) {
                        if (in.action == action || in.action==SECURITY_ACTION.ALL_ACTION) {
                            if (in.permission == SECURITY_PERMISSION.RESTRICT) return false;
                            res = true;
                        }
                    }
                }
            }
            return res;
        }
    }

    public boolean isCanEdit(){
        return checkPermission(SECURITY_ACTION.EDIT_ACTION);
    }

    public boolean isCanView(){
        return checkPermission(SECURITY_ACTION.VIEW_ACTION);
    }

    public boolean isCanDelete(){
        return checkPermission(SECURITY_ACTION.DELETE_ACTION);
    }

    public boolean isCanSubscribe(){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            return false;
        }
        if (subscribes.isEmpty()) return false;
        for (DocumentSubscribe in : subscribes) {
            if (in.getPerson() == null) {
                if (in.getPerson()!=null && (userDetail.getUserPerson().equals(in.getPerson()) || userDetail.getUserPerson().getId().equals(in.getPerson().getId()))) {
                    if(in.getSubscribed()!=null)return false;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSubscribed(){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail==null && userDetail.getUserPerson()==null){
            return false;
        }
        if (subscribes.isEmpty()) return false;
        for (DocumentSubscribe in : subscribes) {
            if (in.getPerson() == null) {
                if (in.getPerson()!=null && (userDetail.getUserPerson().equals(in.getPerson()) || userDetail.getUserPerson().getId().equals(in.getPerson().getId()))) {
                    if(in.getSubscribed()!=null)return true;
                }
            }
        }
        return false;
    }

    public DOCUMENT_SECURITY_MODEL getSecurity_model() {
        return security_model;
    }

    public void setSecurity_model(DOCUMENT_SECURITY_MODEL security_model) {
        this.security_model = security_model;
    }
}
