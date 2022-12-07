/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl_;
import DAL.ClientesDal;
import MD.ClientesMd;
import Util.EstilosReporte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.util.media.AMedia;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



/**
 *
 * @author HP 15
 */
public class Clientes1Ctrl extends GenericForwardComposer{
     private Textbox Ncliente;
    private Textbox NitCliente;
    private Textbox Direccion;
    private Doublebox Telefono;
    private Textbox Correo;
    private Textbox Usu;
    Window modalDialog;

    private Include rootPagina;

    ClientesMd clienteMD = new ClientesMd();
    ClientesDal clienteDal = new ClientesDal();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Usu.setText(desktop.getSession().getAttribute("USER").toString());
    }

  

    public void onClick$btnSalir1(Event e) {
//        List<CatalogosMd> items = new ArrayList<CatalogosMd>();
//        items.clear();
//        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
//                .publish(new Event("onChangeNickname", null, items));
        modalDialog.detach();
    }

  

    //boton guardar
    public void onClick$btnGuardar(Event e) {
        int op = 0;
        if (Ncliente.getText().trim().equals("")) {
            op = 1;
        }
        if (NitCliente.getText().trim().equals("") || NitCliente.getText().trim().equals("1")) {
            op = 1;
        }
        if (Direccion.getText().trim().equals("")) {
            op = 1;
        }
        if (Telefono.getText().trim().equals("") || Telefono.getText().trim().equals("1")) {
            op = 1;
        }
        if (Correo.getText().trim().equals("")) {
            op = 1;
        }
        if (op == 0) {
            clienteMD = new ClientesMd();
            clienteMD.setNombreComercial(Ncliente.getText().toUpperCase());
            clienteMD.setNit(NitCliente.getText().toUpperCase());
            clienteMD.setDireccion(Direccion.getText().toUpperCase());
            clienteMD.setTelefono(Telefono.getText().toUpperCase());
            clienteMD.setCorreCleinte(Correo.getText().toUpperCase());
            clienteMD.setUsuario(Usu.getText().toUpperCase());
            clienteMD = clienteDal.saveCLModal(clienteMD);

            if (clienteMD.getResp().equals("1")) {
                clear();
                Clients.showNotification(clienteMD.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 5000);
                modalDialog.detach();
            } else {
                Clients.showNotification(clienteMD.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 5000);
            }

        } else {
            {
                Clients.showNotification("Hay Campos Vacios<br/> O <br/>Telefono,Nit tiene valor 1   <br/> <br/>Intentelo de Nuevo",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 5000);
            }

        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Vistas/Principal.zul");
    }

    public void onClick$btnModificar() {
        rootPagina.setSrc("/Vistas/ClientesUP.zul");
    }

    public void onClick$btnNuevo(Event e) throws SQLException {

        clear();

    }

    public void clear() {

        Ncliente.setText("");
        NitCliente.setText("");
        Direccion.setText("");
        Telefono.setText("1");
        Correo.setText("");

    }

    
    
    
}
