package ua.pp.fairwind.favorid.internalDB.model.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 16.10.2015.
 */
public class DocumentProxy {
    static final SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
    final long id;
    final String documentType;
    final String number;
    final String name;
    final String from;
    final String to;
    final String from_person;
    final String to_person;
    final String date;

    public DocumentProxy(long id, String documentType, String number, String name, String from, String to, String from_person, String to_person, Date date) {
        this.id = id;
        this.documentType = documentType;
        this.number = number;
        this.name = name;
        this.from = from;
        this.to = to;
        this.from_person = from_person;
        this.to_person = to_person;
        this.date = formater.format(date);
    }

    public static SimpleDateFormat getFormater() {
        return formater;
    }

    public long getId() {
        return id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFrom_person() {
        return from_person;
    }

    public String getTo_person() {
        return to_person;
    }

    public String getDate() {
        return date;
    }
}
