package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Сергей on 26.10.2015.
 */
@Entity
@Table(name = "STOREHOUSE_MOVEMENT_ELEMENTS")
public class MovementElements {
    @Id
    @GeneratedValue
    Long id;
    boolean created=false;
    @ManyToOne
    @JoinColumn(name = "movement_ID")
    @JsonIgnore
    Movement movement;
    @ManyToOne
    @JoinColumn(name = "nomenclature_ID")
    Nomenclature nomenclature;
    long count;
    Units  units;
    String serialNumber;
    String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        if(movement!=null)movement.addNomenclatureElement(this);
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

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
