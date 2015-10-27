package ua.pp.fairwind.favorid.internalDB.model.storehouses;

/**
 * Created by Сергей on 19.10.2015.
 */
public enum Units {
    KILOGRAMS,
    GRAMS,
    TONS,
    COUNT,
    LITRES,
    MILLILITRES,
    METERS,
    MILLIMETERS;

    public static Units fromInteger(int x) {
        switch(x) {
            case 0:
                return KILOGRAMS;
            case 1:
                return GRAMS;
            case 2:
                return TONS;
            case 3:
                return COUNT;
            case 4:
                return LITRES;
            case 5:
                return MILLILITRES;
            case 6:
                return METERS;
            case 7:
                return MILLIMETERS;
        }
        return null;
    }

    public static Integer toInteger(Units x) {
        switch(x) {
            case KILOGRAMS:
                return 0;
            case GRAMS:
                return 1;
            case TONS:
                return 2;
            case COUNT:
                return 3;
            case LITRES:
                return 4;
            case MILLILITRES:
                return 5;
            case METERS:
                return 6;
            case MILLIMETERS:
                return 7;
        }
        return null;
    }
}
