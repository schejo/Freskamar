package Utilitarios;

import Conexion.Conexion;
import MD.ProductosMd;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import org.zkoss.zul.Button;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

public class Util {

    public void cargaCombox(String paquete, Combobox co) throws SQLException {
        PreparedStatement smt = null;
        Connection conn;
        Conexion conex = new Conexion();
        conn = conex.Conexion();
        ResultSet rst = null;

        co.getItems().clear();

        try {

            Comboitem item = new Comboitem();
            smt = conn.prepareStatement(paquete);
            rst = smt.executeQuery();

            while (rst.next()) {
                item = new Comboitem();
                item.setLabel(rst.getString(2));
                item.setValue(rst.getString(1));
                item.setParent(co);
            }

            co.setValue(null);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (rst != null) {
                smt.close();
            }
            if (conn != null) {
                conn.close();
                conex.cerrar();
            }
        }
    }

    public void cargaComboxLista(List<ProductosMd> paquete, Combobox co) throws SQLException {

        co.getItems().clear();
        Comboitem item = new Comboitem();

        /*for (int i = 0; i < paquete.size(); i++) {
         //   if (comparar(paquete.get(i).getProductoDescripcion().toUpperCase(), co.getText().toUpperCase())) {
                item = new Comboitem();
                item.setLabel(paquete.get(i).getProductoDescripcion());
                item.setValue(paquete.get(i).getProductoId());
                item.setParent(co);

            }
        }*/

          co.setValue(item.getValue().toString());
    }

    public boolean comparar(String bd, String busqueda) {
        int searchMeLength = bd.length();
        int findMeLength = busqueda.length();
        boolean foundIt = false;
        for (int i = 0; i <= (searchMeLength - findMeLength); i++) {
            if (bd.regionMatches(i, busqueda, 0, findMeLength)) {
                foundIt = true;
                break;
            }
        }
        return foundIt;
    }

}
