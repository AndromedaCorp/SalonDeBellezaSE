package salondebelleza.accesoadatos;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import salondebelleza.entidadesdenegocio.Rol;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author hecto
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RolDALIT {
    
    private static Rol rolActual;
    public RolDALIT() {
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
     * Testear el metodo de Crear de la clase RolDAL
     */
    @Test
    public void test1Crear() throws Exception {
        System.out.println("crear");
        Rol pRol = new Rol(0, "TEST UNIT ROL");
        int expResult = 0;
        int result = RolDAL.crear(pRol);
        assertNotEquals(expResult, result);
    }

    public int testIndividualQuerySelect(Rol pRol) throws Exception {
        ComunDB comundb = new ComunDB();
        ComunDB.UtilQuery pUtilQuery = comundb.new UtilQuery("",null, 0);
        RolDAL.querySelect(pRol, pUtilQuery);
        return pUtilQuery.getNumWhere();
    }
/**
     * Testear el metodo de QuerySelect de la clase RolDAL
     */
    @Test
    public void test2QuerySelect() throws Exception {
        System.out.println("querySelect");
        Rol pRol = new Rol();
        pRol.setId(1);
        assertTrue(testIndividualQuerySelect(pRol) == 1);
        pRol.setNombre("TEST");
        assertTrue(testIndividualQuerySelect(pRol) == 2);
    }

    /**
     * Testear el metodo de Buscar de la clase RolDAL
     */
    @Test
    public void test3Buscar() throws Exception {
        System.out.println("buscar");
        Rol pRol = new Rol(0, "TEST UNIT ROL");
        ArrayList<Rol> result = RolDAL.buscar(pRol);
        assertTrue(result.size() > 0);
        rolActual = result.get(0);
    }
    /**
     * Testear el metodo de ObtenerPorId de la clase RolDAL
     */
    @Test
    public void test4obtenerPorId() throws Exception {
        System.out.println("obtenerPorId");
        Rol result = RolDAL.obtenerPorId(rolActual);
        assertEquals(rolActual.getId(), result.getId());
    }

    /**
     * Testear el metodo de Modificar de la clase RolDAL
     */
    @Test
    public void test5Modificar() throws Exception {
        System.out.println("modificar");
        Rol pRol = new Rol();
        pRol.setId(rolActual.getId());
        System.out.println("Id Rol: " + rolActual.getId());
        pRol.setNombre("TEST UNIT ROL M");
        int expResult = 0;
        int result = RolDAL.modificar(pRol);
        assertNotEquals(expResult, result);
        Rol rolUpdate = RolDAL.obtenerPorId(rolActual);
        assertTrue(rolUpdate.getNombre().equals(pRol.getNombre()));
    }
    
     /**
     * Testear el metodo de ObtenerTodos de la clase RolDAL
     */
    
    @Test
    public void test6ObtenerTodos() throws Exception {
        System.out.println("obtenerTodos");
        ArrayList<Rol> result = RolDAL.obtenerTodos();
        assertTrue(result.size() > 0);
    }

    /**
     * Testear el metodo de Eliminar de la clase RolDAL
     */
    @Test
    public void test7Eliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 0;
        int result = RolDAL.eliminar(rolActual);
        assertNotEquals(expResult, result);
        Rol rolDelete = RolDAL.obtenerPorId(rolActual);
        assertTrue(rolDelete.getId() == 0);
    }
    
}