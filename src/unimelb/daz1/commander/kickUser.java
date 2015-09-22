package unimelb.daz1.commander;

import javafx.util.Pair;
import unimelb.daz1.Room;
import unimelb.daz1.User;

import java.util.Date;

/**
 * Created by davidzd on 15/9/20.
 */
//KickUser sould fulfill conditions: owner, in the same room, user >#kick "loserid" to kick the user and
//the kicked user cannot join the room in time range of 3600;
public class kickUser {
    public static synchronized boolean kickuser(Room room, User owner, User loser, Integer time) {

        Boolean result = false;
    if(loser!=owner) {
        if (room.getRoomowner() == owner && room.getUserList().contains(loser)) {
            Date now = new Date();
            Pair<Date, Integer> kickinfo = new Pair<>(now, time);
            room.loserlist.put(loser, kickinfo);
            joinRoom.joinroom(loser, Room.MainHall, room);
            result = true;
        }
    }
        return result;
    }
}
