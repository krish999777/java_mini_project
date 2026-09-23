package myExceptions;

public class MissingFieldException extends Exception{
    String field;
    public MissingFieldException(String f){
        field=f;
    }
    public String toString(){
        return "Cannot have "+field+" empty";
    }
}
