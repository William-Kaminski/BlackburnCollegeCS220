package adventuregame;

/**
 *
 * @author William Kaminski
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Scanner;

public class AdventureGame {

    public static Connection connect;
    
    //This method connects this whole file to the database.
    public Connection connect(){
        Connection conn = null;
        String url = "jdbc:sqlite:C:/Users/William Kaminski/Documents/Sqlite/SQLDatabase/Game.db";
        try{
            conn = DriverManager.getConnection(url);
            //System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
            System.out.println("Connection error");
        }
        return conn;
    }
    
    //This is the main method for the game itself.
    public static void main(String[] args) throws SQLException {
        AdventureGame connecting = new AdventureGame();
        Character character = new Character();
        Item item = new Item();
        PlayGame game = new PlayGame();
        
        Scanner in = new Scanner(System.in);
        
        try{
            connect = connecting.connect();
            connect.setAutoCommit(false); //This is begins the transaction
            Savepoint savePoint = connect.setSavepoint();
        
            String Name;
            String FirstName;
            String LastName;
            String Class;    
            int HealthPoints;
        
            System.out.println("Hello, welcome to the game. First off why not start "
                + "by telling me your name.");
            Name = in.nextLine();
            FirstName = Name.substring(0, Name.indexOf(' '));
            LastName = Name.substring(Name.indexOf(' ') + 1);

            System.out.println();
            
            System.out.println("Well now that we got your name. Here are the classes"
                + " you can chose from:");
            character.GetClassName(connect);
            System.out.println();
            System.out.println("*Right now, only the Warrior works correctly.");
            System.out.println();
            System.out.println("So which class would you like to be?");
            Class = in.nextLine();
            HealthPoints = character.GetHealthFromStats(Class, connect);
            
            character.addInfo(FirstName, LastName, Class, HealthPoints, connect);
            item.setCharacterItems(character.GetClassId(Class, connect), connect);

            System.out.println();

            game.StartGame(connect, Class);
            
            connect.rollback(savePoint); //This is the commit to push the data to the table. 
            connect.commit();
            connect.setAutoCommit(true); //This makes it so the transaction is done.
            connect.close();
            
        }catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("AdventureGameMainMethod");
                
        }
    
    
    }
        
}
