package adventuregame;

/**
 *
 * @author William Kaminski
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Character {
    
    public int characterExperience;
    
    ////////////////////////////////////////////////////////////////////////////Inserts specific data into the table from the uesr's input.
    public void addInfo(String FirstName, String LastName, String Class, int HealthPoints, Connection conn){
        String sql = "Update CharacterInfo set First_Name = ? " 
                + "where CharacterId = ?";                      
        this.inputStringInfo(sql, FirstName, conn);
        
        sql = "Update CharacterInfo set Last_Name = ? "
                + "where CharacterId = ?";
        this.inputStringInfo(sql, LastName, conn);
        
        sql = "Update CharacterInfo set Character_Class = ? "
                + "where CharacterId = ?";
        this.inputStringInfo(sql, Class, conn);
        
        sql = "Update CharacterInfo set Health_Points = ? "
                    + "where CharacterId = ?";
        this.inputIntInfo(sql, HealthPoints, conn);
    }
    
    public void inputStringInfo(String sql, String input, Connection conn){
        int CharacterId = 1;
        
        PreparedStatement pstmt = null; 
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, input);
            pstmt.setInt(2, CharacterId);
            pstmt.execute();
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("inputStringInfo");
            
        }finally { //closes all connections so none of the tables lock.
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
    
    public void inputIntInfo(String sql, int input, Connection conn){
        int CharacterId = 1;
        
        PreparedStatement pstmt = null;
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, input);
            pstmt.setInt(2, CharacterId);
            pstmt.execute();
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("inputIntInfo");
            
        }finally { //closes all connections so none of the tables lock.
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////Referencing the actual CharacterInfo table to obtain the level, health, name, and experience
    public int getLevel(Connection conn){
        int CharacterId = 1;
        int CharacterLevel = 0;
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select Character_Level from CharacterInfo where "
                + "CharacterId = ?";
        
        try{
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, CharacterId);
           rs = pstmt.executeQuery();
           CharacterLevel = rs.getInt("Character_Level");
           
        } catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getLevel");
           
       }finally { //closes all connections so none of the tables lock.
            if (rs != null) {
                 try {
                 rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
        return CharacterLevel;
    }
    
    ////////////////////////////////////////////////////////////////////////////Getting the original health value from the stats table
    public int GetHealthFromStats(String ClassName, Connection conn){
        int HealthPoints = 0;
        
        String ClassId;
        ClassId = this.GetClassId(ClassName, conn);
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select Health_Points from ClassStatsInfo, "
                + "ClassCharacteristics"
                + " where ClassCharacteristics.ClassId = ? and "
                + "ClassStatsInfo.StatsId = "
                + "ClassCharacteristics.ClassId group by Health_Points";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ClassId);
            rs = pstmt.executeQuery();
            HealthPoints = rs.getInt("Health_Points");
            
            } catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("GetHealthFromStats");
            
            } finally { //closes all connections so none of the tables lock.
                if (rs != null) {
                    try {
                    rs.close();
                    } catch (SQLException e) { /* Ignored */}
                }
                if (pstmt != null) {
                    try {
                    pstmt.close();
                    } catch (SQLException e) { /* Ignored */}
                }
        
            } 
            return HealthPoints;
    }
    
    public void updatePlayerHealth(Connection conn){
        PlayGame game = new PlayGame();
        
        String sql = "Update CharacterInfo Set Health_Points = ?";
        
        PreparedStatement pstmt = null;
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, game.characterHealth);
            pstmt.execute();
           
        } catch (SQLException e){
            
            System.out.println(e.getMessage());
            System.out.println("updateHealth");
           
        }finally {
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
    }
    
    public int getHealth(Connection conn){
        int characterHealth = 0;
        
        String sql = "Select Health_Points From CharacterInfo";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
           pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           characterHealth = rs.getInt("Health_Points");
           
       } catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getHealth");
           
       }finally { //closes all connections so none of the tables lock.
            if (rs != null) {
                 try {
                 rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
        
        return characterHealth;
    }
    
    ////////////////////////////////////////////////////////////////////////////Gets all of the class names for the user so the user can pick which one they want to be, then it uses the users input to obtain the classid
    public void GetClassName(Connection conn){
       Statement stmt = null;
       ResultSet rs = null;
       
       String sql = "Select Class_Name From ClassInfo";     
        try{  
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            List<String> ClassName = new ArrayList<>();

            int i = 0;
            while (rs.next()) {
                ClassName.add(rs.getString("Class_Name"));
                if(i < 4) {
                    System.out.print(ClassName.get(i) + ", ");
                    i++;
                }else {
                    System.out.println("and " + ClassName.get(i) + ".");
                    i++;
                }
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
            System.out.println("GetClassName");
           
        }finally { //closes all connections so none of the tables lock.
            if (rs != null) {
                 try {
                 rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (stmt != null) {
                try {
                stmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }  
    }
    
    public String GetClassId(String ClassName, Connection conn){
        String sql = "Select ClassId From ClassInfo where Class_Name = ?";
        String ClassId = " ";
        
        switch(ClassName){
            case "Warrior": //If the ClassName is Warrior, it will get the ClassId corresponding to this.
                ClassId = this.FindId(ClassName, sql, conn);
                break;
            case "Thief": //If the ClassName is Thief, it will get the ClassId corresponding to this.
                ClassId = this.FindId(ClassName, sql, conn);
                break;
            case "Wizard": //If the ClassName is Wizard, it will get the ClassId corresponding to this.  
                ClassId = this.FindId(ClassName, sql, conn);
                break;
            case "Battle Mage": //If the ClassName is Battle Mage, it will get the ClassId corresponding to this.              
                ClassId = this.FindId(ClassName, sql, conn);
                break;
            case "Assassin": //If the ClassName is Assassin, it will get the ClassId corresponding to this.
                ClassId = this.FindId(ClassName, sql, conn);
                break;         
            } 
        return ClassId;
   }
    
   public String FindId(String ClassName, String sql, Connection conn){
       String ClassId = " ";
       
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       
       try{
           pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, ClassName);
           rs = pstmt.executeQuery();
           ClassId = rs.getString("ClassId");
           
       } catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("FindId");
           
       }finally { //closes all connections so none of the tables lock.
            if (rs != null) {
                 try {
                 rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
       return ClassId;
   }
   
   /////////////////////////////////////////////////////////////////////////////
   public void getCurrentExperience(Connection conn){
       String sql = "Select Experience from CharacterInfo";
       
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       
       try{
           pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           characterExperience = rs.getInt("Experience");
           
       } catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getCurrentExperience");
           
       }finally { //closes all connections so none of the tables lock.
            if (rs != null) {
                 try {
                 rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
       
   }
   
}
