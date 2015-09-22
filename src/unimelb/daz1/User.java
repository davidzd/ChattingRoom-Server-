package unimelb.daz1;

import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.JSONObject;
/**
 * Created by davidzd on 15/9/20.
 */
public class User {
	// String userid for each user
	private String userid;
	// unimelb.daz1.Room class for new object
	private Room room;
	// Socket class for store each socket from each user.
	private PrintWriter out;

	// get unimelb.daz1.User room
	public Room getroom() {
		return this.room;
	}

	// Set the user in correspoding unimelb.daz1.Room
	public void setroom(Room room) {
		this.room = room;
	}

	public User(String identity) {
		this.userid = identity;
	}

	public void saveSocketOut(PrintWriter out) {
		this.out = out;

	}

	public String getUserId() {
		return this.userid;
	}

	protected void setName(String identity) {
		this.userid = identity;
	}

	public void sendjson(JSONObject obj) throws IOException {
		out.println(obj.toJSONString());
		out.flush();
	}

}
