package ua.pp.fairwind.favorid.internalDB.model.directories;

import javax.persistence.*;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "DOCUMENT_TYPES")
public class DocumentType {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String numberFormat;
    @Version
    long version;
    long counter;

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

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
