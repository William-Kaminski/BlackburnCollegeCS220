/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventuregame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author William Kaminski
 */
public class EnemyTest {
    
    public static Connection conn;
    
    public EnemyTest() {
        AdventureGame game = new AdventureGame();
        conn = game.connect();
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getEnemyName method, of class Enemy.
     */
    @Test
    public void testGetEnemyName() {
        System.out.println("getEnemyName");
        Enemy instance = new Enemy();
        String expResult = "Glorb";
        String result = instance.getEnemyName(conn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEnemyType method, of class Enemy.
     */
    @Test
    public void testGetEnemyType() {
        System.out.println("getEnemyType");
        Enemy instance = new Enemy();
        String expResult = "Blob";
        String result = instance.getEnemyType(conn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEnemyExperience method, of class Enemy.
     */
    @Test
    public void testGetEnemyExperience() {
        System.out.println("getEnemyExperience");
        Enemy instance = new Enemy();
        int expResult = 25;
        int result = instance.getEnemyExperience(conn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEnemyAttack method, of class Enemy.
     */
    @Test
    public void testGetEnemyAttack() {
        System.out.println("getEnemyAttack");
        Enemy instance = new Enemy();
        int expResult = 3;
        int result = instance.getEnemyAttack(conn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEnemyHealth method, of class Enemy.
     */
    @Test
    public void testGetEnemyHealth() {
        System.out.println("getEnemyHealth");
        Enemy instance = new Enemy();
        int expResult = 18;
        int result = instance.getEnemyHealth(conn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of intTryCatch method, of class Enemy.
     */
    @Test
    public void testIntTryCatch() {
        System.out.println("intTryCatch");
        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String columnName = "";
        Enemy instance = new Enemy();
        int expResult = 10;
        int result = instance.intTryCatch(sql, pstmt, rs, columnName, conn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEnemy method, of class Enemy.
     */
    @Test
    public void testSetEnemy() {
        System.out.println("setEnemy");
        String EnemyID = "";
        Enemy instance = new Enemy();
        instance.setEnemy(EnemyID, conn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRandomEnemy method, of class Enemy.
     */
    @Test
    public void testGetRandomEnemy() {
        System.out.println("getRandomEnemy");
        int Map = 1;
        Enemy instance = new Enemy();
        String expResult = "";
        String result = instance.getRandomEnemy(Map, conn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
