package mineward.utils.utils.location;

import mineward.utils.features.EntityHandler;
import mineward.utils.features.PickupHandler;

public class Location {

    private static Locations location = Locations.NONE;

    public static void setLocation(Locations newLocation) {
        if (newLocation ==  null) return;

        location = newLocation;

        //cleanup
        PickupHandler.clearPickups();
        EntityHandler.clear();
    }

    public static boolean inLocation(Locations compare) {
        return location == compare;
    }

    public static Locations getLocation() {
        return location;
    }

    public static boolean inAncient() {
        return location == Locations.ANCIENT_TOMB_T1 ||
                location == Locations.ANCIENT_TOMB_T2 ||
                location == Locations.ANCIENT_TOMB_T3;
    }
}
