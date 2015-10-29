package ua.pp.fairwind.favorid.internalDB.model.requests;

/**
 * Created by Сергей on 29.10.2015.
 */
public enum REQUEST_TYPES {
    PURCHASE,
    SHIPMENT,
    PRODUCTION,
    REPAIR;

    public static REQUEST_TYPES fromInteger(int x) {
        switch(x) {
            case 0:
                return PURCHASE;
            case 1:
                return SHIPMENT;
            case 2:
                return PRODUCTION;
            case 3:
                return REPAIR;
        }
        return null;
    }

    public static Integer toInteger(REQUEST_TYPES x) {
        switch(x) {
            case PURCHASE:
                return 0;
            case SHIPMENT:
                return 1;
            case PRODUCTION:
                return 2;
            case REPAIR:
                return 3;
        }
        return null;
    }
}
