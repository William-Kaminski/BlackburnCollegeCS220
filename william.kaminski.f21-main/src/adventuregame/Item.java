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
import java.util.Scanner;

public class Item {
    
    public static int cLength;
    
    public static String item;
    
    public static List ItemId = new ArrayList<>();
    
    public static int characterDamage;
    
    ////////////////////////////////////////////////////////////////////////////The first table gets the Item Ids of the items the character starts with depending on the class, the second table gets the names of these items.
    public List getItemId(String ClassId, Connection conn){  
        String sql = "Select ItemId From ItemsPerClass join ClassInfo where "
                + "ClassInfo.ClassId = ? and ItemsPerClass.ClassId = ClassInfo.ClassId";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
           pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, ClassId);
           rs = pstmt.executeQuery();
           
           while(rs.next()){
               ItemId.add(rs.getString("ItemId"));
           }
           
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getItemId");
            
        } finally {
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
       return ItemId;
    }
    
    public List getItemName(List<String> ItemId, Connection conn) {
        List<String> ItemName = new ArrayList<>();
        String IndItemId;
        String sql = "Select Item_Name From ItemInfo where ItemId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
           for(int i = 0; i < ItemId.size(); i++){
               IndItemId = ItemId.get(i);
               
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, IndItemId);
               rs = pstmt.executeQuery();
               ItemName.add(rs.getString("Item_Name"));
           }
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getItemName");
           
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
        
        return ItemName;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public List getItemType(List<String> ItemId, Connection conn){
        List<String> ItemType = new ArrayList<>();
        String IndItemType;
        String sql = "Select Item_Type From ItemInfo where ItemId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            for(int i = 0; i < ItemId.size(); i++){
                IndItemType = ItemId.get(i);
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, IndItemType);
                rs = pstmt.executeQuery();
                ItemType.add(rs.getString("Item_Type"));
            }
            }catch (SQLException e){
                System.out.println(e.getMessage());
                System.out.println("getItemType");
           
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
        return ItemType;
        }
        
        public int getDamageAmount(String ItemId, Connection conn){
            int DamageAmount = 0;
            
            String sql = "Select Damage_Amount From DamageItems where ItemId = ?";
            
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            try{
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, ItemId);
                rs = pstmt.executeQuery();
                DamageAmount = rs.getInt("Damage_Amount");
            }catch (SQLException e){
                System.out.println(e.getMessage());
                System.out.println("getDamageAmount");
           
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
          return DamageAmount;
        }
        
        public double getHealingAmount(String ItemId, Connection conn){
            double HealingAmount = 0;
            
            String sql = "Select Healing_Amount From HealingItems where ItemId = ?";
            
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            try{
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, ItemId);
                rs = pstmt.executeQuery();
                HealingAmount = rs.getDouble("Healing_Amount");
            }catch (SQLException e){
                System.out.println(e.getMessage());
                System.out.println("getHealingAmount");
           
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
          return HealingAmount;
        }
        
    ////////////////////////////////////////////////////////////////////////////
    public List getItemDescription(List<String> ItemId, Connection conn){
        List<String> ItemDescription = new ArrayList<>();
        String IndItemId;
        String sql = "Select Item_Description From ItemInfo where ItemId = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
           for(int i = 0; i < ItemId.size(); i++){
               IndItemId = ItemId.get(i);
               
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, IndItemId);
               rs = pstmt.executeQuery();
               ItemDescription.add(rs.getString("Item_Description"));
           }
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getItemDescription");
           
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
        
        return ItemDescription;
    }
    
    ////////////////////////////////////////////////////////////////////////////Sets all of the above tables into the characters bag.
    public void setCharacterItems(String ClassId, Connection conn){
        ItemId = this.getItemId(ClassId, conn);
        
        List<String> ItemName = new ArrayList<>();
        ItemName = this.getItemName(ItemId, conn);
        
        List<String> ItemDescription = new ArrayList<>();
        ItemDescription = this.getItemDescription(ItemId, conn);
        
        List<String> ItemType = new ArrayList<>();
        ItemType = this.getItemType(ItemId, conn);
        
        String IndItemId;
        String IndItemName;
        String PocketId = "P";
        String IndDescription;
        String IndType;
        
        String sql = "Insert Into CharacterItems (PocketId, Item_Name, ItemId, "
                + "Item_Type, Item_Description, Item_Damage, Item_Healing) "
                + "Values (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement pstmt = null;
        
        try{
            for(int i = 0; i < ItemId.size(); i++){
                IndItemId = (String) ItemId.get(i);
                IndItemName = ItemName.get(i);
                IndDescription = ItemDescription.get(i);
                IndType = ItemType.get(i);
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, PocketId + i);
                pstmt.setString(2, IndItemName);
                pstmt.setString(3, IndItemId);
                
                if(IndType.equals("Damage")){
                    pstmt.setString(4, IndType);
                    pstmt.setInt(6, this.getDamageAmount(IndItemId, conn));
                    pstmt.setInt(7, 0);
                }else if (IndType.equals("Healing")){
                    pstmt.setString(4, IndType);
                    pstmt.setInt(6, 0);
                    pstmt.setDouble(7, this.getHealingAmount(IndItemId, conn));
                }else{
                    pstmt.setString(4, IndType);
                    pstmt.setInt(6, 0);
                    pstmt.setInt(7,0);
                }
                
                pstmt.setString(5, IndDescription);
                pstmt.execute();
            }
            
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("setCharacterItems");
           
        } finally { //closes all connections so none of the tables lock.
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void showItemDescription(Connection conn, String ClassName){
        Scanner input = new Scanner(System.in);
        
        String ItemDescription;
        
        System.out.println("Which item description would you like to see?");
        this.showItems(conn, ClassName);
        String userInput = input.nextLine();
        
        String sql = "Select Item_Description From CharacterItems Where Item_Name = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userInput);
            rs = pstmt.executeQuery();
            ItemDescription = rs.getString("Item_Description");
            
            System.out.println(ItemDescription);
            System.out.println();
            
        } catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("showItemDescription");
            
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
    }
    
    public void showItems(Connection conn, String ClassName){
        List<String> ItemNames = this.getItems(conn, ClassName);
        
        int count = 0;
        
        for(int i = 0; i < ItemNames.size(); i++){
            if(count < (ItemNames.size() - 1)){
                System.out.print(ItemNames.get(i) + ", ");
            }else{
                System.out.println("and " + ItemNames.get(i));
            }
            count++;
        }
        
    }
    
    public int getColumnLength(Connection conn, String ClassName){
        cLength = 0;
        
        switch(ClassName){
            case "Warrior":
            
                cLength = 5;    
                
            break;
            case "Thief":
               
                cLength = 4;
                
            break;
            case "Wizard":  
                
                cLength = 4;
                
            break;
            case "Battle Mage":              

                cLength = 5;
                
            break;
            case "Assassin":
                
                cLength = 4;
            
            break;
        }
        
        return cLength;
    }
    
    public List getItems(Connection conn, String ClassName){
        List<String> ItemNames = new ArrayList<>();
        
        cLength = this.getColumnLength(conn, ClassName);
        
        String sql = "Select Item_Name From CharacterItems where ItemId = ?";
        
        String IndItemId;
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            for(int i = 0; i < cLength; i++){
                IndItemId = (String) ItemId.get(i);
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, IndItemId);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    ItemNames.add(rs.getString("Item_Name"));
                }
            }
           
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
        
        return ItemNames;
    }
    
    public void useItem(Connection conn, String ClassName){
        Scanner input = new Scanner(System.in);
        
        System.out.println("Which item would you like to use?");
        this.showItems(conn, ClassName);
        String userInput = input.nextLine();
        
        String sql = "Delete from CharacterItems where Item_Name = ?";
        
        PreparedStatement pstmt = null;
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userInput);
            pstmt.executeUpdate();
            cLength = cLength - 1;
        } catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("useItem");
            
        } finally { //closes all connections so none of the tables lock.
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
    }
    
    public void equipItem(Connection conn, String ClassName){
        Scanner input = new Scanner(System.in);
        
        System.out.println("Which item would you like to equip?");
        this.showItems(conn, ClassName);
        String userInput = input.nextLine();
        System.out.println();
        
        String sql = "Update CharacterItems Set Equipped = ? Where Item_Name = ?";
        
        PreparedStatement pstmt = null;
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setString(2, userInput);
            pstmt.executeUpdate();
            
        } catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("equipItem");
            
        } finally { //closes all connections so none of the tables lock.
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public String getRandomItem(Connection conn){
        String sql;
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int rand =(int)(16 * Math.random() + 1);
        
        sql = "Select Item_Name From ItemInfo where ItemId = ?";
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "I" + rand);
            rs = pstmt.executeQuery();
            item = rs.getString("Item_Name");
            
        } catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("getRandomItem");
            
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
        return item;
    }
    
    public String returnItem(){
        return item;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void addItem(Connection conn){
        item = this.getRandomItem(conn);
        this.setItemInfo(conn);
        
        cLength = cLength + 1;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void setItemInfo(Connection conn){
        String IndItemId;
        String PocketId = "P";
        String IndDescription;
        String IndType;
        
        String sql = "Insert Into CharacterItems (PocketId, Item_Name, ItemId, "
                + "Item_Type, Item_Description, Item_Damage, Item_Healing) "
                + "Values (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement pstmt = null;
        
        try{
            //cLength = this.numCharItem(conn);
            IndItemId = this.getIndItemId(conn);
            IndDescription = this.getIndItemDescription(conn);
            IndType = this.getIndItemType(conn);
            
            pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, PocketId + cLength + 1);
                pstmt.setString(2, item);
                pstmt.setString(3, IndItemId);
            
            if(IndType.equals("Damage")){
                    pstmt.setString(4, IndType);
                    pstmt.setInt(6, this.getDamageAmount(IndItemId, conn));
                    pstmt.setInt(7, 0);
                }else if (IndType.equals("Healing")){
                    pstmt.setString(4, IndType);
                    pstmt.setInt(6, 0);
                    pstmt.setDouble(7, this.getHealingAmount(IndItemId, conn));
                }
            
            pstmt.setString(5, IndDescription);
            pstmt.execute();
            
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("setItemInfo");
           
        } finally { //closes all connections so none of the tables lock.
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
        
    }
    
    public String getIndItemId(Connection conn){
        String ItemId = "";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select ItemId From ItemInfo where Item_Name = ?";
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item);
            rs = pstmt.executeQuery();
            ItemId = rs.getString("ItemId");
 
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getIndItemId");
           
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
        
        return ItemId;
    }
    
    public String getIndItemDescription(Connection conn){
        String ItemDescription = "";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select Item_Description From ItemInfo where Item_Name = ?";
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item);
            rs = pstmt.executeQuery();
            ItemDescription = rs.getString("Item_Description");
 
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getIndItemDescription");
           
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
        
        return ItemDescription;
    }
    
    public String getIndItemType(Connection conn){
        String ItemType = "";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select Item_Type From ItemInfo where Item_Name = ?";
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item);
            rs = pstmt.executeQuery();
            ItemType = rs.getString("Item_Type");
 
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getIndItemType");
           
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
        
        return ItemType;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public int numCharItem(Connection conn){
        int length = 0;
        List<String> ItemList= new ArrayList<>();
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "Select PocketId From CharacterItems";
        
        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
            ItemList.add(rs.getString("PocketId"));
            }
            length = ItemList.size();
            
        } catch(SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("numCharItem");
            
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
        
        return length;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public int getItemDamage(Connection conn){
        String sql = "Select Item_Damage From CharacterItems Where Equipped = ? "
                + "And Item_Type = ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setString(2, "Damage");
            rs = pstmt.executeQuery();
            characterDamage = rs.getInt("Item_Damage");
 
        }catch (SQLException e){
           System.out.println(e.getMessage());
           System.out.println("getItemDamage");
           
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
        
        return characterDamage;
    }
}
