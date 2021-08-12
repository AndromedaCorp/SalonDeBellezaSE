/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salondebelleza.accesoadatos;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import salondebelleza.entidadesdenegocio.Servicio;


import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServicioDALIT {
    private static Servicio servicioActual;
    public ServicioDALIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of crear method, of class ServicioDAL.
     */
    @Test
    public void test1Crear() throws Exception {
        System.out.println("crear");
        Servicio pServicio = new Servicio(0, "TEST UNIT SERVICIO", "TEST UNIT SERVICIO",0);
        int expResult = 0;
        int result = ServicioDAL.crear(pServicio);
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    public int testIndividualQuerySelect(Servicio pServicio) throws Exception {
        ComunDB comundb = new ComunDB();
        ComunDB.UtilQuery pUtilQuery = comundb.new UtilQuery("",null, 0);
        ServicioDAL.querySelect(pServicio, pUtilQuery);
        return pUtilQuery.getNumWhere();
    }
    
    /**
     * Test of querySelect method, of class ServicioDAL.
     */
    @Test
    public void test2QuerySelect() throws Exception {
        System.out.println("querySelect");
        Servicio pServicio = new Servicio();
        pServicio.setId(1);
        assertTrue(testIndividualQuerySelect(pServicio) == 1);
        pServicio.setNombre("TEST");
        assertTrue(testIndividualQuerySelect(pServicio) == 2);
        pServicio.setDescripcion("TEST");
        assertTrue(testIndividualQuerySelect(pServicio) == 3);
        pServicio.setPrecioServicio(1);
        assertTrue(testIndividualQuerySelect(pServicio) == 4);
    }
    
    /**
     * Test of buscar method, of class ServicioDAL.
     */
    @Test
    public void test3Buscar() throws Exception {
        System.out.println("buscar");
        Servicio pServicio = new Servicio(0, "TEST UNIT SERVICIO", "TEST UNIT SERVICIO",0);
        ArrayList<Servicio> result = ServicioDAL.buscar(pServicio);
        assertTrue(result.size()>0);
        servicioActual = result.get(0);
    }
    
    /**
     * Test of obtenerPorId method, of class ServicioDAL.
     */
    @Test
    public void test4ObtenerPorId() throws Exception {
        System.out.println("obtenerPorId");
        Servicio result = ServicioDAL.obtenerPorId(servicioActual);
        assertEquals(servicioActual.getId(), result.getId());
    }

    /**
     * Test of modificar method, of class ServicioDAL.
     */ 
    @Test
    public void test5Modificar() throws Exception {
        System.out.println("modificar");
        Servicio pServicio = new Servicio();       
        pServicio.setId(servicioActual.getId());
        pServicio.setNombre("TEST UNIT SERVICIO M");
        pServicio.setDescripcion("TEST UNIT SERVICIO M");
        pServicio.setPrecioServicio(1);
        int expResult = 0;
        int result = ServicioDAL.modificar(pServicio);
        assertNotEquals(expResult, result);
        Servicio servicioUpdate = ServicioDAL.obtenerPorId(servicioActual);
        assertTrue(servicioUpdate.getNombre().equals(pServicio.getNombre()));
        assertTrue(servicioUpdate.getDescripcion().equals(pServicio.getDescripcion()));
        assertTrue(servicioUpdate.getPrecioServicio() == (pServicio.getPrecioServicio()));
    }
    
    /**
     * Test of obtenerTodos method, of class ServicioDAL.
     */
    @Test
    public void test6ObtenerTodos() throws Exception {
        System.out.println("obtenerTodos");
        ArrayList<Servicio> result = ServicioDAL.obtenerTodos();
        assertTrue(result.size()>0);
    }

    /**
     * Test of eliminar method, of class ServicioDAL.
     */
    @Test
    public void test7Eliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 0;
        int result = ServicioDAL.eliminar(servicioActual);
        assertNotEquals(expResult, result);
        Servicio servicioDelete = ServicioDAL.obtenerPorId(servicioActual);
        assertTrue(servicioDelete.getId()==0);
    }

}
