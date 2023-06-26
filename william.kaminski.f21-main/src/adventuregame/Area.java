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

public class Area {
    
    public static int[] characterLocation = new int[2];
    public static int row;
    public static int column;
    
    ////////////////////////////////////////////////////////////////////////////
    public String[][] Area1 (Connection conn){
        int row = 8;
        int column = 3;
        
        String[][] Area = new String[row][column];
        
        for(int r = 0; r < Area.length; r++) {
            for(int c = 0; c < Area[r].length; c++) {
                Area[r][c] = this.getAreaArray(r, c, 1, conn);
            }
        }
        //this.printAreaArray(Area, 1, conn);
        return Area;
    }
    
    public String[][] Area2(Connection conn){
        int row = 11;
        int column = 9;
        
        String[][] Area = new String[row][column];
        
        for(int r = 0; r < Area.length; r++) {
            for(int c = 0; c < Area[r].length; c++) {
                Area[r][c] = this.getAreaArray(r, c, 2, conn);
            }
        }
        //this.printAreaArray(Area, 2, conn);
        return Area;
    }
    
    public String[][] Area3(Connection conn){
        int row = 17;
        int column = 10;
        
        String[][] Area = new String[row][column];
        
        for(int r = 0; r < Area.length; r++) {
            for(int c = 0; c < Area[r].length; c++) {
                Area[r][c] = this.getAreaArray(r, c, 3, conn);
            }
        }
        //this.printAreaArray(Area, 3, conn);
        return Area;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public String getAreaArray (int row, int column, int specificArea, Connection conn){
        String getValue = null;
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select Column" + column + " From Area" + specificArea + 
                " where row = ?";
        
        try{
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, row);
           rs = pstmt.executeQuery();
           getValue = rs.getString("Column" + column);
           //System.out.println(column);
           
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getAreaArray " + specificArea);
           
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
        
        return getValue;
    }
    
    public void printAreaArray(String[][] Area, int specificArea, Connection conn){
        switch (specificArea) {
            case 1:
                for(int r = 0; r < Area.length; r++) {
                    for (int c = 0; c < Area[c].length; c++) {
                        System.out.print(Area[r][c] + "\t");
                    }
                    System.out.println();
                }
            break;

            case 2:
                for (int r = 0; r < Area.length; r++) {
                    for (int c = 0; c < Area[c].length; c++) {
                        System.out.print(Area[r][c] + "\t");
                        }
                    System.out.println();
                }
            break;

            case 3:
                for (int r = 0; r < Area.length; r++) {
                    for (int c = 0; c < Area[c].length; c++) {
                        System.out.print(Area[r][c] + "\t");
                    }
                    System.out.println();
                }
            break;

        }
    }
    
    ////////////////////////////////////////////////////////////////////////////Whenever I get a specific area like item or enemy, i will put dead or got after so it will be easier to find the right values in the array
    public boolean specificLocation(String[][] Area, String userInput, int[] cLocation, int Map, Connection conn){
        boolean checkedArea = false;
        String specificArea;
        
        specificArea = this.checkSpecificLocation(userInput, cLocation, Map, Area, conn);
        
        //System.out.println(specificArea);
        
        switch(specificArea){
            case "XXXXX":
              
                checkedArea = false;
              
            break;
            
            case " ":
                
                checkedArea = true;
                
            break;
            
            case "Finish":
                
                checkedArea = true;
                
            break;
                
            case "Enemy":
                
                checkedArea = true;
                
            break;
            
            case "Item":
                
                checkedArea = true;
                
            break;
            
            case "Start":
                
                checkedArea = true;
                
            break;
        }
        return checkedArea;
    }
    
    public String CheckingLocation(String[][]Area, int Row, int Column){
        String specificArea = null;
        
        for(int i = 0; i < Area.length; i++){
            for(int j = 0; j < Area[i].length; j++){
                if(i == Row && j == Column){
                    specificArea = Area[i][j];
                    return specificArea;
                }
            }
        }
        
        return specificArea;
    }
    
    public String checkSpecificLocation(String userInput, int[] cLocation, int Map, String[][] Area, Connection conn){
        String specificArea = null;
        
        int[] northValue = new int[2];
        int[] eastValue = new int[2];
        int[] southValue = new int[2];
        int[] westValue = new int[2];
        
        int Row = cLocation[0];
        int Column = cLocation[1];
        
        switch(userInput){
            case "North":
                        
                northValue[0] = Row - 1;
                northValue[1] = Column;
                        
                Row = northValue[0];
                Column = northValue[1];
                            
                        //System.out.println(northValue[0]);
                        //System.out.println(northValue[1]);
                        
                specificArea = this.CheckingLocation(Area, Row, Column);
                        
            break;
                    
            case "East":
                        
                eastValue[0] = Row;
                eastValue[1] = Column + 1;
                        
                Row = eastValue[0];
                Column = eastValue[1];
                        
                //System.out.println(eastValue[0]);
                //System.out.println(eastValue[1]);
                        
                specificArea = this.CheckingLocation(Area, Row, Column);
                        
            break;
                    
            case "South":
                        
            southValue[0] = Row + 1;
            southValue[1] = Column;
                        
            Row = southValue[0];
            Column = southValue[1];
                        
            specificArea = this.CheckingLocation(Area, Row, Column);
                        
            break;
                    
            case "West":
                        
            westValue[0] = Row;
            westValue[1] = Column - 1;
                        
            Row = westValue[0];
            Column = westValue[1];
                        
            specificArea = this.CheckingLocation(Area, Row, Column);
                        
            break;
        }
        return specificArea;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public String getAreaId(int Map, Connection conn){
        String AreaId = "AR";
        
        AreaId = AreaId + Map;
        
        return AreaId;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public int[] getCharacterLocation(String[][] Area, int count){
        if(count == 0){
            for(int i = 0; i < Area.length; i++){
                for(int j = 0; j < Area[i].length; j++){
                    if(Area[i][j].equals("Start")){
                        row = i;
                        column = j;
                        
                        characterLocation[0] = row;
                        characterLocation[1] = column;
                        return characterLocation;
                    }
                }
            }
        }
        return characterLocation;
    }
    
    public String getIndividualLocationName(String[][] Area, int[] cLocation){
        String individualLocation = null;
        
        int Row = cLocation[0];
        int Column = cLocation[1];
        
        for(int i = 0; i < Area.length; i++){
            for(int j = 0; j < Area[i].length; j++){
                if(i == Row && j == Column){
                    individualLocation = Area[i][j];
                    //System.out.println(individualLocation);
                    return individualLocation;
                }
            }
        }
        
        return individualLocation;
    }
        
    public int[] changeCharacterLocation(String userInput, String[][] Area, int count){
        characterLocation = this.getCharacterLocation(Area, count);
        
        row = characterLocation[0];
        column = characterLocation[1];
        
        switch(userInput){
            case "North":
                row = row - 1;
                
                characterLocation[0] = row;
                characterLocation[1] = column;
                        
            break;
                    
            case "East":
                column = column + 1;
                        
                characterLocation[0] = row;
                characterLocation[1] = column;
                
            break;
                    
            case "South":
                row = row + 1;       
                        
                characterLocation[0] = row;
                characterLocation[1] = column;
                
            break;
                    
            case "West":
                column = column - 1;        
                        
                characterLocation[0] = row;
                characterLocation[1] = column;
                
            break;
        }
        
        return characterLocation;
        
    }
}
