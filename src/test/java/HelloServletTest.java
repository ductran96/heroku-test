/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.gradle.demo.HelloServlet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Amir Ingher
 */
public class HelloServletTest {
    
    public HelloServletTest() {
    }
    
    @Test
    public void setUpClass() {
        HelloServlet classUnderTest = new HelloServlet();
        assertNotNull("app should have a greeting", classUnderTest);
    
        
    }
    
    @Test
    public void dummyUnitTest() {
       // assertEquals(0, 1);
    }

    
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
