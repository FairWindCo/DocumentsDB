package ua.pp.fairwind.favorid.internalDB.model.document;

/**
 * Created by Сергей on 16.10.2015.
 */
public enum DOCUMENT_SECURITY_MODEL {
    ACCESS_FOR_ALL,
    ACCESS_FOR_OWNER,
    ACCESS_FOR_LIST;

    public static DOCUMENT_SECURITY_MODEL fromInteger(int x) {
        switch(x) {
            case 0:
                return ACCESS_FOR_ALL;
            case 1:
                return ACCESS_FOR_OWNER;
            case 2:
                return ACCESS_FOR_LIST;
        }
        return null;
    }

    public static Integer toInteger(DOCUMENT_SECURITY_MODEL x) {
        switch(x) {
            case ACCESS_FOR_ALL:
                return 0;
            case ACCESS_FOR_OWNER:
                return 1;
            case ACCESS_FOR_LIST:
                return 2;
        }
        return null;
    }
}
