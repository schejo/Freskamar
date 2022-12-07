package DAL;

import Conexion.Conexion;
import MD.KardexTipoMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

public class KardexTipoDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<KardexTipoMd> REGselect(String anio, String tipo) throws SQLException, ClassNotFoundException {
        List<KardexTipoMd> alldata = new ArrayList<KardexTipoMd>();

        System.out.println("que lleva tipo en DAL 1.: " + tipo);

        String query
                = " SELECT a.pro_id,a.pro_descripcion, \n"
                + " a.pro_minimo, a.pro_precio_venta, \n"
                + " a.pro_maximo, \n"
                + " CASE a.pro_tipo \n"
                + " WHEN 'G' then 'GLOBOS' \n"
                + " WHEN 'A'then 'ADORNOS' \n"
                + " WHEN 'P' then 'PIÑATAS' \n"
                + " WHEN 'I' then 'INVITACIONES' \n"
                + " WHEN 'D' then 'DULCES' \n"
                + " WHEN 'J' then 'JUGUETES' \n"
                + " WHEN 'K' then 'PASTELES' \n"
                + " WHEN 'W' then 'SORPRESA' \n"
                + " WHEN 'V' then 'VELAS' \n"
                + " WHEN 'L' then 'ACCESORIOS DE VITRINA' \n"
                + " WHEN 'O' then 'Otros' \n"
                + " END AS TIPO, \n"
                + " a.pro_stock \n"
                + " FROM almacen.productos a\n"
                + " WHERE a.pro_tipo = '" + tipo + "' ";

        System.out.println("que lleva tipo en DAL 2.: " + query);

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            KardexTipoMd rg;
            int x = 0;
            while (rs.next()) {
                rg = new KardexTipoMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setCantidad_ing(rs.getString(3));
                rg.setPrecio_ing(rs.getString(4));
                rg.setCantidad_sal(rs.getString(5));
                rg.setTipo(rs.getString(6));
                rg.setExistencia(rs.getString(7));

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
