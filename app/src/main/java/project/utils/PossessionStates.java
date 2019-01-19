package project.utils;

/**
 * Created by VINCENT on 08/03/2016.
 */
public abstract class PossessionStates {

    public static final int NOT_WANTED = 0;
    public static final int IN_POSSESSION = 1;
    public static final int WANTED = 2;
    public static final int IN_DELIVERY = 3;
    public static final int RESERVED = 4;
    public static final int TO_ORDER_AT_BDFUGUE = 5;
    public static final int TO_RESERVE_AT_CULTURA = 6;
    public static final int PRESENT = 7;

    public static int getStatesCount() {
        return 8;
    }

}
