package tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnexion {
    public String url="jdbc:mysql://localhost:3308/pi_dev";
    public  String login="root";
    public String pwd="";
    public  static Myconnexion instance;
    Connection cnx ;
    public Myconnexion(){
        try {
           cnx= DriverManager.getConnection(url, login, pwd);
            System.out.println("connexion etabli");
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    public Connection getCnx() {
        return cnx;
    }

    public static Myconnexion getInstance() {
        if(instance== null){
            instance=new Myconnexion();
        }
        return instance;
    }
}
