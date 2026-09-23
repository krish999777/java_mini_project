package session;
import data.Role;

public class Session {
    private static int id;
    private static Role role;
    public static void start(int i,Role r) {
        id = i;
        role = r;
    }
    public static int getId(){
        return id;
    }
    public static Role getRole(){
        return role;
    }
    public static void logout(){
        id=0;
        role=null;
    }
}

