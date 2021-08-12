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
import salondebelleza.entidadesdenegocio.Usuario;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import salondebelleza.entidadesdenegocio.Rol;
import java.time.LocalDate;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


/**
 *
 * @author Dev3hc01
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDALIT {

    private static Usuario usuarioActual;
    private static String login;

    public UsuarioDALIT() {
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

     *  Testear el metodo de Crear de la clase UsuarioDAL
     */
    @Test
    public void test1Crear() throws Exception {
        int randomNum = (int) (Math.random() * 1000);
        login = "UNITTEST" + randomNum;
        System.out.println("crear");
        Usuario usuario = new Usuario();
        usuario.setIdrol(1);
         usuario.setDui("DUI");
        usuario.setNombre("Nombre");
        usuario.setApellido("Apellido");
        usuario.setNumero("Numero");
        usuario.setLogin(login);
        usuario.setPassword("12345");
        usuario.setEstado(Usuario.EstadoUsuario.INACTIVO);
        usuario.setFechaRegistro(LocalDate.now());
        Rol rolB = new Rol();
        rolB.setTop_aux(1);
        usuario.setIdrol(RolDAL.buscar(rolB).get(0).getId());
        int expResult = 0;
        int result = UsuarioDAL.crear(usuario);
        assertNotEquals(expResult, result);
    }

    public int testIndividualQuerySelect(Usuario pUsuario) throws Exception {
        ComunDB comundb = new ComunDB();
        ComunDB.UtilQuery pUtilQuery = comundb.new UtilQuery("", null, 0);
        UsuarioDAL.querySelect(pUsuario, pUtilQuery);
        return pUtilQuery.getNumWhere();
    }

     /**
     *  Testear el metodo de QuerySelect de la clase UsuarioDAL
     */
    @Test
    public void test2QuerySelect() throws Exception {
        System.out.println("querySelect");
        int index = 0;
        Usuario usuario = new Usuario();
        usuario.setId(1);
        index++;
          assertTrue(testIndividualQuerySelect(usuario) == index);
        usuario.setDui("TEST");
        index++;
        assertTrue(testIndividualQuerySelect(usuario) == index);
        usuario.setNombre("TEST");
        index++;
        assertTrue(testIndividualQuerySelect(usuario) == index);
        usuario.setApellido("TEST");
        index++;
          assertTrue(testIndividualQuerySelect(usuario) == index);
        usuario.setNumero("TEST");
        index++;
        assertTrue(testIndividualQuerySelect(usuario) == index);
        usuario.setLogin("TEST");
        index++;
        assertTrue(testIndividualQuerySelect(usuario) == index);
        usuario.setIdrol(1);
        index++;
        assertTrue(testIndividualQuerySelect(usuario) == index);
        usuario.setEstado((byte) 1);
        index++;
        assertTrue(testIndividualQuerySelect(usuario) == index);
    }

    /**
     *  Testear el metodo de Buscar de la clase UsuarioDAL
     */
    @Test
    public void test3Buscar() throws Exception {
        System.out.println("buscar");
        
        Usuario usuario = new Usuario();
//        usuario.setIdrol(1);
        usuario.setDui("DUI");
        usuario.setNombre("Nombre");
        usuario.setApellido("Apellido");
        usuario.setNumero("Numero");
        usuario.setLogin(login);
        usuario.setEstado(Usuario.EstadoUsuario.INACTIVO);
        usuario.setTop_aux(10);
        ArrayList<Usuario> result = UsuarioDAL.buscar(usuario);
//        ArrayList<Usuario> result = new ArrayList(UsuarioDAL.crear(usuario));
//        System.out.println(result.get(0));
        assertTrue(result.size()> 0);
        usuarioActual = result.get(0);

    }

     /**
     *  Testear el metodo de ObtenerPorId de la clase UsuarioDAL
     */
    @Test
    public void test4ObtenerPorId() throws Exception {
        System.out.println("obtenerPorId");
        Usuario result = UsuarioDAL.obtenerPorId(usuarioActual);
        assertEquals(usuarioActual.getId(), result.getId());
    }

     /**
     *  Testear el metodo de Modificar de la clase UsuarioDAL
     */
    @Test
    public void test5Modificar() throws Exception {
        System.out.println("modificar");
        Usuario usuario = new Usuario();
        usuario.setId(usuarioActual.getId());
        usuario.setDui("DUI");
        usuario.setNombre("Nombre M");
        usuario.setApellido("ApellidoM");
        usuario.setNumero("NumeroM");
        login+="_MOD";
        usuario.setLogin(login);
        usuario.setEstado(Usuario.EstadoUsuario.ACTIVO);
        Rol rolB = new Rol();
        rolB.setTop_aux(2);
        usuario.setIdrol(RolDAL.buscar(rolB).get(1).getId());
        int expResult = 0;
        int result = UsuarioDAL.modificar(usuario);
        assertNotEquals(expResult, result);
        Usuario usuarioUpdate = UsuarioDAL.obtenerPorId(usuarioActual);
        assertTrue(usuarioUpdate.getLogin().equals(usuario.getLogin()));

    }

     /**
     *  Testear el metodo de ObtenerTodos de la clase UsuarioDAL
     */
    @Test
    public void test6ObtenerTodos() throws Exception {
        System.out.println("obtenerTodos");
        ArrayList<Usuario> result = UsuarioDAL.obtenerTodos();
        assertTrue(result.size() > 0);
    }

     /**
     *  Testear el metodo de BuscarIncluirRol de la clase UsuarioDAL
     */
    @Test
    public void test7BuscarIncluirRol() throws Exception {
        System.out.println("buscarIncluirRol");
        Usuario usuario = new Usuario();
        usuario.setTop_aux(10);
        ArrayList<Usuario> result = UsuarioDAL.buscarIncluirRol(usuario);
        assertTrue(result.size() > 0);
        Usuario usuarioConRol = result.get(0);
        assertTrue(usuarioConRol.getIdrol()== usuarioConRol.getRol().getId());
    }

    /**
     *  Testear el metodo de ObtenerCampos de la clase UsuarioDAL
     */
    @Test
    public void test8ObtenerCampos() {
        System.out.println("obtenerCampos");
        String expResult = "";
        String result = UsuarioDAL.obtenerCampos();
        assertNotEquals(expResult, result);
    }

     /**
     *  Testear el metodo de CambiarPassword de la clase UsuarioDAL
     */
    @Test
    public void test90CambiarPassword() throws Exception {
        System.out.println("cambiarPassword");
        Usuario usuario = new Usuario();
        usuario.setId(usuarioActual.getId());
        usuario.setLogin(login);
        usuario.setPassword("UNODOSTRES");
        String pPasswordAnt = "12345";
        int expResult = 0;
        int result = UsuarioDAL.cambiarPassword(usuario, pPasswordAnt);
        assertNotEquals(expResult, result);
    }

    /**
     *  Testear el metodo de Login de la clase UsuarioDAL
     */
    @Test
    public void test91Login() throws Exception {
        System.out.println("login");
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setPassword("UNODOSTRES");
        Usuario result = UsuarioDAL.login(usuario);
        assertTrue(result.getId() > 0);
        assertEquals(usuario.getLogin(), result.getLogin());
    }
     /**
     *  Testear el metodo de AsignarDatosResultSet de la clase UsuarioDAL
     */
    @Test
    public void test92AsignarDatosResultSet() throws Exception {
        System.out.println("asignarDatosResultSet");
        Usuario usuario = new Usuario();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT " + UsuarioDAL.obtenerCampos() + " FROM Usuario u";
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, usuarioActual.getId());
                try (ResultSet resultSet = ComunDB.obtenerResultSet(ps);) {
                    while (resultSet.next()) {
                        UsuarioDAL.asignarDatosResultSet(usuario, resultSet, 0);
                    }
                    resultSet.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } // Handle any errors that may have occurred.
        catch (SQLException ex) {
            throw ex;
        }
        assertTrue(usuario.getId() == usuarioActual.getId());
    }
     /**
     *  Testear el metodo de Eliminar de la clase UsuarioDAL
     */
    @Test
    public void test93Eliminar() throws Exception {
        System.out.println("eliminar");
        int expResult = 0;
        int result = UsuarioDAL.eliminar(usuarioActual);
        assertNotEquals(expResult, result);
        Usuario usuarioDelete = UsuarioDAL.obtenerPorId(usuarioActual);
        assertTrue(usuarioDelete.getId() == 0);
    }

}
