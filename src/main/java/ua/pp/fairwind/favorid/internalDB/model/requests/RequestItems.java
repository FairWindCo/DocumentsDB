package ua.pp.fairwind.favorid.internalDB.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Units;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Сергей on 29.10.2015.
 */
@Entity
@Table(name = "REQUEST_ITEMS")
public class RequestItems {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "request_ID")
    @JsonIgnore
    Request request;
    @ManyToOne
    @JoinColumn(name = "nomenclature_ID")
    @JsonIgnore
    Nomenclature nomenclature;
    long count;
    Units units;
    Date lastUpdate;
    String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        if(request!=null){
            this.request = request;
            request.items.add(this);
        } else {
            if(this.request!=null){
                this.request.items.remove(this);
            }
        }
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature = nomenclature;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public RequestItems cloneForRequest(){
        RequestItems newitem=new RequestItems();
        newitem.comments=this.comments;
        newitem.count=this.count;
        newitem.nomenclature=this.nomenclature;
        newitem.units=this.units;
        newitem.request=null;
        return newitem;
    }
}
