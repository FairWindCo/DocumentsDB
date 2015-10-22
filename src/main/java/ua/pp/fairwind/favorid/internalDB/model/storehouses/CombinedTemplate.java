package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import javax.persistence.*;

/**
 * Created by Сергей on 19.10.2015.
 */
@Entity
@Table(name = "NOMENCLATURE_TAMPLATES")
public class CombinedTemplate {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "parent_nomenclature_id")
    Nomenclature parent;
    @ManyToOne
    @JoinColumn(name = "nomenclature_id")
    Nomenclature nomenclature;
    long count=1;
    @Version
    long version=0;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nomenclature getParent() {
        return parent;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature=nomenclature;
    }

    public void setParent(Nomenclature nomenclature) {
        if(this.parent!=null){
            this.parent.templates.remove(this);
            if(this.parent.templates.size()==0){
                this.parent.combined=false;
            }
        }
        if(nomenclature!=null) {
            this.parent = nomenclature;
            this.parent.templates.add(this);
            this.parent.combined=true;
        }
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
