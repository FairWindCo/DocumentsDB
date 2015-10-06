package ua.pp.fairwind.favorid.internalDB.model.administrative;

import javax.persistence.*;

/**
 * Created by ������ on 06.10.2015.
 */
@Entity
@Table(name = "USER_ROELS")
public class Role {
    @Id
    @GeneratedValue
    Long id;
    String name;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
