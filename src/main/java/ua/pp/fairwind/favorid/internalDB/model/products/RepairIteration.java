package ua.pp.fairwind.favorid.internalDB.model.products;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Сергей on 15.10.2015.
 */
@Entity
public class RepairIteration {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "repair_ID")
    Repair repair;
    REPAIR_STATUS status;
    String description;
    Date created=new Date();
}
