package salondebelleza.accesoadatos;

import java.util.*;
import java.sql.*;
import salondebelleza.entidadesdenegocio.*; // Agregar la referencia al proyecto de entidades de negocio y poder utilizar la entidad servicios

public class ClienteDAL {// Clase para poder realizar consulta de Insertar, modificar, eliminar, obtener datos de la tabla Rol
    // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Rol
    static String obtenerCampos() {
        return "c.Id, c.Nombre, c.Apellido, c.Dui, c.Numero";
    }

    // Metodo para obtener el SELECT a la tabla Rol y el TOP en el caso que se utilice una base de datos SQL SERVER
    private static String obtenerSelect(Cliente pCliente) {
        String sql;
        sql = "SELECT ";
        if (pCliente.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y "getTop_aux" es mayor a cero
            sql += "TOP " + pCliente.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Cliente c"); // Agregar los campos de la tabla de Rol mas el FROM a la tabla Rol con su alias "r"
        return sql;
    }

    // Metodo para obtener Order by a la consulta SELECT de la tabla Rol y ordene los registros de mayor a menor 
    private static String agregarOrderBy(Cliente pCliente) {
        String sql = " ORDER BY c.Id DESC";
        if (pCliente.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Rol en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pCliente.getTop_aux() + " ";
        }
        return sql;
    }
    
    // Metodo para poder insertar un nuevo registro en la tabla de Rol
    public static int crear(Cliente pCliente) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO Cliente(Nombre,Apellido,Dui,Numero) VALUES(?,?,?,?)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pCliente.getNombre()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                ps.setString(2, pCliente.getApellido()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                ps.setString(3, pCliente.getDui()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                ps.setInt(4, pCliente.getNumero()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                result = ps.executeUpdate(); // Ejecutar la consulta INSERT en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }

    // Metodo para poder actualizar un registro en la tabla de Rol
    public static int modificar(Cliente pCliente) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE Cliente SET Nombre=?,Apellido=?,Dui=?,Numero=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pCliente.getNombre()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                ps.setString(2, pCliente.getApellido()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                ps.setString(3, pCliente.getDui()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                ps.setInt(4, pCliente.getNumero()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                ps.setInt(5, pCliente.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #4
                result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }

    // Metodo para poder eliminar un registro en la tabla de Rol
    public static int eliminar(Cliente pCliente) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Cliente WHERE Id=?";  // Definir la consulta DELETE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pCliente.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate();  // Ejecutar la consulta DELETE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el DELETE en la base de datos 
    }    
    
    // Metodo para llenar las propiedades de la entidad de Rol con los datos que viene en el ResultSet,
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int asignarDatosResultSet(Cliente pCliente, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Rol
        pIndex++;
        pCliente.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pCliente.setNombre(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pCliente.setApellido(pResultSet.getString(pIndex)); // index 
        pIndex++;
        pCliente.setDui(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pCliente.setNumero(pResultSet.getInt(pIndex)); // index 2
        return pIndex;
    }
    
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Rol 
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Cliente> pClientes) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Rol
                Cliente cliente = new Cliente(); 
                asignarDatosResultSet(cliente, resultSet, 0); // Llenar las propiedaddes de la Entidad Rol con los datos obtenidos de la fila en el ResultSet
                pClientes.add(cliente); // Agregar la entidad Rol al ArrayList de Rol
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    
   // Metodo para obtener por Id un registro de la tabla de Rol 
    public static Cliente obtenerPorId(Cliente pCliente) throws Exception {
        Cliente cliente = new Cliente();
        ArrayList<Cliente> clientes = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pCliente); // Obtener la consulta SELECT de la tabla Rol
            sql += " WHERE c.Id=?"; // Concatenar a la consulta SELECT de la tabla Rol el WHERE 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pCliente.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, clientes); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (clientes.size() > 0) { // Verificar si el ArrayList de Rol trae mas de un registro en tal caso solo debe de traer uno
            cliente = clientes.get(0); // Si el ArrayList de Rol trae un registro o mas obtenemos solo el primero 
        }
        return cliente; // Devolver el rol encontrado por Id 
    }

    // Metodo para obtener todos los registro de la tabla de Rol
    public static ArrayList<Cliente> obtenerTodos() throws Exception {
        ArrayList<Cliente> clientes;
        clientes = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Cliente());  // Obtener la consulta SELECT de la tabla Rol
            sql += agregarOrderBy(new Cliente());  // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, clientes); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return clientes; // Devolver el ArrayList de Rol
    }
    
    // Metodo para asignar los filtros de la consulta SELECT de la tabla de Rol de forma dinamica
    static void querySelect(Cliente pCliente, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pCliente.getId() > 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Rol
            pUtilQuery.AgregarWhereAnd(" c.Id=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Rol
                statement.setInt(pUtilQuery.getNumWhere(), pCliente.getId()); 
            }
        }
        // Verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Rol
        if (pCliente.getNombre() != null && pCliente.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Nombre LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getNombre() + "%"); 
            }
        }
        
        if (pCliente.getApellido() != null && pCliente.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Apellido LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getApellido() + "%"); 
            }
        }
        
        if (pCliente.getDui() != null && pCliente.getDui().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" c.Dui LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Rol
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getDui() + "%"); 
            }
        }
        
        if (pCliente.getNumero()> 0) { 
            pUtilQuery.AgregarWhereAnd(" c.Numero=? "); // Agregar el campo PrecioServicio al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Servicio
                statement.setInt(pUtilQuery.getNumWhere(), pCliente.getNumero()); 
            }
        }
        
    }

     // Metodo para obtener todos los registro de la tabla de Rol que cumplan con los filtros agregados 
     // a la consulta SELECT de la tabla de Rol 
    public static ArrayList<Cliente> buscar(Cliente pCliente) throws Exception {
        ArrayList<Cliente> clientes = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pCliente); // Obtener la consulta SELECT de la tabla Rol
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pCliente, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Rol 
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pCliente); // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pCliente, utilQuery);  // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Rol
                obtenerDatos(ps, clientes); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return clientes; // Devolver el ArrayList de Rol
    }
    ////////
    
}
