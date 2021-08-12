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
import salondebelleza.entidadesdenegocio.Cliente;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteDALIT {
    private static Cliente clienteActual;
    public ClienteDALIT() {
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
        Cliente pCliente = new Cliente(0, "Test Nombre", "Test Apellido", "Test Dui",0);
        int expResult = 0;
        int result = ClienteDAL.crear(pCliente);
        assertNotEquals(expResult, result);
    }
    
    public int testIndividualQuerySelect(Cliente pCliente) throws Exception {
        ComunDB comundb = new ComunDB();
        ComunDB.UtilQuery pUtilQuery = comundb.new UtilQuery("",null, 0);
        ClienteDAL.querySelect(pCliente, pUtilQuery);
        return pUtilQuery.getNumWhere();
    }
    
    /**
     * Test of querySelect method, of class ServicioDAL.
     */
    @Test
    public void test2QuerySelect() throws Exception {
        System.out.println("querySelect");
        Cliente pCliente = new Cliente();
        pCliente.setId(1);
        assertTrue(testIndividualQuerySelect(pCliente) == 1);
        pCliente.setNombre("TEST");
        assertTrue(testIndividualQuerySelect(pCliente) == 2);
        pCliente.setApellido("TEST");
        assertTrue(testIndividualQuerySelect(pCliente) == 3);
         pCliente.setDui("TEST");
        assertTrue(testIndividualQuerySelect(pCliente) == 4);
        pCliente.setNumero(1);
        assertTrue(testIndividualQuerySelect(pCliente) == 5);
    }
    
    /**
     * Test of buscar method, of class ServicioDAL.
     */
    @Test
    public void test3Buscar() throws Exception {
        System.out.println("buscar");
        Cliente pCliente = new Cliente(0, "Test Nombre", "Test Apellido", "Test Dui",0);
        ArrayList<Cliente> result = ClienteDAL.buscar(pCliente);
        assertTrue(result.size()>0);
        clienteActual = result.get(0);
    }
    
    /**
     * Test of obtenerPorId method, of class ServicioDAL.
     */
    @Test
    public void test4ObtenerPorId() throws Exception {
        System.out.println("obtenerPorId");
        Cliente result = ClienteDAL.obtenerPorId(clienteActual);
        assertEquals(clienteActual.getId(), result.getId());
    }

    /**
     * Test of modificar method, of class ServicioDAL.
     */ 
    @Test
    public void test5Modificar() throws Exception {
        System.out.println("modificar");
        Cliente pCliente = new Cliente();       
        pCliente.setId(clienteActual.getId());
        pCliente.setNombre("Test Nombre");
        pCliente.setApellido("Test Apellido");
        pCliente.setDui("Test Dui");
        pCliente.setNumero(1);
        int expResult = 0;
        int result = ClienteDAL.modificar(pCliente);
        assertNotEquals(expResult, result);
        Cliente clienteUpdate = ClienteDAL.obtenerPorId(clienteActual);
        assertTrue(clienteUpdate.getNombre().equals(pCliente.getNombre()));
        assertTrue(clienteUpdate.getApellido().equals(pCliente.getApellido()));
        assertTrue(clienteUpdate.getDui().equals(pCliente.getDui()));
        assertTrue(clienteUpdate.getNumero()== (pCliente.getNumero()));
    }
    
    /**
     * Test of obtenerTodos method, of class ServicioDAL.
     */
    @Test
    public void test6ObtenerTodos() throws Exception {
        System.out.println("obtenerTodos");
        ArrayList<Cliente> result = ClienteDAL.obtenerTodos();
        assertTrue(result.size()>0);
    }

    /**
     * Test of eliminar method, of class ServicioDAL.
     */
    @Test
    public void test7Eliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 0;
        int result = ClienteDAL.eliminar(clienteActual);
        assertNotEquals(expResult, result);
        Cliente clienteDelete = ClienteDAL.obtenerPorId(clienteActual);
        assertTrue(clienteDelete.getId()==0);
    }
    ////////////////////////////////////////////////////////////////////
    
}
