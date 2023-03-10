package DAL;

import Conexion.Conexion;
import MD.ReporteVentas1Md;
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

public class ReporteVentas1Dal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<ReporteVentas1Md> REGselect(String anio) throws SQLException, ClassNotFoundException {
        List<ReporteVentas1Md> alldata = new ArrayList<ReporteVentas1Md>();

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

        String query = "SELECT a.prefac_id as NUMEROFACT,\n"
                + "              c.pro_descripcion AS DESCRIPCION,\n"
                + "                  b.det_cantidad as CANTIDAD, b.det_precio_venta as PRECIO, \n"
                + "                  (b.det_precio_venta * b.det_cantidad) as TOTAL\n"
                + "                   from prefactura a,\n"
                + "                     detalle_prefactura b, \n"
                + "                     productos c\n"
                + "                WHERE a.prefac_id = b.det_prefac_id \n"
                + "                AND b.det_pro_id = c.pro_id\n"
                + "                AND c.pro_tipo_servicio = 'B'"
                + "                ORDER BY a.prefac_id";

        // +"   a.PREFAC_FECHA_ALTA AS FECHA_VENTA\n" 
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ReporteVentas1Md rg;
            int x = 0;

            while (rs.next()) {
                rg = new ReporteVentas1Md();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setFactura(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setCantidad_ing(rs.getString(3));
                rg.setPrecio_ing(rs.getString(4));
                rg.setTotal(rs.getString(5));

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
