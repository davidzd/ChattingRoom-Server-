package unimelb.daz1;

import java.io.IOException;

import org.json.simple.JSONObject;
import unimelb.daz1.commander.*;

/**
 * Created by davidzd on 15/9/20.
 */

public class messageControl {

    public void inputControl(JSONObject injson, User user) {
        String command = injson.get("type").toString();
        Room room = null;
        JSONObject response = null;
        boolean result = false;
        if (command != null) {
            if (command.equals("errormessage")) {
                try {
                    user.sendjson(serverJson.sendMessage("Plz check Your Input", "System.ERROR: "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //join the room should firsty quit the cunrrent room
            if (command.equals("join")) {
                System.out.println(injson.toJSONString());
                String roomname = injson.get("roomid").toString();
                Room former = user.getroom();
                Room newroom = Server.checkRoom(roomname);
                if (newroom != null) {
                boolean sign = joinRoom.joinroom(user, newroom, former);
                    if (sign) {
                        response = serverJson.sendChangeRoom(former, newroom, user);
                        try {
                            newroom.sendtoAll(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        response = serverJson.sendMessage("Invalid Room Name : " + injson.get("roomid"), "SYSTEM: ");
                        try {
                            user.sendjson(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    response = serverJson.sendMessage("Invalid Room Name : " + injson.get("roomid"), "SYSTEM: ");
                    try {
                        user.sendjson(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
            //all connected user should get the information;
            if (command.equals("identitychange")) {
                String former = user.getUserId();
                String newname = injson.get("identity").toString();
                result = identityChange.identitychange(injson, user);
                userContol uc = new userContol();
                if (result) {
                    user.setName(newname);
                    try {
                         for(Room r: Server.roomlist){
                            r.sendtoAll(serverJson.sendNewidentity(newname, former));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    response = serverJson.sendMessage("Invalid Name : " + injson.get("identity"), "SYSTEM: ");
                    try {
                        user.sendjson(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            if (command.equals("who")) {
                String roomid = injson.get("roomid").toString();
                Room roomnew = Server.checkRoom(roomid);
                if (roomnew != null) {
                    response = serverJson.roomWho(roomid, user);
                } else {
                    response = serverJson.sendMessage("Roomid doesn't exist", "System: ");
                }
                try {
                    user.sendjson(response);
                    System.out.println(response.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (command.equals("list")) {
                try {
                    user.sendjson(serverJson.roomList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("who's room : " + command);
            }
            if (command.equals("kick")) {
                userContol uc = new userContol();
                String losername = injson.get("identity").toString();
                User loser = uc.findGuest(losername);
                boolean kickid = kickUser.kickuser(user.getroom(), user, loser, 3600);
                if (kickid) {
                    response = serverJson.sendChangeRoom(user.getroom(), Room.MainHall, loser);
                    try {
                        System.out.print("123");
                        loser.sendjson(response);
                        user.getroom().sendtoAll(serverJson.sendMessage(losername + " has been kicked out by " + user.getUserId() + " !!!", "System: "));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    response = serverJson.sendMessage(loser.getUserId() +
                            " cannot be moved out,Plz check your command", "System: ");
                    try {
                        user.sendjson(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (command.equals("createroom")) {
                room = createRoom.createroom(injson, user);
                if (room != null) {
                    response = serverJson.sendMessage("Successfully Created ROOM : " + room.getroomid(), "SYSTEM: ");
                } else {
                    response = serverJson.sendMessage("Invalid Name : " + injson.get("roomid"), "SYSTEM: ");
                }
                try {
                    user.sendjson(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (command.equals("delete")) {
                String deleteroomid = injson.get("roomid").toString();
                Room deleteroom = Server.checkRoom(deleteroomid);
                boolean state = deleteRoom.deleteroom(user, deleteroom);
                if (state) {
                    try {
                        response = serverJson.sendMessage("Delete Room: "+ deleteroomid,"System: ");
                        user.sendjson(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    response = serverJson.sendMessage("You cannot delete the room !", "System: ");
                    try {
                        user.sendjson(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
            if (command.equals("message")) {

                try {
                    JSONObject msg = serverJson.sendMessage(
                            injson.get("content").toString(), user.getUserId());

                    // Find the mistake here
                    System.out.print(msg.toJSONString());
                    user.getroom().sendtoAll(msg);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        }
    }
}
