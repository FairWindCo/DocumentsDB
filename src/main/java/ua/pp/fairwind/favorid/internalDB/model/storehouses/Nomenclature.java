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
    @OneToMany
    @JsonIgnore
    final Set<Safekeeping> safekeeping=new HashSet<>();

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

    public Set<Safekeeping> getSafekeeping() {
        return Collections.unmodifiableSet(safekeeping);
    }
}
