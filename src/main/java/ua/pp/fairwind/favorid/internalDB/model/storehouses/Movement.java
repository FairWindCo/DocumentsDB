package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import ua.pp.fairwind.favorid.internalDB.model.Counterparty;
import ua.pp.fairwind.favorid.internalDB.model.Person;

import javax.persistence.*;
import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name = "nomenclature_ID")
    Nomenclature nomenclature;
    long count;
    String  units;

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

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
