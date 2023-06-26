package adventuregame;

/**
 *
 * @author William Kaminski
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Scanner;

public class Enemy {
    
    public static String enemyName;
    public static String EnemyId;
    public static String EnemyType;
    
    public static int experience;
    public static int attack;
    public static int health;
    
    ////////////////////////////////////////////////////////////////////////////
    public String getEnemyName(Connection conn){
        String sql = "Select Enemy_Name From EnemyInfo Where EnemyId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
                    
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (EnemyId));
            rs = pstmt.executeQuery();
            enemyName = rs.getString("Enemy_Name");
                    
        }catch (SQLException e){
            
            System.out.println(e.getMessage());
            System.out.println("getEnemyName");
           
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            } if (pstmt != null) {
                try {
                    pstmt.close();
                    } catch (SQLException e) { /* Ignored */}
            }
        
        }
        return enemyName;
    }
    
    public String getEnemyType(Connection conn){
        String sql = "Select Enemy_Type From EnemyInfo Where EnemyId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String enemyType = "Enemy_Type";
        
        try{
                    
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (EnemyId));
            rs = pstmt.executeQuery();
            EnemyType = rs.getString(enemyType);
                    
        }catch (SQLException e){
            
            System.out.println(e.getMessage());
            System.out.println("getEnemyType");
           
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            } if (pstmt != null) {
                try {
                    pstmt.close();
                    } catch (SQLException e) { /* Ignored */}
            }
        
        }
        return EnemyType;
    }
    
    public int getEnemyExperience(Connection conn){
        String sql = "Select Enemy_Experience From EnemyInfo Where EnemyId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String EnemyExperience = "Enemy_Experience";
        
        experience = this.intTryCatch(sql, pstmt, rs, EnemyExperience, conn);
        
        return experience;
    }
    
    public int getEnemyAttack(Connection conn){
        String sql = "Select Damage From EnemyInfo Where EnemyId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String Damage = "Damage";
        
        attack = this.intTryCatch(sql, pstmt, rs, Damage, conn);
        
        return attack;
    }
    
    public int getEnemyHealth(Connection conn){
        String sql = "Select Health_Points From EnemyInfo Where EnemyId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String healthPoints = "Health_Points";
        
        health = this.intTryCatch(sql, pstmt, rs, healthPoints, conn);
        //System.out.println(health);
        return health;
    }
    
    public int intTryCatch(String sql, PreparedStatement pstmt, ResultSet rs, String columnName, Connection conn){
        int result = 0;
        
        try{
                    
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (EnemyId));
            rs = pstmt.executeQuery();
            result = rs.getInt(columnName);
                    
        }catch (SQLException e){
            
            System.out.println(e.getMessage());
            System.out.println("get" + columnName);
           
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            } if (pstmt != null) {
                try {
                    pstmt.close();
                    } catch (SQLException e) { /* Ignored */}
            }
        
        }
        
        return result;
    }
    
    public void setEnemy(String EnemyID, Connection conn){
        EnemyId = EnemyID;
        
        this.getEnemyName(conn);
        this.getEnemyType(conn);
        this.getEnemyHealth(conn);
        this.getEnemyAttack(conn);
        this.getEnemyExperience(conn);
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public String getRandomEnemy(int Map, Connection conn){
        Area area = new Area();
        
        String AreaId = area.getAreaId(Map, conn);
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select EnemyId From AreaPlacement Where AreaId = ?";
        
        switch(Map){
            case 1:
                
                try{
                    
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, (AreaId));
                    rs = pstmt.executeQuery();
                    EnemyId = rs.getString("EnemyId");
                    
                }catch (SQLException e){
            
                    System.out.println(e.getMessage());
                    System.out.println("getRandomEnemy");
           
                }finally {
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
                
            break;
            
            case 2:
                
                boolean getting = true;
                
                int random = (int)(6 * Math.random() + 1);
                
                while(getting == true){
                    //System.out.println(random);
                    random = (int)(6 * Math.random() + 1);
                    
                    switch(random){
                        case 1:
                            
                            EnemyId = "E1";
                            getting = false;
                            
                        break;
                        
                        case 2:
                            
                            EnemyId = "E2";
                            getting = false;
                            
                        break;
                        
                        case 6:
                            
                            EnemyId = "E3";
                            getting = false;
                            
                        break;
                    }
                }
                
            break;
            
            case 3:
                
                getting = true;
                
                random = (int)(6 * Math.random() + 1);
                
                while(getting == true){
                    
                    random = (int)(6 * Math.random() + 1);
                    
                    switch(random){
                        case 3:
                            
                            EnemyId = "E3";
                            getting = false;
                            
                        break;
                        
                        case 4:
                            
                            EnemyId = "E4";
                            getting = false;
                            
                        break;
                        
                        case 5:
                            
                            EnemyId = "E5";
                            getting = false;
                            
                        break;
                    }
                }
                
            break;
        }
        
        return EnemyId;
        
    }
}
