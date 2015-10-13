package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Сергей on 13.10.2015.
 */
@Entity
@Table(name = "SAFEKEEPING")
public class Safekeeping {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "storehouse_ID")
    Storehouse storehouse;
    @ManyToOne
    @JoinColumn(name = "nomenclature_ID")
    Nomenclature nomenclature;
    long count;
    String  units;
    Date lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Storehouse getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(Storehouse storehouse) {
        if(storehouse!=null) {
            storehouse.addSafekeeping(this);
        } else {
            storehouse.removeSafekeeping(this);
        }
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        if(nomenclature!=null) {
            nomenclature.addSafekeeping(this);
        } else {
            nomenclature.removeSafekeeping(this);
        }
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
