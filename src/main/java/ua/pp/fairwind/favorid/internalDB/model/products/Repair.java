package ua.pp.fairwind.favorid.internalDB.model.products;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 15.10.2015.repairs
 */
@Entity
@Table(name = "REPAIRS")
public class Repair {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "product_ID")
    Product product;
    String description;
    String repairResult;
    REPAIR_STATUS status;
    Date start=new Date();
    Date end;
    Date lastStatusChange;
    @OneToMany(mappedBy = "repair")
    Set<RepairIteration> repairIteration=new HashSet<>();
}
