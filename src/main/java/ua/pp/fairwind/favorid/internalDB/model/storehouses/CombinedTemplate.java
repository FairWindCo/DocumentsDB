package ua.pp.fairwind.favorid.internalDB.model.storehouses;

import javax.persistence.*;

/**
 * Created by Сергей on 19.10.2015.
 */
@Entity
@Table(name = "NOMENCLATURE_TAMPLATES")
public class CombinedTemplate {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "parent_nomenclature_id")
    Nomenclature parent;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    Nomenclature nomenclature;
    long count=1;
    @Version
    long version=0;


}
