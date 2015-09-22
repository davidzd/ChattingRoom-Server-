package unimelb.daz1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;
/**
 * Created by davidzd on 15/9/20.
 */
public class serverJson {
    @SuppressWarnings("unchecked")
    public static JSONObject sendChangeRoom(Room former, Room newRoom, User user) {
        JSONObject obj = new JSONObject();
        obj.put("type", "roomchange");
        obj.put("identity", user.getUserId());
        if (former == null) {
            obj.put("former", "");
        } else {
            obj.put("former", former.getroomid());
        }
        if (newRoom == null) {
            obj.put("roomid", "");
        } else {
            obj.put("roomid", newRoom.getroomid());
        }
        return obj;
    }


    @SuppressWarnings("unchecked")
    public static JSONObject sendMessage(String content, String identity) {
        JSONObject obj = new JSONObject();
        obj.put("type", "message");
        obj.put("identity", identity);
        obj.put("content", content);
        return obj;
    }


    @SuppressWarnings("unchecked")
    public static JSONObject sendNewidentity(String identity, String former) {
        JSONObject obj = new JSONObject();
        obj.put("type", "newidentity");
        obj.put("former", former);
        obj.put("identity", identity);
        return obj;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject roomList() {
        JSONObject obj = new JSONObject();
        JSONArray roomInfo = new JSONArray();
        for (Room r : Server.roomlist) {
            roomInfo.add(r.getroomInfo());
        }
        obj.put("type", "roomlist");
        obj.put("rooms", roomInfo);
        return obj;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject roomWho(String roomid,User user) {
        JSONObject obj = new JSONObject();
        Room room = Server.checkRoom(roomid);
        ArrayList<String> arrayList = new ArrayList<>();
        for(User u : room.getUserList()){
            arrayList.add(u.getUserId());
        }

        obj.put("type", "roomcontents");
        obj.put("roomid", room.getroomid());
        if(!room.getUserList().isEmpty()) {
            obj.put("identities", arrayList);
        }else{
            obj.put("identities", "");
        }
        if(room == Room.MainHall) {
            obj.put("owner", "");
        }else {
            obj.put("owner", room.getRoomowner().getUserId());
        }
        return obj;
    }




}