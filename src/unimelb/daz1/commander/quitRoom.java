package unimelb.daz1.commander;

import unimelb.daz1.Room;
import unimelb.daz1.User;

/**
 * Created by davidzd on 15/9/20.
 */
public class quitRoom {
    public static synchronized void quitroom (User user, Room room){
        if(room == null || user == null){
            return;
        }
        room.getUserList().remove(user);
        if(room != Room.MainHall){
            if(room.getRoomowner() == null){
                if(room.getUserList().isEmpty()){
                    deleteRoom.deleteroom(null, room);
                }
            }


        }
    }
}
