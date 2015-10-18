package ua.pp.fairwind.favorid.internalDB.model.proxy;

/**
 * Created by Сергей on 18.10.2015.
 */
public class CounterpartProxy {
    final long id;
    final String shortName;
    final String fullName;

    public CounterpartProxy(long id, String shortName, String fullName) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }
}
