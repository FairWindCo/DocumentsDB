package ua.pp.fairwind.favorid.internalDB.model.proxy;

import java.util.Date;

/**
 * Created by Сергей on 12.10.2015.
 */
public class PersonProxy {
    final long id;
    final String surname;
    final String middleName;
    final String firstName;
    final Date date_of_birth;

    public PersonProxy(long id, String surname, String middleName, String firstName, Date date_of_birth) {
        this.id = id;
        this.surname = surname;
        this.middleName = middleName;
        this.firstName = firstName;
        this.date_of_birth = date_of_birth;
    }

    public long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }
}
