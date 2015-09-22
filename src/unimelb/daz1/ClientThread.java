package unimelb.daz1;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.*;
import org.json.simple.parser.*;
import unimelb.daz1.commander.Quit;

;

/**
 * Created by davidzd on 15/9/20.
 */
public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private User user;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            JSONParser parser = new JSONParser();
            this.out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), "UTF-8"));
            this.in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), "UTF-8"));
            initialize();
            try {
                String inString;
                while ((inString = in.readLine()) != null) {
                    JSONObject injson = (JSONObject) parser.parse(inString);
                    String type = injson.get("type").toString();
                    if (type.equals("quit")) {
                        String Id = user.getUserId();
                        Quit.quit(user);
                        JSONObject response = serverJson.sendChangeRoom(user.getroom(), null, user);
                        user.sendjson(response);
                        System.out.println(Id + " disconnected.");
                        break;
                    }

                    messageControl mc = new messageControl();
                    mc.inputControl(injson, user);
                    if (injson.equals("end"))
                        break;
                }
                in.close();
                out.close();
                socket.close();
            } catch (EOFException e) {
                if (socket != null)
                    socket.close();
                System.out.println("Client disconnected.");
            }
        } catch (IOException e) {
            e.printStackTrace();

            // A thread finishes if run method finishes
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
//Initialize the User;
    public void initialize() {
        // Insert new user to the room

        this.user = userContol.create();
        this.user.saveSocketOut(out);
        // room.newUser(identity, user);
        // Java creates new socket object for each connection.
        user.setroom(Room.MainHall);
        Room.MainHall.addUser(user);
        try {
            //Send the RoomList already Exist
            user.sendjson(serverJson.roomList());
            //Send the New Id of the User;
            user.sendjson(serverJson.sendNewidentity(user.getUserId(), ""));
            //Send the unimelb.daz1.Room Change;
            Room.MainHall.sendtoAll(serverJson.sendChangeRoom(null, Room.MainHall, user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        System.out.println("Client Connected... as " + user.getUserId());
    }
}
