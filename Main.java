//javac -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main.java
//java -cp ".:/Users/krishshah/all coding stuff/java_mini_project/lib/postgresql-42.7.13.jar:/Users/krishshah/all coding stuff/java_mini_project" Main
import java.sql.*;
import db.DBConnection;

class Main{
    public static void main(String args[]){
        try(
            Connection connection=DBConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM test");
        ){
            ResultSet result=(statement.executeQuery());
            while(result.next()){
                System.out.println(result.getInt("id"));
                System.out.println(result.getString("name"));
            }

        }catch(SQLException e){
            System.out.println("SQL ERROR"+e);
        }catch(Exception e){
            System.out.println("Unknown error");
            e.printStackTrace();
        }
        

    }
}