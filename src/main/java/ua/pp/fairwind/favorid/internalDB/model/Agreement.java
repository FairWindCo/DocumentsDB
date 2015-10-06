package ua.pp.fairwind.favorid.internalDB.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ������ on 06.10.2015.
 */
@Entity
@Table(name = "AGREEMENTS")
public class Agreement {
    @Id
    @GeneratedValue
    Long id;
    String number;
    String name;
    Date startDate;
    Date planEndDate;
    Date endDate;
    @ManyToOne
    @JoinColumn(name = "counterparty_id")
    Counterparty counterparty;

    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
