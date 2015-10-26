package ua.pp.fairwind.favorid.internalDB.model.storehouses;

/**
 * Created by Сергей on 13.10.2015.
 */
public enum MOVEMENT_TYPES {
    ARRIVAL,
    SHIPMENT,
    MOVE,
    DEFECTIVE,
    UTILIZATION,
    COMBINED;

    public static MOVEMENT_TYPES fromInteger(int x) {
        switch(x) {
            case 0:
                return ARRIVAL;
            case 1:
                return SHIPMENT;
            case 2:
                return MOVE;
            case 3:
                return COMBINED;
            case 4:
                return UTILIZATION;
            case 5:
                return DEFECTIVE;
        }
        return null;
    }

    public static Integer toInteger(MOVEMENT_TYPES x) {
        switch(x) {
            case ARRIVAL:
                return 0;
            case SHIPMENT:
                return 1;
            case MOVE:
                return 2;
            case COMBINED:
                return 3;
            case UTILIZATION:
                return 4;
            case DEFECTIVE:
                return 5;
        }
        return null;
    }
}
