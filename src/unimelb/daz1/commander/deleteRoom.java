package unimelb.daz1.commander;

import unimelb.daz1.Room;
import unimelb.daz1.Server;
import unimelb.daz1.User;
import unimelb.daz1.serverJson;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by davidzd on 15/9/20.
 */
public class deleteRoom {
    //delte the selected room ::only by owner
    public static synchronized boolean deleteroom(User user, Room room) {
        //include the room in the whole roomlist and the user should be the owner
        if (!Server.roomlist.contains(room) || (room.getRoomowner() != user)
                || (room == Room.MainHall)) {
            return false;
        } else {
            //copyuserlist to delete and operate some of the users or the iteration of sending message;
            Set<User> copyuserlist = new HashSet<>(room.getUserList());
            for (User u : copyuserlist) {
                joinRoom.joinroom(u, Room.MainHall, room);
                try {
                    Room.MainHall.sendtoAll(serverJson.sendChangeRoom(room, Room.MainHall, u));
                    System.out.println(serverJson.sendChangeRoom(room,Room.MainHall,u));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Server.roomlist.remove(room);
            return true;
        }
    }
}
