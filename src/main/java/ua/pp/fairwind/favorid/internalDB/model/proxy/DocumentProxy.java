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
    final String respondent_firm;
    final String respondent_person;
    final String agriment;
    final String date;

    public DocumentProxy(long id, String documentType, String number, String name, String respondent_firm, String respondent_person, String agriment, Date date) {
        this.id = id;
        this.documentType = documentType;
        this.number = number;
        this.name = name;
        this.respondent_firm=respondent_firm;
        this.respondent_person=respondent_person;
        this.agriment=agriment;
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

    public String getDate() {
        return date;
    }

    public String getRespondent_firm() {
        return respondent_firm;
    }

    public String getRespondent_person() {
        return respondent_person;
    }

    public String getAgriment() {
        return agriment;
    }
}
