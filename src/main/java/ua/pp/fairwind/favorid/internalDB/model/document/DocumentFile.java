package ua.pp.fairwind.favorid.internalDB.model.document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
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
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "document_id")
    Document document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        if(this.document!=null){
            this.document.documentFiles.remove(this);
        }
        if(document!=null) {
            this.document = document;
            this.document.documentFiles.add(this);
        }
    }
}
