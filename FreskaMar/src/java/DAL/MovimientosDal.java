package DAL;

import Conexion.Conexion;
import MD.MovimientosMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class MovimientosDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    //PreparedStatement st = null;
    //Statement st = null;
    // ResultSet rs = null;

    public List<MovimientosMd> REGselect(String codigo) throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<MovimientosMd> allMovimientos = new ArrayList<MovimientosMd>();
        String query = "SELECT trim(mov_pro_codigo),"
                + " trim(mov_correlativo),"
                + " trim(mov_precio_unitario,)"
                + " trim(mov_fecha_ingreso,)"
                + " trim(mov_usuario,)"
                + " trim(mov_vendedor,)"
                + " trim(mov_cantidad)"
                + " FROM mov_productos WHERE mov_pro_codigo = '" + codigo + "' ";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            MovimientosMd rg = new MovimientosMd();
            while (rs.next()) {
                rg.setCodigo(rs.getString(1));
                rg.setCorrelativo(rs.getString(2));
                rg.setPrecio(rs.getString(3));
                rg.setIngreso(rs.getString(4));
                rg.setUsuario(rs.getString(5));
                rg.setVendedor(rs.getString(6));
                rg.setCantidad(rs.getString(7));

                allMovimientos.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (REGselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allMovimientos;
    }

    public List<MovimientosMd> RSelect() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<MovimientosMd> allMovimientos = new ArrayList<MovimientosMd>();
        String query = "SELECT"
                + " trim(mov_pro_codigo),"
                + " trim(mov_correlativo),"
                + " trim(mov_precio_unitario),"
                + " trim(DATE_FORMAT(mov_fecha_ingreso,'%d/%m/%Y')),"
                + " trim(mov_usuario),"
                + " trim(mov_vendedor),"
                + " trim(mov_cantidad)"
                + " FROM mov_productos ORDER BY  mov_pro_codigo asc";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            MovimientosMd rg;
            while (rs.next()) {
                rg = new MovimientosMd();
                rg.setCodigo(rs.getString(1));
                rg.setCorrelativo(rs.getString(2));
                rg.setPrecio(rs.getString(3));
                rg.setIngreso(rs.getString(4));
                rg.setUsuario(rs.getString(5));
                rg.setVendedor(rs.getString(6));
                rg.setCantidad(rs.getString(7));

                allMovimientos.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allMovimientos;
    }

    public void REGinsert(String codigo, String correlativo, String precio,
            String ingreso, String usuario, String vendedor,
            String cantidad) throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        String sql = "INSERT INTO almacen.mov_productos"
                + "(mov_pro_codigo,"
                + " mov_correlativo,"
                + " mov_precio_unitario,"
                + " mov_fecha_ingreso,"
                + " mov_usuario,"
                + " mov_vendedor,"
                + " mov_cantidad)"
                + " VALUES(?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?)";
        //  String sql0 = "select max(mov_prod_codigo)+1 as codigo from mov_productos";
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            st = conexion.createStatement();

            /*  rs = st.executeQuery(sql0);
            String codigo = "";
            while (rs.next()) {
                codigo = rs.getString("codigo");
            }*/
            ps = conexion.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.setString(2, correlativo);
            ps.setString(3, precio);
            ps.setString(4, ingreso);
            ps.setString(5, usuario);
            ps.setString(6, vendedor);
            ps.setString(7, cantidad);
            ps.executeUpdate();
            ps.close();

            conexion.commit();
            Clients.showNotification("REGISTRO CREADO <br/> CON EXITO  <br/>");
            conexion.close();
            conexion = null;
            System.out.println("Conexion Cerrada" + conexion);

        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL INSERTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
    }

    public void REGupdate(String codigo, String correlativo, String precio,
            String ingreso, String usuario, String vendedor,
            String cantidad) throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE almacen.mov_productos set "
                    + ",mov_correlativo = '" + correlativo + "'"
                    + ",mov_precio_unitario = '" + precio + "'"
                    + ",mov_fecha_ingreso = STR_TO_DATE('" + ingreso + "','%d/%m/%Y') "
                    + ",mov_usuario = '" + usuario + "'"
                    + ",mov_vendedor = '" + vendedor + "'"
                    + ",mov_cantidad = '" + cantidad + "'"
                    + " WHERE mov_pro_codigo = '" + codigo + "' ");

            Clients.showNotification("REGISTRO ACTUALIZADO <br/> CON EXITO  <br/>");
            System.out.println("Actualizacion Exitosa.! ");

            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {

            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ACTUALIZAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Actualizacion EXCEPTION.: " + mensaje);
            conexion.rollback();
            conexion.close();
        }

    }

    public void REGdelete(String codigo) throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ELIMINAR DATOS..!");
            System.out.println("Eliminar " + codigo);
            st = conexion.createStatement();

            st.executeUpdate("DELETE almacen.mov_productos WHERE mov_pro_codigo = '" + codigo + "' ");
            Clients.showNotification("REGISTRO ELIMINADO <br/> CON EXITO  <br/>");
            System.out.println("Eliminacion Exitosa.! ");
            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {
            conexion.rollback();
            conexion.close();
            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ELIMINAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Eliminacion EXCEPTION.: " + mensaje);
        }

    }

    public void CalculaMov(String codigo, String correla, String cantidad, String precio) throws ClassNotFoundException, SQLException {
        Statement st = null;
        ResultSet rs = null;
        int valor = 0;
        double precios = 0.00;
        valor = Integer.parseInt(cantidad);
        precios = Double.parseDouble(precio);
        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);
            System.out.println("ACTUALIZAR DATOS..!");
            System.out.println("Actualizar " + codigo);
            st = conexion.createStatement();

            st.executeUpdate("UPDATE almacen.productos "
                    + "set pro_stock = pro_stock +" + valor + " ,"
                    + " pro_precio_venta = +" + precios + " "
                    + " WHERE pro_id = '" + codigo + "'  ");

            Clients.showNotification("REGISTRO ACTUALIZADO <br/> CON EXITO  <br/>");
            System.out.println("Actualizacion Exitosa.! ");

            st.close();
            conexion.commit();
            conexion.close();
        } catch (SQLException e) {

            String mensaje = e.getMessage();
            Clients.showNotification("ERROR AL ACTUALIZAR <br/>'" + mensaje + "' <br/> REGISTROS! <br/> ",
                    "warning", null, "middle_center", 0);
            System.out.println("Actualizacion EXCEPTION.: " + mensaje);
            conexion.rollback();
            conexion.close();
        }
    }

    public String Correlativo(String codigo) throws ClassNotFoundException, SQLException {
        Statement st = null;
        ResultSet rs = null;
        String query = "SELECT count(mov_correlativo)+1 AS correlativo FROM almacen.mov_productos WHERE mov_pro_codigo='" + codigo + "' ";
        String resp = "";
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                resp = rs.getString("correlativo");
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR (Rselect) <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return resp;
    }

}
