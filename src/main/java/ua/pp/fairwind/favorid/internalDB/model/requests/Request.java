package ua.pp.fairwind.favorid.internalDB.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.pp.fairwind.favorid.internalDB.model.Agreement;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.security.UserDetailsAdapter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 29.10.2015.
 */
@Entity
@Table(name = "REQUESTS")
public class Request {
    @Id
    @GeneratedValue
    Long id;
    REQUEST_TYPES typeRequest;
    @ManyToOne
    @JoinColumn(name = "responsible_person_ID")
    Person responsiblePerson;
    Date operationDate;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "counterparty_id")
    Counterparty counterparty;
    @ManyToOne
    @JoinColumn(name = "agreement_ID")
    Agreement agreement;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "request")
    @JsonIgnore
    Set<RequestItems> items=new HashSet<>();
    boolean executed=false;
    Date executedDate;
    @ManyToOne
    @JoinColumn(name = "executed_person_ID")
    Person executedPerson;
    @ManyToOne
    @JoinColumn(name = "approved_person_ID")
    Person approvedPerson;
    Date approvedDate;
    @Version
    long version;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_request_ID")
    Request parentRequest;
    @OneToMany(mappedBy = "parentRequest")
    @JsonIgnore
    Set<Request> requests=new HashSet<>();
    String comments;

    private boolean testSubscribe(UserDetailsAdapter userDetail){
        switch (typeRequest){
            case PRODUCTION:
                return userDetail.hasRole("ROLE_SUBSCRIBE_REQUEST_PRODUCTION");
            case SHIPMENT:
                return userDetail.hasRole("ROLE_SUBSCRIBE_REQUEST_SHIPMENT");
            case PURCHASE:
                return userDetail.hasRole("ROLE_SUBSCRIBE_REQUEST_PURCHASE");
            case REPAIR:
                return userDetail.hasRole("ROLE_SUBSCRIBE_REQUEST_REPAIR");
            default:
                return false;
        }
    }

    private boolean testCommite(UserDetailsAdapter userDetail){
        switch (typeRequest){
            case PRODUCTION:
                return userDetail.hasRole("ROLE_COMMIT_REQUEST_PRODACTION");
            case SHIPMENT:
                return userDetail.hasRole("ROLE_COMMIT_REQUEST_STOREHOUSE");
            case PURCHASE:
                return userDetail.hasRole("ROLE_COMMIT_REQUEST_STOREHOUSE");
            case REPAIR:
                return userDetail.hasRole("ROLE_COMMIT_REQUEST_REPAIR");
            default:
                return false;
        }
    }

    @JsonSerialize
    public boolean isCanSubscribe(){
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(responsiblePerson!=null && userDetail!=null && userDetail.getUserPerson()!=null){
            if(responsiblePerson.equals(userDetail))return false;
            if((approvedDate != null || approvedPerson != null)) return false;
            return testSubscribe(userDetail);
        } else {
            return false;
        }
    }

    @JsonSerialize
    public boolean isSubscribed(){
        return approvedDate!=null || approvedPerson!=null;
    }

    @JsonSerialize
    public boolean isCanCommite(){
        if((executed || executedPerson != null || executedDate!= null)) return false;
        UserDetailsAdapter userDetail=(UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetail!=null && userDetail.getUserPerson()!=null){
            return testCommite(userDetail);
        } else {
            return false;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public REQUEST_TYPES getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(REQUEST_TYPES typeRequest) {
        this.typeRequest = typeRequest;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public void addItem(RequestItems item){
        if(item!=null){
            item.request=this;
            items.add(item);
        }
    }

    public void removeItem(RequestItems item){
        if(item!=null){
            item.request=null;
            items.remove(item);
        }
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public Set<RequestItems> getItems() {
        return Collections.unmodifiableSet(items);
    }


    public Person getApprovedPerson() {
        return approvedPerson;
    }

    public void setApprovedPerson(Person approvedPerson) {
        this.approvedPerson = approvedPerson;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Request getParentRequest() {
        return parentRequest;
    }

    public void setParentRequest(Request parentRequest) {
        if(this.parentRequest!=null){
            this.parentRequest.requests.remove(this);
        }
        this.parentRequest = parentRequest;
    }

    public void addItem(Request request){
        if(request!=null){
            if(request.parentRequest!=null){
                request.parentRequest.removeItem(request);
            }
            request.parentRequest=this;
            requests.add(request);
        }
    }

    public void removeItem(Request request){
        if(request!=null){
            request.parentRequest=null;
            requests.remove(request);
        }
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public Person getExecutedPerson() {
        return executedPerson;
    }

    public void setExecutedPerson(Person executedPerson) {
        this.executedPerson = executedPerson;
    }
}
