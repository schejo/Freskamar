package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection cnx = null;

    public Connection Conexion() throws SQLException {
        //if (cnx == null) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://157.230.59.79:3306/almacen?user=SERGIO&password=sergio&allowPublicKeyRetrieval=true&useSSL=false");

            System.out.println("Conexion Exitosa a Base de Datos...!");
        } catch (Exception ex) {
            System.out.println("Conexion Fallida...!");
            throw new SQLException(ex);
        }
//////      //}
        return cnx;
    }

    public void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
    }

    public Connection desconectar() throws SQLException {
        cnx.close();
        cnx = null;
        System.out.println("Cerrando Session Esquema control_de_trasporte.: " + cnx);
        return cnx;
    }

//     Connection conn = null;
//
//   
//    public Connection Conexion() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//
//            String dato = "jdbc:mysql://45.55.47.25:3306/Almacen?useSSL=false";
//
//            conn = DriverManager.getConnection(dato, "CALUCO", "SmVkaS0xMTQ5NjA=");
//
//            return conn;
//
//        } catch (ClassNotFoundException e) {
//            return null;
//        } catch (SQLException e) {
//            return conn;
//        }
//    }
//
//    public Connection getConnection() {
//        return Conexion();
//    }
//
//    public void cerrar() throws SQLException {
//        conn.close();
//        conn = null;
//
//    }
}
