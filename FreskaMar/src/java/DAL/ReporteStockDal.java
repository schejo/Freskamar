package DAL;

import Conexion.Conexion;
import MD.ReporteStockMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class ReporteStockDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ReporteStockMd> REGselect(String anio, String fechaF) throws SQLException, ClassNotFoundException {
        List<ReporteStockMd> alldata = new ArrayList<ReporteStockMd>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "";

        try {
            Date date = formatter.parse(anio);
            formatter.applyPattern("yyyy/MM/dd");
            dateInString = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString2 = "";

        try {
            Date date2 = formatter2.parse(fechaF);
            formatter2.applyPattern("yyyy/MM/dd");
            dateInString2 = formatter2.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String query = "SELECT pro_id, pro_descripcion, pro_precio_venta,\n"
                + "         CASE pro_ubicacion\n"
                + "                    when 'S' then 'Sala de Ventas'\n"
                + "                    when 'B'then 'Bodega Nueva'\n"
                + "                    when 'C' then 'Bodega Cables'\n"
                + "                    when 'L' then 'Bodega Llantas'\n"
                + "                    when 'A' then 'Ala Derecha'\n"
                + "                    when 'I' then 'Ala Izquierda'\n"
                + "                    when 'D' then 'Pasillo Derecha'\n"
                + "                    when 'P' then 'Pasillo Izquierdo'\n"
                + "                 END AS UBICACION,\n"
                + "       pro_stock\n"
                + "FROM  productos\n"
                + "WHERE pro_stock < 6\n"
                + "AND    pro_fecha_alta >=  '" + dateInString + "'\n "
                + "AND    pro_fecha_alta <=  '" + dateInString2 + "'";

//        String query2 = " select  SUM( pro_precio_venta) as PRECIO,\n"
//                + "	     SUM(pro_stock) as STOCK\n" 
//                +  "   from productos";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ReporteStockMd rg;
            int x = 0;

            while (rs.next()) {
                rg = new ReporteStockMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setPrecio(rs.getString(3));
                rg.setUbicacion(rs.getString(4));
                rg.setStock(rs.getString(5));

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
