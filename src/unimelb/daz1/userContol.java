package unimelb.daz1;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by davidzd on 15/9/20.
 */
public class userContol {
	static Set<User> alluserlist = new HashSet<User>();

	public static synchronized User create(){

		int postfix = 1;
		for( ; ; postfix++) {
			String id = "guest" + postfix;
			User guest = null;
			for (User g : alluserlist) {
				if (g.getUserId().equals(id)) {
					guest = g;
					break;
				}
			}
			if (guest == null)
				break;
		}
		User newguest = new User("guest"+postfix);

		alluserlist.add(newguest);
		return newguest;
	}

	public static synchronized Boolean verifyIdChange(User user, String newname){
		Boolean result = false;
		String pattern = "^[a-zA-Z]\\w{2,15}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(newname);
		if(!m.matches()){
			return result;
		}
		Boolean idUsed = false;
		for(User u : alluserlist){
			if(u.getUserId().equals(newname)){
				idUsed = true;
				break;
			}
		}
		String former = user.getUserId();

		if(!former.equals(newname) && (!idUsed)){
			user.setName(newname);
			result = true;
		}
		return result;
	}

	public static User findGuest(String username){
		User user = null;
		for(User u : alluserlist){
			if(u.getUserId().equals(username)){
				user = u;
				break;
			}
		}
		return user;
	}

	public synchronized static void removeGuest (User user){
		alluserlist.remove(user);
	}
}
