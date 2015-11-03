package ua.pp.fairwind.favorid.internalDB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.directories.TaskType;
import ua.pp.fairwind.favorid.internalDB.model.document.Document;
import ua.pp.fairwind.favorid.internalDB.model.proxy.MyDateSerializer;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Сергей on 06.10.2015.
 */
@Entity
@Table(name = "TASKS")
public class Task {
    @Id
    @GeneratedValue
    Long id;
    String description;
    String result;
    @ManyToOne
    @JoinColumn(name = "task_type_id")
    TaskType taskType;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "responsible_person_id")
    Person responsible;
    @ManyToMany
    @JoinTable(name = "EXECUTORS",joinColumns = @JoinColumn(name="task_id"),inverseJoinColumns = @JoinColumn(name="person_id"))
    @JsonManagedReference
    final Set<Person> executors=new HashSet<>();
    Date startDate;
    Date dedLineDate;
    Date endDate;
    Date creationDate=new Date();
    @LastModifiedDate
    Date modificationDate;
    @ManyToMany
    @JsonManagedReference
    @JoinTable(name = "TASK_DOCUMENTS",joinColumns = @JoinColumn(name="task_id"),inverseJoinColumns = @JoinColumn(name="document_id"))
    final Set<Document> taskDocuments=new HashSet<>();
    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "create_user_id")
    User creationUser;
    Date endControlDate;
    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }


    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

    public Set<Person> getExecutors() {
        return Collections.unmodifiableSet(executors);
    }

    @JsonSerialize(using=MyDateSerializer.class)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonSerialize(using=MyDateSerializer.class)
    public Date getDedLineDate() {
        return dedLineDate;
    }

    public void setDedLineDate(Date dedLineDate) {
        this.dedLineDate = dedLineDate;
    }
    @JsonSerialize(using=MyDateSerializer.class)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    @JsonSerialize(using=MyDateSerializer.class)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonSerialize(using=MyDateSerializer.class)
    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Set<Document> getTaskDocuments() {
        return Collections.unmodifiableSet(taskDocuments);
    }


    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void addExecutor(Person executor){
        executors.add(executor);
    }

    public void removeExecutor(Person executor){
        executors.remove(executor);
    }

    public void addDocument(Document document){
        taskDocuments.add(document);
    }

    public void removeDocument(Document document){
        taskDocuments.remove(document);
    }

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User creationUser) {
        this.creationUser = creationUser;
    }

    @JsonSerialize(using=MyDateSerializer.class)
    public Date getEndControlDate() {
        return endControlDate;
    }

    public void setEndControlDate(Date endControlDate) {
        this.endControlDate = endControlDate;
    }
}
