package ua.pp.fairwind.favorid.internalDB.model.document;

/**
 * Created by Сергей on 16.10.2015.
 */
public enum SECURITY_ACTION {
    ALL_ACTION,
    VIEW_ACTION,
    EDIT_ACTION,
    DELETE_ACTION;

    public static SECURITY_ACTION fromInteger(int x) {
        switch(x) {
            case 0:
                return ALL_ACTION;
            case 1:
                return VIEW_ACTION;
            case 2:
                return EDIT_ACTION;
            case 3:
                return DELETE_ACTION;
        }
        return null;
    }

    public static Integer toInteger(SECURITY_ACTION x) {
        switch(x) {
            case ALL_ACTION:
                return 0;
            case VIEW_ACTION:
                return 1;
            case EDIT_ACTION:
                return 2;
            case DELETE_ACTION:
                return 3;
        }
        return null;
    }
}
