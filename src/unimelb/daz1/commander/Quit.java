package unimelb.daz1.commander;

import org.json.simple.JSONObject;
import unimelb.daz1.Room;
import unimelb.daz1.User;
import unimelb.daz1.serverJson;
import unimelb.daz1.userContol;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by davidzd on 15/9/20.
 */
/*
    Quit:
    1. remove the user from all userlist
    2. if the user is the owner of one room, delete the room as well;
    3. remove the user from the userlist of the room
 */
public class Quit {
    public static void quit(User user) throws IOException {
        Room room = user.getroom();
        Set<User> copyuserlist = new HashSet<>(room.getUserList());
        copyuserlist.remove(user);
        if (user.getroom() != Room.MainHall) {
            if (user == user.getroom().getRoomowner()) {
                deleteRoom.deleteroom(user, user.getroom());
            }

            for (User u : copyuserlist) {
                JSONObject response = serverJson.sendChangeRoom(room, null, u);
                u.sendjson(response);
            }
        } else {
            for (User u : copyuserlist) {
                JSONObject response = serverJson.sendChangeRoom(room, null, user);
                u.sendjson(response);
            }
        }
        Room.MainHall.removeUser(user);
        userContol.removeGuest(user);

    }
}
