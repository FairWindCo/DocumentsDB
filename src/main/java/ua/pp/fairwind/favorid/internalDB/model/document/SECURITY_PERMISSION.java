package ua.pp.fairwind.favorid.internalDB.model.document;

/**
 * Created by Сергей on 16.10.2015.
 */
public enum SECURITY_PERMISSION {
    RESTRICT,
    PERMIT;

    public static SECURITY_PERMISSION fromInteger(int x) {
        switch(x) {
            case 0:
                return RESTRICT;
            case 1:
                return PERMIT;
        }
        return null;
    }

    public static Integer toInteger(SECURITY_PERMISSION x) {
        switch(x) {
            case RESTRICT:
                return 0;
            case PERMIT:
                return 1;
        }
        return null;
    }
}
