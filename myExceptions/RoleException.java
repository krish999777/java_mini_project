package myExceptions;

public class RoleException extends Exception {
    String requiredRole;
    public RoleException(String r){
        requiredRole=r;
    }
    public String toString(){
        return "Cannot access, needs "+requiredRole+" role";
    }
}
