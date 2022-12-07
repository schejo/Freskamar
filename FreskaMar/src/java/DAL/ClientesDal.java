package DAL;

import Conexion.Conexion;
import MD.ClientesMd;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import org.zkoss.zk.ui.util.Clients;

public class ClientesDal {

    Connection conexion;
    Conexion cnn = new Conexion();
    ClientesMd cl = new ClientesMd();
    Statement st = null;
    ResultSet rs = null;

    public ClientesMd BuscarClientes(String nit) throws SQLException {
        PreparedStatement smt = null;

        conexion = cnn.Conexion();

        ResultSet result = null;

        ClientesMd Buscar = null;

        String sql = "SELECT CL_ID, "
                + "          CL_NOMBRE, "
                + "          CL_NIT, "
                + "          CL_DIRECCION, "
                + "          CL_TELEFONO, "
                + "          DATE_FORMAT(CL_FECHA_ALTA, '%d/%m/%Y') "
                + "   FROM cliente U"
                + "   WHERE TRIM(CL_NIT) = ?";

        try {
            smt = conexion.prepareStatement(sql);
            smt.setString(1, nit.replace("-", ""));

            result = smt.executeQuery();

            while (result.next()) {
                Buscar = new ClientesMd();

                Buscar.setCodigoCliente(result.getString(1));
                Buscar.setNombreComercial(result.getString(2));
                Buscar.setNit(result.getString(3));
                Buscar.setDireccion(result.getString(4));
                Buscar.setTelefono(result.getString(5));
                Buscar.setFechaAlta(result.getString(6));

            }
        } catch (Exception e) {
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (result != null) {
                smt.close();
            }
            if (conexion != null) {
                cnn.cerrar();
                conexion.close();
                conexion = null;
            }
        }
        return Buscar;
    }
    
        public ClientesMd saveCLModal(ClientesMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ClientesMd();
        String query1 = " SELECT max(CL_ID)+1 as id FROM cliente; ";
        String sql = " INSERT INTO cliente (CL_ID, CL_NOMBRE, CL_NIT, CL_DIRECCION, CL_TELEFONO, CL_FECHA_ALTA, CL_USUARIO_ALTA, CL_CORREO)\n"
                + "VALUES (?,?,?,?,?,NOW(),?,?);";

        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);

            st = conexion.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                id = rs.getString("id");
            }
            st.close();
            rs.close();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, data.getNombreComercial());
            ps.setString(3, data.getNit());
            ps.setString(4, data.getDireccion());
            ps.setString(5, data.getTelefono());
            ps.setString(6, data.getUsuario());
            ps.setString(7, data.getCorreCleinte());
           

            ps.executeUpdate();
            ps.close();
            conexion.commit();
            cl.setResp("1");
            cl.setMsg("REGISTRO GUARDADO CORRECTAMENTE");

            conexion.close();
            cnn.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());
        }

        return cl;
    }

    public List<ClientesMd> REGselect() throws SQLException, ClassNotFoundException {
        List<ClientesMd> allReporteCli = new ArrayList<ClientesMd>();

        String query = "SELECT * FROM cliente;";

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            ClientesMd rg;

            while (rs.next()) {
                rg = new ClientesMd();

                rg.setCodigoCliente(rs.getString(1));
                rg.setNombreComercial(rs.getString(2));
                rg.setNit(rs.getString(3));
                rg.setDireccion(rs.getString(4));
                rg.setTelefono(rs.getString(5));
                rg.setFechaAlta(rs.getString(6));
                rg.setUsuarioAlta(rs.getString(7));
               
                rg.setCorreCleinte(rs.getString(10));

                allReporteCli.add(rg);
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
        return allReporteCli;
    }
    
       //obtener fecha de la base de datos
    public ClientesMd obtenerFecha() {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ClientesMd();
        String query0 = " SELECT trim(DATE_FORMAT(NOW(),'%d-%m-%Y'));";
        try {
            conexion = cnn.Conexion();

            st = conexion.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                cl.setObtenerFecha(rs.getString(1));

                cl.setResp("1");
                cl.setMsg("fecha obtenida!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("error con la base <br/>  <br/>  de datos");

            }
            conexion.close();
            cnn.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public ClientesMd saveCL(ClientesMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new ClientesMd();
        String query1 = " SELECT MAX(CL_ID)+1 AS id FROM cliente; ";
        String sql = " INSERT INTO cliente (CL_ID, CL_NOMBRE, CL_NIT, CL_DIRECCION, CL_TELEFONO, CL_FECHA_ALTA, CL_USUARIO_ALTA, CL_CORREO)\n"
                + "VALUES (?,?,?,?,?,NOW(),?,?);";

        try {
            conexion = cnn.Conexion();
            conexion.setAutoCommit(false);

            st = conexion.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                id = rs.getString("id");
            }
            st.close();
            rs.close();

            ps = conexion.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, data.getNombreComercial());
            ps.setString(3, data.getNit());
            ps.setString(4, data.getDireccion());
            ps.setString(5, data.getTelefono());
            ps.setString(6, data.getUsuario());
            ps.setString(7, data.getCorreCleinte());

            ps.executeUpdate();
            ps.close();
            conexion.commit();
            cl.setResp("1");
            cl.setMsg("REGISTRO GUARDADO CORRECTAMENTE");

            conexion.close();
            cnn.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());
        }

        return cl;
    }
}
