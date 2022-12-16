package DAL;

import Conexion.Conexion;
import MD.KardexMd;
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

public class KardexDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<KardexMd> REGselect(String anio) throws SQLException, ClassNotFoundException {
        List<KardexMd> alldata = new ArrayList<KardexMd>();

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

        String query = "SELECT a.pro_id,a.pro_descripcion,pro_marca,\n" +
"                a.pro_minimo, a.pro_precio_venta, a.pro_maximo,\n" +
"                CASE a.pro_ubicacion \n" +
"                 when 'S' then 'SALA DE VENTAS'\n" +
"                 when 'B' then 'BODEGA NUEVA '\n" +
"                 when 'C' then 'BODEGA CABLES'\n" +
"                 when 'L' then 'BODEGA LLANTAS'\n" +
"                 when 'A' then 'ALA 1, DERECHA'\n" +
"                when 'I' then 'ALA 2, IZQUIERDA'\n" +
"                when 'D' then 'PASILLO 1, DERECHA'\n" +
"                when 'P' then 'PASILLO 2, IZQUIEDO'\n" +
"                 when 'T' then 'TALLER'\n" +
"                END AS UBICACION,\n" +
"                a.pro_stock,\n" +
"                (a.pro_precio_venta * a.pro_stock) AS TOTAL\n" +
"                FROM productos a;";

        String query2 = "SELECT a.pro_id,a.pro_descripcion,\n"
                + "                a.pro_minimo, SUM( a.pro_precio_venta) as TOTAL1, a.pro_maximo,\n"
                + "                a.pro_ubicacion, SUM(a.pro_stock) as TOTAL2\n"
                + "                FROM productos a ";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            KardexMd rg;
            int x = 0;
            
            while (rs.next()) {
                rg = new KardexMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setMarca(rs.getString(3));
                rg.setCantidad_ing(rs.getString(4));
                rg.setPrecio_ing(rs.getString(5));
                rg.setCantidad_sal(rs.getString(6));
                rg.setPrecio_sal(rs.getString(7));
                rg.setExistencia(rs.getString(8));
                rg.setPrecioXcantidad(rs.getString(9));
            
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
