package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ������ on 13.10.2015.
 */
@Entity
@Table(name = "SAFEKEEPING")
public class Safekeeping {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "storehouse_ID")
    @JsonIgnore
    Storehouse storehouse;
    @ManyToOne
    @JoinColumn(name = "nomenclature_ID")
    @JsonIgnore
    Nomenclature nomenclature;
    long count;
    Units  units;
    Date lastUpdate;
    boolean defective=false;
    String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @JsonSerialize
    public String getNomenclatureName(){
        return nomenclature==null?"":nomenclature.getName();
    }

    @JsonSerialize
    public String getNomenclatureCode(){
        return nomenclature==null?"":nomenclature.getCode();
    }

    @JsonSerialize
    public String getNomenclatureManufacturer(){
        return nomenclature==null?"":nomenclature.getManufacturer();
    }


    public Storehouse getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(Storehouse storehouse) {
        if(storehouse!=null) {
            storehouse.addSafekeeping(this);
        } else {
            this.storehouse.removeSafekeeping(this);
        }
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        if(nomenclature!=null) {
            nomenclature.addSafekeeping(this);
        } else {
            this.nomenclature.removeSafekeeping(this);
        }
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isDefective() {
        return defective;
    }

    public void setDefective(boolean defective) {
        this.defective = defective;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
