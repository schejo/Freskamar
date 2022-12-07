package DAL;

import Conexion.Conexion;
import MD.VentaPagoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class VentaPagoDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<VentaPagoMd> REGselect(String anio, String pago) throws SQLException, ClassNotFoundException {
        List<VentaPagoMd> alldata = new ArrayList<VentaPagoMd>();

        System.out.println("que lleva tipo en DAL 1.: " + pago);

        String query
                = "SELECT a.pro_descripcion AS PRODUCTO, \n"
                + "       b.ven_precio AS PRECIO, \n"
                + "       b.ven_cantidad AS CANTIDAD, \n"
                + "       b.ven_descuento AS DESCUENTO, \n"
                + "     ( b.ven_total ) AS GANACIA, \n"
                + "CASE   b.ven_tipo_pago \n"
                + "WHEN 'E' THEN 'EFECTIVO' \n"
                + "WHEN 'T'THEN 'TARJETA' \n"
                + "WHEN 'C' THEN 'CHEQUE' \n"
                + "END   AS TIPO_PAGO \n"
                + "FROM   almacen.productos a, \n"
                + "       almacen.ventas b \n"
                + "WHERE  a.pro_id = b.ven_pro_codigo \n"
                + "AND    b.ven_tipo_pago = '" + pago + "'";

     /*   String query2 
                = "SELECT SUM(b.ven_precio)  as PRECIO, \n"
                + "       SUM(b.ven_descuento) as DESCUENTO, \n"
                + "       SUM( b.ven_total ) as GANACIA, \n"
                + "       SUM(b.ven_cantidad) as CANTIDAD \n"
                + " FROM      almacen.productos a, \n"
                + "   		  almacen.ventas b \n"
                + " WHERE  a.pro_id = b.ven_pro_codigo \n" 
                + " AND    b.ven_tipo_pago = '" + pago + "'";*/

        System.out.println("que lleva tipo en DAL 2.: " + query);

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            VentaPagoMd rg;
            int x = 0;
            while (rs.next()) {
                rg = new VentaPagoMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setNombre(rs.getString(1));
                rg.setPrecio(rs.getString(2));
                rg.setCantidad(rs.getString(3));
                rg.setDescuento(rs.getString(4));
                rg.setGanancia(rs.getString(5));
                rg.setTipo_pago(rs.getString(6));

                alldata.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alldata;
    }

}
