package ua.pp.fairwind.favorid.internalDB.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "FILES")
public class DocumentFile {
    @Id
    @GeneratedValue
    Long id;
    String fileName;
    String filePath;
    String mimeType;
    long size;
    Date creationDate=new Date();
}
