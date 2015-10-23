package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 13.10.2015.
 */
@Entity
@Table(name = "NOMENCLATURE")
public class Nomenclature {
    @Id
    @GeneratedValue
    Long id;
    String code;
    String name;
    String manufacturer;
    String country;
    String description;
    boolean combined=false;
    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    Set<CombinedTemplate> templates=new HashSet<>();
    @OneToMany
    @JsonIgnore
    final Set<Safekeeping> safekeeping=new HashSet<>();
    @ManyToMany
    @JsonIgnore
    final Set<NomenclatureTypes> nomenclatureTypes=new HashSet<>();

    @Version
    long version=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void addSafekeeping(Safekeeping safekeeping){
        if(safekeeping!=null) {
            if (this.safekeeping.add(safekeeping)) {
                if(safekeeping.nomenclature!=null){
                    safekeeping.nomenclature.safekeeping.remove(safekeeping);
                }
                safekeeping.nomenclature = this;
            }
        }
    }

    public void removeSafekeeping(Safekeeping safekeeping){
        if(safekeeping!=null) {
            if (this.safekeeping.remove(safekeeping)) {
                safekeeping.nomenclature = null;
            }
        }
    }

    public void addTypes(NomenclatureTypes types){
        if(types!=null) {
            if (this.nomenclatureTypes.add(types)) {
                types.nomenclature.add(this);
            }
        }
    }

    public void removeTypes(NomenclatureTypes types){
        if(types!=null) {
            if (this.safekeeping.remove(types)) {
                types.nomenclature.remove(this);
            }
        }
    }

    public Set<Safekeeping> getSafekeeping() {
        return Collections.unmodifiableSet(safekeeping);
    }

    public Set<NomenclatureTypes> getTypes() {
        return Collections.unmodifiableSet(nomenclatureTypes);
    }

    public boolean isCombined() {
        return combined;
    }

    public void setCombined(boolean combined) {
        this.combined = combined;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
