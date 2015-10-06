package ua.pp.fairwind.favorid.internalDB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;
import ua.pp.fairwind.favorid.internalDB.model.directories.TaskType;

import javax.persistence.*;
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
    String name;
    String description;
    @ManyToOne
    @JoinColumn(name = "task__type_id")
    TaskType taskType;
    @ManyToOne
    Counterparty counterparty;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "responsible_person_id")
    Person responsible;
    @ManyToMany
    @JoinTable(name = "EXECUTORS",joinColumns = @JoinColumn(name="task_id"),inverseJoinColumns = @JoinColumn(name="person_id"))
    @JsonManagedReference
    Set<Person> executors=new HashSet<>();
    Date startDate;
    Date dedLineDate;
    Date endDate;
    Date creationDate=new Date();
    @LastModifiedBy
    Date modificationDate;
    @ManyToMany
    @JsonManagedReference
    @JoinTable(name = "TASK_DOCUMENTS",joinColumns = @JoinColumn(name="task_id"),inverseJoinColumns = @JoinColumn(name="document_id"))
    Set<Document> taskDocuments=new HashSet<>();
    @ManyToOne
    @CreatedBy
    User creationUser;

    @Version
    private long version;

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

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

    public Set<Person> getExecutors() {
        return executors;
    }

    public void setExecutors(Set<Person> executors) {
        this.executors = executors;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDedLineDate() {
        return dedLineDate;
    }

    public void setDedLineDate(Date dedLineDate) {
        this.dedLineDate = dedLineDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Set<Document> getTaskDocuments() {
        return taskDocuments;
    }

    public void setTaskDocuments(Set<Document> taskDocuments) {
        this.taskDocuments = taskDocuments;
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
}
