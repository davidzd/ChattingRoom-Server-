package unimelb.daz1.commander;

import unimelb.daz1.*;
import org.json.simple.*;
import unimelb.daz1.User;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by davidzd on 15/9/19.
 */
//create new room to server;
public class createRoom {
    public static synchronized Room createroom(JSONObject injson, User user) {
        String roomname = injson.get("roomid").toString();
        String pattern = "^[a-zA-Z]\\w{2,31}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(roomname);
        //User regex to match the input all room name;
        if (!m.matches()) {
            return null;

        }
        //find the room of roomname;
        Room room = Server.checkRoom(roomname);
        Room newRoom = null;
        //If the roomname doesn't exist we can create the room;
        if (room == null) {
            newRoom = new Room(roomname, user);
            Server.roomlist.add(newRoom);
        }
        return newRoom;
    }


}
