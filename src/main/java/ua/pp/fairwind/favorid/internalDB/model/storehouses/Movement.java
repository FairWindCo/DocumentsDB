package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.requests.Request;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 13.10.2015.
 */
@Entity
@Table(name = "STOREHOUSE_MOVEMENT")
public class Movement {
    @Id
    @GeneratedValue
    Long id;
    MOVEMENT_TYPES typeMovement;
    @ManyToOne
    @JoinColumn(name = "responsible_person_ID")
    Person responsiblePerson;
    Date operationDate;
    @ManyToOne
    @JoinColumn(name = "from_storehouse_ID")
    Storehouse fromStorehouse;
    @ManyToOne
    @JoinColumn(name = "counterparty_id")
    Counterparty counterparty;
    @ManyToOne
    @JoinColumn(name = "to_storehouse_ID")
    Storehouse toStorehouse;
    String requestNumber;
    String comments;

    @ManyToOne
    @JoinColumn(name = "request_ID")
    Request request;

    @OneToMany(mappedBy = "movement")
    @JsonIgnore
    Set<MovementElements> nomenclatute=new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "movement")
    Set<MovementElements> nomenclatuteCreated=new HashSet<>();
    @OneToMany
    @JsonIgnore
    Set<Document> documents=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "acknowledgement_person_ID")
    Person acknowledgementPerson;
    Date acknowledgementDate;
    @ManyToOne
    @JoinColumn(name = "approved_person_ID")
    Person approvedPerson;
    Date approvedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MOVEMENT_TYPES getTypeMovement() {
        return typeMovement;
    }

    public void setTypeMovement(MOVEMENT_TYPES typeMovement) {
        this.typeMovement = typeMovement;
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

    public Storehouse getFromStorehouse() {
        return fromStorehouse;
    }

    public void setFromStorehouse(Storehouse fromStorehouse) {
        this.fromStorehouse = fromStorehouse;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public Storehouse getToStorehouse() {
        return toStorehouse;
    }

    public void setToStorehouse(Storehouse toStorehouse) {
        this.toStorehouse = toStorehouse;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Person getAcknowledgementPerson() {
        return acknowledgementPerson;
    }

    public void setAcknowledgementPerson(Person acknowledgementPerson) {
        this.acknowledgementPerson = acknowledgementPerson;
    }

    public Date getAcknowledgementDate() {
        return acknowledgementDate;
    }

    public void setAcknowledgementDate(Date acknowledgementDate) {
        this.acknowledgementDate = acknowledgementDate;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public void addNomenclatureElement(MovementElements element){
        if(element!=null){
            if(element.movement!=null){
                element.movement.removeNomenclatureElement(element);
            }
            if(element.created){
                if (this.nomenclatuteCreated.add(element)) {
                    element.movement = this;
                }
            } else {
                if (this.nomenclatute.add(element)) {
                    element.movement = this;
                }
            }
        }
    }

    public void removeNomenclatureElement(MovementElements element){
        if(element!=null){
            element.movement=null;
            if(element.created){
                this.nomenclatuteCreated.remove(element);
            } else {
                this.nomenclatute.remove(element);
            }
        }
    }

    public void addDocument(Document document){
        if(document!=null){
            this.documents.add(document);
        }
    }

    public void removeDocument(Document document){
        if(document!=null){
            this.documents.remove(document);
        }
    }

    public boolean isApproved(){
        return approvedDate!=null||approvedPerson!=null;
    }


    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
