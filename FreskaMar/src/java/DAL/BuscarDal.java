//CALUCO
package DAL;

import Conexion.Conexion;
import MD.BuscarMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class BuscarDal {

    Connection conexion = null;
    Conexion cnn = new Conexion();
    PreparedStatement ps = null;

    public List<BuscarMd> BuscarProd() throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<BuscarMd> allBusca = new ArrayList<BuscarMd>();
        String query = "SELECT "
                + "         pro_id,"
                + "        pro_descripcion, "
                + "        pro_marca, "
                + "        pro_precio_venta, \n"
                + "          CASE pro_ubicacion \n"
                + "                when 'S' then 'SALA DE VENTAS' \n"
                + "                when 'B' then 'BODEGA NUEVA' \n"
                + "                when 'C' then 'BODEGA CABLES' \n"
                + "                when 'L' then 'BODEGA LLANTAS' \n"
                + "                when 'A' then 'ALA 1, DERECHA' \n"
                + "                when 'I' then 'ALA 2, IZQUIERDA' \n"
                + "                when 'D' then 'PASILLO 1, DERECHA' \n"
                + "                when 'P' then 'PASILLO 2, IZQUIEDO'\n"
                + "                when 'T' then 'TALLER' \n"
                + "                END AS UBICACION,\n"
                + "                pro_stock\n"
                + "		 FROM productos";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            BuscarMd rg;
            while (rs.next()) {
                rg = new BuscarMd();
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setMarca(rs.getString(3));
                rg.setPrecio(rs.getString(4));
                rg.setUbicacion(rs.getString(5));
                rg.setSaldo(rs.getString(6));
                allBusca.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allBusca;
    }

}
