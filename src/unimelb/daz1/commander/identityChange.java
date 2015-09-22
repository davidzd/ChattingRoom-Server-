package unimelb.daz1.commander;

import org.json.simple.JSONObject;
import unimelb.daz1.Room;
import unimelb.daz1.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import unimelb.daz1.userContol;

/**
 * Created by davidzd on 15/9/19.
 */
//Change name for new id
public class identityChange {
    //New name must be unique
    public static synchronized boolean identitychange(JSONObject injson, User user) {
        String newname = injson.get("identity").toString();
        userContol uc = new userContol();
        return uc.verifyIdChange(user,newname);

    }
}

