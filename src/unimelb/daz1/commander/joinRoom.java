package unimelb.daz1.commander;

import unimelb.daz1.Room;
import unimelb.daz1.User;

/**
 * Created by davidzd on 15/9/20.
 */
/*
    Join room should contains two steps:
    1. Quit the former room;
    2. Join the new room;
 */
public class joinRoom {
    public static synchronized boolean joinroom(User user, Room newRoom, Room formerRoom) {
        Boolean success = false;

        if (user != null && formerRoom != newRoom && newRoom.judgejoin(user)) {
            quitRoom.quitroom(user, formerRoom);
            newRoom.addUser(user);
            user.setroom(newRoom);
            success = true;
        }
        return success;
    }
}
