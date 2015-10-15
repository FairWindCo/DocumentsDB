package ua.pp.fairwind.favorid.internalDB.model.administrative;

import javax.persistence.*;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "USER_ROLES")
public class Role {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String description;
    @Version
    long version;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
