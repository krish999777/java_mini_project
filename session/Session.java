package session;
import data.Role;

public class Session {
    int id;
    String name;
    Role role;
    Session(int id,String name,Role role){
        this.id=id;
        this.name=name;
        this.role=role;
    }
    int getId(){
        return id;
    }
    String getName(){
        return name;
    }
    Role getRole(){
        return role;
    }
}

