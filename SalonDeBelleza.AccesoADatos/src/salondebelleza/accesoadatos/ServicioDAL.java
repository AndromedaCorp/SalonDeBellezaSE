package salondebelleza.accesoadatos;

import java.util.*;
import java.sql.*;
import salondebelleza.entidadesdenegocio.*; // Agregar la referencia al proyecto de entidades de negocio y poder utilizar la entidad servicios

public class ServicioDAL {// Clase para poder realizar consulta de Insertar, modificar, eliminar, obtener datos de la tabla Servicio
    // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Servicio
    static String obtenerCampos() {
        return "s.Id, s.Nombre, s.Descripcion, s.PrecioServicio";
    }
    
    // Metodo para obtener el SELECT a la tabla Servicio y el TOP en el caso que se utilice una base de datos SQL SERVER
    private static String obtenerSelect(Servicio pServicio) {
        String sql;
        sql = "SELECT ";
        if (pServicio.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y "getTop_aux" es mayor a cero
            sql += "TOP " + pServicio.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Servicio s"); // Agregar los campos de la tabla de Servicio mas el FROM a la tabla Servicio con su alias "r"
        return sql;
    }
    
    // Metodo para obtener Order by a la consulta SELECT de la tabla Servicio y ordene los registros de mayor a menor 
    private static String agregarOrderBy(Servicio pServicio) {
        String sql = " ORDER BY s.Id DESC";
        if (pServicio.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Servicio en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pServicio.getTop_aux() + " ";
        }
        return sql;
    }
    
    // Metodo para poder insertar un nuevo registro en la tabla de Servicio
    public static int crear(Servicio pServicio) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO Servicio(Nombre,Descripcion,PrecioServicio) VALUES(?,?,?)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pServicio.getNombre()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                ps.setString(2, pServicio.getDescripcion()); // Agregar el parametro a la consulta donde estan el simbolo ? #2
                ps.setDouble(3, pServicio.getPrecioServicio()); // Agregar el parametro a la consulta donde estan el simbolo ? #3
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
    
    // Metodo para poder actualizar un registro en la tabla de Servicio
    public static int modificar(Servicio pServicio) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE Servicio SET Nombre=?,Descripcion=?,PrecioServicio=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Servicio utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pServicio.getNombre()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                ps.setString(2, pServicio.getDescripcion()); // Agregar el parametro a la consulta donde estan el simbolo ? #2
                ps.setDouble(3, pServicio.getPrecioServicio()); // Agregar el parametro a la consulta donde estan el simbolo ? #3
                ps.setInt(4, pServicio.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #4  
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
    public static int eliminar(Servicio pServicio) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Servicio WHERE Id=?";  // Definir la consulta DELETE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pServicio.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
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
    
    // Metodo para llenar las propiedades de la entidad de Servicio con los datos que viene en el ResultSet,
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int asignarDatosResultSet(Servicio pServicio, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT s.Id(indice 1),s.Nombre(indice 2),s.Descripcion(indice 3),s.PrecioServicio(indice 4) * FROM Servicio
        pIndex++;
        pServicio.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pServicio.setNombre(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        pServicio.setDescripcion(pResultSet.getString(pIndex)); // index 3
        pIndex++;
        pServicio.setPrecioServicio(pResultSet.getDouble(pIndex)); // index 4
        return pIndex;
    }
    
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Servicio
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Servicio> pServicios) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Servicio
                Servicio servicio = new Servicio(); 
                asignarDatosResultSet(servicio, resultSet, 0); // Llenar las propiedaddes de la Entidad Servicio con los datos obtenidos de la fila en el ResultSet
                pServicios.add(servicio); // Agregar la entidad Servicio al ArrayList de Servicio
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    
    // Metodo para obtener por Id un registro de la tabla de Servicio 
    public static Servicio obtenerPorId(Servicio pServicio) throws Exception {
        Servicio servicio = new Servicio();
        ArrayList<Servicio> servicios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pServicio); // Obtener la consulta SELECT de la tabla Servicio
            sql += " WHERE s.Id=?"; // Concatenar a la consulta SELECT de la tabla Servicio el WHERE 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pServicio.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, servicios); // Llenar el ArrayList de Servicio con las fila que devolvera la consulta SELECT a la tabla de Servicio
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close();  // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (servicios.size() > 0) { // Verificar si el ArrayList de Servicio trae mas de un registro en tal caso solo debe de traer uno
            servicio = servicios.get(0); // Si el ArrayList de Servicio trae un registro o mas obtenemos solo el primero 
        }
        return servicio; // Devolver el Servicio encontrado por Id 
    }
    
    // Metodo para obtener todos los registro de la tabla de Servicio
    public static ArrayList<Servicio> obtenerTodos() throws Exception {
        ArrayList<Servicio> servicios;
        servicios = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Servicio());  // Obtener la consulta SELECT de la tabla Servicio
            sql += agregarOrderBy(new Servicio());  // Concatenar a la consulta SELECT de la tabla Servicio el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, servicios); // Llenar el ArrayList de Servicio con las fila que devolvera la consulta SELECT a la tabla de Servicio
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return servicios; // Devolver el ArrayList de Servicio
    }
    
    // Metodo para asignar los filtros de la consulta SELECT de la tabla de Servicio de forma dinamica
    static void querySelect(Servicio pServicio, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // Obtener el PreparedStatement al cual aplicar los parametros
        if (pServicio.getId() > 0) { // Verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Servicio
            pUtilQuery.AgregarWhereAnd(" s.Id=? "); // Agregar el campo Id al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo Id a la consulta SELECT de la tabla de Servicio
                statement.setInt(pUtilQuery.getNumWhere(), pServicio.getId()); 
            }
        }
        // Verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Servicio
        if (pServicio.getNombre() != null && pServicio.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" s.Nombre LIKE ? "); // Agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Servicio
                statement.setString(pUtilQuery.getNumWhere(), "%" + pServicio.getNombre() + "%"); 
            }
        }
        // Verificar si se va incluir el campo Descripcion en el filtro de la consulta SELECT de la tabla de Servicio
        if (pServicio.getDescripcion()!= null && pServicio.getDescripcion().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" s.Descripcion LIKE ? "); // Agregar el campo Descripcion al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                // Agregar el parametro del campo Descripcion a la consulta SELECT de la tabla de Servicio
                statement.setString(pUtilQuery.getNumWhere(), "%" + pServicio.getDescripcion()+ "%"); 
            }
        }
        // Verificar si se va incluir el campo PrecioServicio en el filtro de la consulta SELECT de la tabla de Servicio
        if (pServicio.getPrecioServicio()> 0) { 
            pUtilQuery.AgregarWhereAnd(" s.PrecioServicio=? "); // Agregar el campo PrecioServicio al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) { 
                // Agregar el parametro del campo PrecioServicio a la consulta SELECT de la tabla de Servicio
                statement.setDouble(pUtilQuery.getNumWhere(), pServicio.getPrecioServicio()); 
            }
        }
    }
    
    // Metodo para obtener todos los registro de la tabla de Servicio que cumplan con los filtros agregados 
     // a la consulta SELECT de la tabla de Servicio
    public static ArrayList<Servicio> buscar(Servicio pServicio) throws Exception {
        ArrayList<Servicio> servicios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pServicio); // Obtener la consulta SELECT de la tabla Servicio
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0); 
            querySelect(pServicio, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Servicio
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pServicio); // Concatenar a la consulta SELECT de la tabla Servicio el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pServicio, utilQuery);  // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Servicio
                obtenerDatos(ps, servicios); // Llenar el ArrayList de Servicio con las fila que devolvera la consulta SELECT a la tabla de Servicio
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return servicios; // Devolver el ArrayList de Servicio
    }
    
}
