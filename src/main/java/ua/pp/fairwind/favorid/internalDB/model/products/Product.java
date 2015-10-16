package ua.pp.fairwind.favorid.internalDB.model.products;

import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.storehouses.Nomenclature;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 15.10.2015.
 */
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "nomenclature_ID")
    Nomenclature nomenclature;
    String serial_number;
    boolean self_created=false;
    String description;
    @OneToMany
    Set<Document> productDocuments=new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "composed_ID")
    Product composedOf;
    @OneToMany(mappedBy = "composedOf")
    Set<Product> composition=new HashSet<>();
    Date createdDate;
    Date shippingDate;
    Date guaranteePeriod;

}
