package ctrl_;

import DAL.MovimientosDal;
import MD.MovimientosMd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class MovimientosCtrl extends GenericForwardComposer {

    private Textbox codMov;
    private Textbox corMov;
    private Doublebox preMov;
    private Datebox ingMov;
    private Textbox usuMov;
    private Textbox nomMov;
    private Textbox canMov;

    List<MovimientosMd> allMovimientos = new ArrayList<MovimientosMd>();

    private Listbox lb2;
   // MovimientosDal rg = new MovimientosDal();
        MovimientosDal error = new MovimientosDal();
        private Include rootPagina;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //allMovimientos = error.RSelect();
        //lb2.setModel(new ListModelList(allMovimientos));
        usuMov.setText(desktop.getSession().getAttribute("USER").toString());
        codMov.focus();
    }

    public void onClick$btnNuevo(Event e) throws SQLException {
        codMov.setText("");
        corMov.setText("");
        preMov.setText("");
        ingMov.setText("");
        nomMov.setText("");
        canMov.setText("");
        corMov.focus();

    }

    public void onChange$codMov(Event e) throws SQLException, ClassNotFoundException {
        String correla = "";

        correla = error.Correlativo(codMov.getText());
        corMov.setText(correla);
        preMov.setFocus(true);
    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        for (MovimientosMd dt : allMovimientos) {
            if (dt.getCodigo().equals(codMov.getText())) {
                op++;
            }
        }

        if (op == 0) {
            error.REGinsert(codMov.getText(),corMov.getText(), preMov.getText(), ingMov.getText(),
                             usuMov.getText(), nomMov.getText().toUpperCase(), canMov.getText());
            
            error.CalculaMov(codMov.getText(), corMov.getText(), canMov.getText(), preMov.getText());
            
            System.out.println("Entro al if : "+op);
            codMov.setText("");
            corMov.setText("");
            preMov.setText("");
            ingMov.setText("");
            usuMov.setText("");
            nomMov.setText("");
            canMov.setText("");
            
            

            allMovimientos = error.RSelect();
           // lb2.setModel(new ListModelList(allMovimientos));
        } else {
            error.REGupdate(codMov.getText(), corMov.getText(), preMov.getText(), ingMov.getText(),
                     usuMov.getText(), nomMov.getText(), canMov.getText());

            codMov.setDisabled(false);
            corMov.setText("");
            preMov.setText("");
            ingMov.setText("");
            usuMov.setText("");
            nomMov.setText("");
            canMov.setText("");
            codMov.focus();
            allMovimientos = error.RSelect();
          //  lb2.setModel(new ListModelList(allMovimientos));
        }
    }

    public void onClick$btnActualiza(Event e) throws SQLException {
        codMov.setText("");
        corMov.setText("");
        preMov.setText("");
        ingMov.setText("");
        usuMov.setText("");
        nomMov.setText("");
        canMov.setText("");
        codMov.focus();
    }

    public void onClick$btnDelete(Event e) throws SQLException {

        if (!codMov.getText().equals("") && !codMov.getText().equals("")) {
            Messagebox.show("Estas seguro que Deseas Borrar este Registro?",
                    "Question", Messagebox.OK | Messagebox.CANCEL,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event e) throws SQLException, ClassNotFoundException {
                    if (Messagebox.ON_OK.equals(e.getName())) {
                        error.REGdelete(codMov.getText());
                        codMov.setDisabled(false);
                        corMov.setText("");
                        preMov.setText("");
                        ingMov.setText("");
                        usuMov.setText("");
                        nomMov.setText("");
                        canMov.setText("");

                        allMovimientos = error.RSelect();
                        lb2.setModel(new ListModelList(allMovimientos));
                    } else if (Messagebox.ON_CANCEL.equals(e.getName())) {
                        Clients.showNotification("REGISTRO NO SE HA <br/> BORRADO <br/>");
                    }
                }
            }
            );

        } else {
            Clients.showNotification("DEBE SELECCIONAR <br/> UN REGISTRO! <br/>",
                    "warning", null, "middle_center", 50);
        }
    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Vistas/Principal.zul");
    }
}
