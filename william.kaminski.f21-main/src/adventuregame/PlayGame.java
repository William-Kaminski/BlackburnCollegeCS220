package adventuregame;

import java.util.Scanner;

/**
 *
 * @author William Kaminski
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PlayGame {
    
    public static String userInput;
    
    public int count;
    public int characterHealth = 30;
    public static int EnemyHealth;
    
    public static int currentExperience;
    
    public boolean attacking = true;
    
    public static int[] characterLocation;
    public int[] cLocation;
    
    public static String[][] Area1;
    public static String[][] Area2;
    public static String[][] Area3;
    
    ////////////////////////////////////////////////////////////////////////////
    public void StartGame(Connection conn, String ClassName){
        Area area = new Area();
        Character character = new Character();
        
        Area1 = area.Area1(conn);
        Area2 = area.Area2(conn);
        Area3 = area.Area3(conn);
        
        boolean playGame = true;
        boolean playing = true;
        int specificArea = 1;
        String SpecificAreaMove;
      
        Scanner in = new Scanner(System.in);
        String input;
      
        while(playGame == true){
            if(characterHealth > 0){
                switch(specificArea){
                    case 1:
                        System.out.println("You wake up in a strange cavern and all "
                            + "you can do, is to move straight forward.");
                        System.out.println("What would you like to do? (You are able"
                            + " to move {'Move'}, look at your items {'Bag'}, and "
                            + "look at the map of the area {'Map'})");
                        System.out.println("*Don't forget to equip your items*");
                  
                        input = in.nextLine();
                        System.out.println();
                        this.checkInput(input, specificArea, conn, ClassName);
                    
                        characterLocation = area.getCharacterLocation(Area1, count);
                    
                        while(playing == true){
                            SpecificAreaMove = area.getIndividualLocationName(Area1, characterLocation);
                            //System.out.println(SpecificAreaMove);
                            if(SpecificAreaMove.equals("Finish")){
                                System.out.println("You have reached the first "
                                        + "exit of the cavern, your character "
                                        + "will now take a short rest before "
                                        + "going on to the second area.");
                            
                                character.updatePlayerHealth(conn);
                                playing = false;
                            
                            } else {
                                System.out.println("What would you like to do now? "
                                    + "{'Move', 'Bag', 'Map'}");
                                input = in.nextLine();
                                System.out.println();
                                this.checkInput(input, specificArea, conn, ClassName);
                                playing = true;
                            }
                            
                        }
                        
                        specificArea = 2;
                    break;
              
                    case 2:
                        count = 0;
                        playing = true;
                        specificArea = 2;
                        
                        System.out.println();
                        System.out.println("As you exit the first chamber of the"
                                + " cavern, you see that there is a larger "
                                + "cavern up ahead.");
                        System.out.println("What would you like to do now? "
                                    + "{'Move', 'Bag', 'Map'}");
                        //System.out.println("*Don't forget to equip your items*");
                  
                        input = in.nextLine();
                        System.out.println();
                        this.checkInput(input, specificArea, conn, ClassName);
                    
                        characterLocation = area.getCharacterLocation(Area2, count);
                    
                        while(playing == true){
                            SpecificAreaMove = area.getIndividualLocationName(Area2, characterLocation);
                            //System.out.println(SpecificAreaMove);
                            if(SpecificAreaMove.equals("Finish")){
                                System.out.println("You have reached the second "
                                        + "exit of the cavern, your character "
                                        + "will now take a short rest before "
                                        + "going on to the second area.");
                            
                                character.updatePlayerHealth(conn);
                                playing = false;
                            
                            } else {
                                System.out.println("What would you like to do now? "
                                    + "{'Move', 'Bag', 'Map'}");
                                input = in.nextLine();
                                System.out.println();
                                this.checkInput(input, specificArea, conn, ClassName);
                                playing = true;
                            }
                        }
                    
                        specificArea = 3;
                    break;
              
                    case 3:
                        count = 0;
                        playing = true;
                        specificArea = 3;
                        
                        System.out.println("As you exit the second chamber of the"
                                + " cavern, you see that there is even a larger "
                                + "cavern up ahead.");
                        System.out.println("What would you like to do now? "
                                    + "{'Move', 'Bag', 'Map'}");
                        //System.out.println("*Don't forget to equip your items*");
                  
                        input = in.nextLine();
                        System.out.println();
                        this.checkInput(input, specificArea, conn, ClassName);
                    
                        characterLocation = area.getCharacterLocation(Area2, count);
                    
                        while(playing == true){
                            SpecificAreaMove = area.getIndividualLocationName(Area2, characterLocation);
                            //System.out.println(SpecificAreaMove);
                            if(SpecificAreaMove.equals("Finish")){
                                System.out.println("You have reached the end of "
                                        + "the cavern, thanks for playing!");
                            
                                character.updatePlayerHealth(conn);
                                playing = false;
                            
                            } else {
                                System.out.println("What would you like to do now? "
                                    + "{'Move', 'Bag', 'Map'}");
                                input = in.nextLine();
                                System.out.println();
                                this.checkInput(input, specificArea, conn, ClassName);
                                playing = true;
                            }
                        }
                        playGame = false;
                    break;    
                }
            }else if(characterHealth == 0){
                playGame = false;
            }
        }
    }
    
    public void specificCase(String specificArea, int Map, Connection conn, int count){
        Item item = new Item();
        Enemy enemy = new Enemy();
        
        String newItem;
        
        switch(specificArea){
            case("Item"):
                                    
                item.addItem(conn);
                newItem = item.returnItem();
                System.out.println("You got a " + newItem + "!");
                System.out.println("Check your bag for more "
                    + "information about this item!");
                System.out.println();
                
            break;
                                
            case("Enemy"):
                String EnemyId;
                
                EnemyId = enemy.getRandomEnemy(Map, conn);
                enemy.setEnemy(EnemyId, conn);
                
                this.attacking(conn, count);
                                    
            break;
        }
    }
    
    public boolean moving(boolean checkedArea, String specificArea, String[][] Area, int Map, Connection conn){
        Area area = new Area();
        
        boolean move = false;
        
        if(checkedArea == true){
            switch(userInput){
                case "North":
                            
                    specificArea = area.checkSpecificLocation(userInput, characterLocation, Map, Area, conn);
                    cLocation = area.changeCharacterLocation(userInput, Area, count);
                            
                    this.specificCase(specificArea, Map, conn, count);
                        
                    count++;
                    move = false;
                break;
            
                case "East":
                                    
                    specificArea = area.checkSpecificLocation(userInput, characterLocation, Map, Area, conn);
                    cLocation = area.changeCharacterLocation(userInput, Area, count);
                                    
                    this.specificCase(specificArea, Map, conn, count);  
                    
                    count++;
                    move = false;
                break;
            
                case "South":
                    specificArea = area.checkSpecificLocation(userInput, characterLocation, Map, Area, conn);
                    cLocation = area.changeCharacterLocation(userInput, Area, count);
                    
                    this.specificCase(specificArea, Map, conn, count);
                                    
                    count++;
                    move = false;
                break;
            
                case "West":
                    specificArea = area.checkSpecificLocation(userInput, characterLocation, Map, Area, conn);
                    cLocation = area.changeCharacterLocation(userInput, Area, count);
                    
                    this.specificCase(specificArea, Map, conn, count);
                                    
                    count++;
                    move = false;
                break;
                            } 
        }else if (checkedArea == false){
            System.out.println("You can not move that way, please chose "
                + "a different direction.");
                            
            cLocation = characterLocation;
        }
        return move;
    }

    public void movement(int Map, Connection conn){
        Area area = new Area();
        Scanner in = new Scanner(System.in);
        
        boolean checkedArea;
        boolean move = true;
        String specificArea = "";
        
        switch(Map) {
            case 1:
                
                while (move == true){
                    if(characterHealth > 0){
                        if(count >= 1){
                        
                            System.out.println("Which direction would you like to move? "
                                + "{'North', 'South', 'East', 'West'}");
                            userInput = in.nextLine();
                            System.out.println();
                        
                            characterLocation = area.getCharacterLocation(Area1, count);
                            checkedArea = area.specificLocation(Area1, userInput, characterLocation, Map, conn);
        
                            move = this.moving(checkedArea, specificArea,Area1, Map, conn);
                        
                        }else if(count == 0) {
                        
                            System.out.println("You have chosen to move, which direction would "
                                + "you like to move? {'North', 'South', 'East', 'West'}");
                            userInput = in.nextLine();
                            System.out.println();
        
                            characterLocation = area.getCharacterLocation(Area1, count);
                            checkedArea = area.specificLocation(Area1, userInput, characterLocation, Map, conn);
                        
                            move = this.moving(checkedArea, specificArea,Area1, Map, conn);
                        
                        }
                    }else if(characterHealth == 0){
                        move = false;
                    }
                }       
                
            break;
            
            case 2:
                
                while (move == true){
                    if(characterHealth > 0){
                        if(count >= 1){
                        
                            System.out.println("Which direction would you like to move? "
                                + "{'North', 'South', 'East', 'West'}");
                            userInput = in.nextLine();
                            System.out.println();
                        
                            characterLocation = area.getCharacterLocation(Area2, count);
                            checkedArea = area.specificLocation(Area2, userInput, characterLocation, Map, conn);
        
                            move = this.moving(checkedArea, specificArea,Area2, Map, conn);
                        
                        }else if(count == 0) {
                        
                            System.out.println("You have chosen to move, which direction would "
                                + "you like to move? {'North', 'South', 'East', 'West'}");
                            userInput = in.nextLine();
                            System.out.println();
        
                            characterLocation = area.getCharacterLocation(Area2, count);
                            checkedArea = area.specificLocation(Area2, userInput, characterLocation, Map, conn);
                        
                            move = this.moving(checkedArea, specificArea,Area2, Map, conn);
                        
                        }
                    }else if(characterHealth == 0){
                        move = false;
                    }
                }
                
            break;
            
            case 3:
                
                while (move == true){
                    if(characterHealth > 0){
                        if(count >= 1){
                        
                            System.out.println("Which direction would you like to move? "
                                + "{'North', 'South', 'East', 'West'}");
                            userInput = in.nextLine();
                            System.out.println();
                        
                            characterLocation = area.getCharacterLocation(Area3, count);
                            checkedArea = area.specificLocation(Area3, userInput, characterLocation, Map, conn);
        
                            move = this.moving(checkedArea, specificArea,Area3, Map, conn);
                        
                        }else if(count == 0) {
                        
                            System.out.println("You have chosen to move, which direction would "
                                + "you like to move? {'North', 'South', 'East', 'West'}");
                            userInput = in.nextLine();
                            System.out.println();
        
                            characterLocation = area.getCharacterLocation(Area3, count);
                            checkedArea = area.specificLocation(Area3, userInput, characterLocation, Map, conn);
                        
                            move = this.moving(checkedArea, specificArea,Area3, Map, conn);
                        
                        }
                    }else if(characterHealth == 0){
                        move = false;
                    }
                }
                
            break;
        }
        
    }
  
    public void checkInput(String uInput, int specificArea, Connection conn, String ClassName){
        Item item = new Item();
        Area area = new Area();
        Scanner in = new Scanner(System.in);
        
        String input;
        
        switch(uInput){
            case "Move":
                
                this.movement(specificArea, conn);
                
            break;
          
            case "Bag":
                System.out.println("What would you like to do to the items in "
                        + "your bag? {'Use', 'Equip', or 'More Info'}");
                input = in.nextLine();
                System.out.println();
                
                switch (input) {
                    case "Use":
                    
                        item.useItem(conn, ClassName);
                        System.out.println("You have used the item!");
                    
                    break;
                    
                    case "Equip":
                    
                        item.equipItem(conn, ClassName);
                        System.out.println("You have equipped the item!");
                        System.out.println();
                    
                    break;
                    
                    case "More Info":
                    
                    item.showItemDescription(conn, ClassName);
                    
                    break;
                }
            break;

            case "Map":
                switch(specificArea){
                    case 1:
                        
                        area.printAreaArray(Area1, specificArea, conn);
                        System.out.println();
                        
                    break;
                    
                    case 2:
                        
                        area.printAreaArray(Area2, specificArea, conn);
                        System.out.println();
                        
                    break;
                    
                    case 3:
                        
                        area.printAreaArray(Area3, specificArea, conn);
                        System.out.println();
                        
                    break;
                }
                
            break;    
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void attacking(Connection conn, int count){
        Enemy enemy = new Enemy();
        Item item = new Item();
        Character character = new Character();
        
        Scanner input = new Scanner(System.in);
        
        EnemyHealth = enemy.getEnemyHealth(conn);
        //System.out.println(EnemyHealth);
        characterHealth = character.getHealth(conn);
        
        String enemyName = enemy.getEnemyName(conn);
        String enemyType = enemy.getEnemyType(conn);
        
        int EHealthInfo = enemy.getEnemyHealth(conn);
        
        int CharacterDamage = item.getItemDamage(conn);
        int EnemyDamage = enemy.getEnemyAttack(conn);
        //System.out.println(CharacterDamage);
        //System.out.println(EnemyDamage);
        
        attacking = true;
        
        int attackCount = 0;
        
        while(attacking == true){
            if(EnemyHealth > 0 && characterHealth > 0){
                if(attackCount == 0){
                    System.out.println("A wild " + enemyName + " appeared!");
                    System.out.println("What would you like to do? "
                        + "(You can either fight {'Fight'}, or check more info "
                        + "about it {'More Info'})");
                    String userAttackInput = input.nextLine();
                    System.out.println();
                    
                    this.attack(userAttackInput, CharacterDamage, enemyName, enemyType, EHealthInfo);
                    
                    attackCount++;
                } else if(attackCount > 0) {
                    System.out.println("You are still fighting " + enemyName + ".");
                    System.out.println("What would you like to do? {'Fight' or 'More Info'}");
                    String userAttackInput = input.nextLine();
                    System.out.println("");
                    
                    this.attack(userAttackInput, CharacterDamage, enemyName, enemyType, EHealthInfo);
                    
                    attackCount++;
                }
                
                System.out.println("The enemy hits you for " 
                        + EnemyDamage + " points.");
                characterHealth = characterHealth - EnemyDamage;
                System.out.println();
                attacking = true;
                
            }else if(EnemyHealth <= 0){
                System.out.println("You have defeated the enemy!");
                System.out.println();
                this.updateExperience(conn, enemyName);
                this.checkLevelUp(conn);
                attacking = false;
                
            }else if(characterHealth <= 0){
                System.out.println("Your character has died.");
                
                attacking = false;
            }
        }
    }
    
    public void attack(String userAttackInput, int CharacterDamage, String enemyName, String enemyType, int EHealthInfo){
        switch(userAttackInput){
            case "Fight":
                System.out.println("You hit the enemy for " + 
                    CharacterDamage + " points.");
                EnemyHealth = EnemyHealth - CharacterDamage;
                        
            break;
                    
            case "More Info":
                        
                System.out.println(enemyName + " - " + enemyType + 
                    " type, " + EHealthInfo + " health points");
                        
            break;
        }
    }
    
    public void updateExperience(Connection conn, String enemyName){
        Enemy enemy = new Enemy();
        Character character = new Character();
        
        int enemyExperience = enemy.getEnemyExperience(conn);
        
        character.getCurrentExperience(conn);
        currentExperience = character.characterExperience;

        String sql = "Update CharacterInfo Set Experience = ?";
        
        if(count == 0){
            currentExperience = enemyExperience;
        }else if(count > 0){
            currentExperience = currentExperience + enemyExperience;
        }
        
        PreparedStatement pstmt = null;
       
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, currentExperience);
            pstmt.execute();
           
        } catch (SQLException e){
            
            System.out.println(e.getMessage());
            System.out.println("updateExperience");
           
        }finally {
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
    }
    
    public boolean checkLevelUp(Connection conn){
        boolean check = false;
        
        if(currentExperience > 50 && currentExperience < 100){
            this.levelUp(2, conn);
        }else if(currentExperience > 100 && currentExperience < 150){
            this.levelUp(3, conn);
        }else if(currentExperience > 150 && currentExperience < 200){
            this.levelUp(4, conn);
        }else if(currentExperience > 200 && currentExperience < 250){
            this.levelUp(5, conn);
        }else if(currentExperience > 250 && currentExperience < 300){
            this.levelUp(6, conn);
        }else if(currentExperience > 300 && currentExperience < 350){
            this.levelUp(7, conn);
        }else if(currentExperience > 350 && currentExperience < 400){
            this.levelUp(8, conn);
        }else if(currentExperience > 400 && currentExperience < 450){
            this.levelUp(9, conn);
        }else if(currentExperience > 450){
            this.levelUp(10, conn);
        }
        
        return check;
    }
    
    public void levelUp(int level, Connection conn){
        
        switch(level){
            case 2:
                
                this.leveling(conn, 2);
                
            break;
            
            case 3:
                
                this.leveling(conn, 3);
                
            break;
            
            case 4:
                
                this.leveling(conn, 4);
                
            break;
            
            case 5:
                
                this.leveling(conn, 5);
                
            break;
            
            case 6:
                
                this.leveling(conn, 6);
                
            break;
            
            case 7:
                
                this.leveling(conn, 7);
                
            break;
            
            case 8:
                
                this.leveling(conn, 8);
                
            break;
            
            case 9:
                
                this.leveling(conn, 9);
                
            break;
            
            case 10:
                
                this.leveling(conn, 10);
                
            break;
        }
        
    }
    
    public void leveling(Connection conn, int level){
        String sql = "Update CharacterInfo Set Character_Level = ?";
        
        PreparedStatement pstmt = null;
       
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, level);
            pstmt.execute();
           
        } catch (SQLException e){
            
            System.out.println(e.getMessage());
            System.out.println("leveling");
           
        }finally {
            if (pstmt != null) {
                try {
                pstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
        
        }
    }
    
}
