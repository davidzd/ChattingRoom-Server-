package unimelb.daz1;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javafx.util.Pair;

import org.json.simple.JSONObject;
/**
 * Created by davidzd on 15/9/20.
 */
public class Room {
    // Userlist Storing the corresponding users in each room
    private Set<User> userlist = new HashSet<User>();
    // name of room
    public static Room MainHall = new Room("MainHall",null);
    private String roomid;
    private User roomowner;
    public HashMap<User,Pair>loserlist = new HashMap<>();
    // Constructor of unimelb.daz1.Room ;
    public Room(String roomid, User roomowner) {
        this.roomid = roomid;
        this.roomowner = roomowner;
    }

    //Add user to the userlist for store all users
    public synchronized void addUser(User user) {
        userlist.add(user);
    }
    //Remove user from the whole userlist
    public synchronized void removeUser(User user){
        userlist.remove(user);
    }
    // initialize room
    public void setRoom(String roomname) {
        this.roomid = roomname;
    }

    public String getroomid() {
        return roomid;
    }
    public User getRoomowner(){
        return roomowner;
    }
    public Set<User> getUserList(){
        return userlist;
    }
    public JSONObject getroomInfo (){
        JSONObject roomInfo = new JSONObject();
        roomInfo.put("roomid",this.getroomid());
        roomInfo.put("count", userlist.size());
        return roomInfo;
    }

    public void sendtoAll(JSONObject obj) throws IOException {

        for (User user : this.userlist) {
            System.out.println("userlist");
            user.sendjson(obj);
        }
    }
    public Boolean judgejoin(User guest) {
        if (loserlist.containsKey(guest)) {
            Pair<Date, Integer> kickinfo = loserlist.get(guest);
            Date kicktimepoint = (Date) kickinfo.getKey();
            Integer timearrange = kickinfo.getValue();
            Date now = new Date();
            if ((now.getTime() - kicktimepoint.getTime()) > (long) timearrange * 1000) {
                loserlist.remove(guest);
                return true;
            } else
                return false;
        }
        return true;
    }

}
