/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.jmq.inversiones.dbmanager;

import java.sql.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LUIS
 */
public class DBManagerTest {
    
    public DBManagerTest() {
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
     * Test of getInstance method, of class DBManager.
     */
//    @Test
//    public void testGetInstance() {
//        System.out.println("getInstance");
//        DBManager expResult = null;
//        DBManager result = DBManager.getInstance();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of obtenerConexion method, of class DBManager.
     */
    @Test
    public void testObtenerConexion() throws Exception {
        System.out.println("obtenerConexion");
        DBManager instance = DBManager.getInstance();
        Connection result = instance.obtenerConexion();
        if(result != null)
            System.out.println("Conexion existosa");
        else
            System.out.println("No conecta");
    }

    /**
     * Test of cerrarPool method, of class DBManager.
     */
//    @Test
//    public void testCerrarPool() {
//        System.out.println("cerrarPool");
//        DBManager instance = null;
//        instance.cerrarPool();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
