package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 23.10.2015.
 */
@Entity
@Table(name = "NOMENCLATURE_TYPES")
public class NomenclatureTypes {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String description;
    @Version
    long version=0;

    @ManyToMany
    @JsonIgnore
    final Set<Nomenclature> nomenclature=new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void addNomenclature(Nomenclature nomenclature){
        if(nomenclature!=null) {
            if (this.nomenclature.add(nomenclature)) {
                nomenclature.nomenclatureTypes.add(this);
            }
        }
    }

    public void removeNomenclature(Nomenclature nomenclature){
        if(nomenclature!=null) {
            if (this.nomenclature.remove(nomenclature)) {
                nomenclature.nomenclatureTypes.remove(this);
            }
        }
    }
}
