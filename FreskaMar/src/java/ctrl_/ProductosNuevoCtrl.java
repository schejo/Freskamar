/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl_;

import DAL.ProductosNuevoDal;
import MD.ProductosNuevoMd;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

/**
 *
 * @author chejo
 */
public class ProductosNuevoCtrl extends GenericForwardComposer {

    //para mostrar los prodcutos y sus codigos
    private Combobox busPro;
    List<ProductosNuevoMd> allProductos = new ArrayList<ProductosNuevoMd>();
    List<ProductosNuevoMd> allProductos1 = new ArrayList<ProductosNuevoMd>();

    ProductosNuevoDal ProductoDal = new ProductosNuevoDal();//lo copie de usuarios
    //para mostrar
    ProductosNuevoMd ProductoModelo = new ProductosNuevoMd();
    private Textbox codPro;
    private Textbox nomPro;
    private Combobox tipPro;
    private Combobox tisPro;
    private Textbox marPro;
    private Combobox prsPro;
    private Doublebox prePro;
    private Doublebox desPro;
    private Doublebox stoPro;
    private Textbox covPro;
    private Combobox medPro;
    private Textbox minPro;
    private Textbox maxPro;
    private Combobox ubiPro;
    //rediraccionar paginas 
    private Include rootPagina;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allProductos1 = ProductoDal.RSelect();
        allProductos = ProductoDal.allCL();
        busPro.setModel(new ListModelList(allProductos));
        desPro.setFormat("###0.##");
        desPro.setLocale(Locale.US);
        prePro.setFormat("###0.##");
        prePro.setLocale(Locale.US);

    }
    
    public void onChange$nomPro(Event e) {
        ProductoModelo = new ProductosNuevoMd();
        ProductoModelo = ProductoDal.BusProducto(nomPro.getText());
        if (ProductoModelo.getResp().equals("1")) {
           
            
        } else {
            //clear();
           // nomPro.setText("");
            //curso.setDisabled(false);
           // nomPro.setFocus(true);
            Clients.showNotification(ProductoModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);
        }
        
    }

    public void onChange$codPro(Event e) {
        ProductoModelo = new ProductosNuevoMd();
        ProductoModelo = ProductoDal.MostrarProducto(codPro.getText());
        if (ProductoModelo.getResp().equals("1")) {
            nomPro.setText(ProductoModelo.getDescripcion());
            BuscaItem(ProductoModelo.getTipo_pro(), this.tipPro);
            // tipPro.setText(ProductoModelo.getTipo_pro());
            //tisPro.setText(ProductoModelo.getTipo_ser());
            BuscaItem(ProductoModelo.getTipo_ser(), this.tisPro);
            marPro.setText(ProductoModelo.getMarca());
            BuscaItem(ProductoModelo.getPresentacion(), this.prsPro);
            //prsPro.setText(ProductoModelo.getPresentacion());
            prePro.setText(ProductoModelo.getPre_venta());
            desPro.setText(ProductoModelo.getDescuento());
            stoPro.setText(ProductoModelo.getPro_stock());
            stoPro.setDisabled(true);
            covPro.setText(ProductoModelo.getPro_conver());
            //medPro.setText(ProductoModelo.getMedi_pro());
            BuscaItem(ProductoModelo.getMedi_pro(), this.medPro);
            minPro.setText(ProductoModelo.getMinimo());
            maxPro.setText(ProductoModelo.getMaximo());
            // ubiPro.setText(ProductoModelo.getUbicacion());
            BuscaItem(ProductoModelo.getUbicacion(), this.ubiPro);

        } else {
            clear();
            codPro.setText("");
            codPro.setDisabled(false);
            codPro.setFocus(true);
            Clients.showNotification(ProductoModelo.getMsg() + "<br/>",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
        }

    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException, ParseException {
        int op = 0, op1 = 0;
        if (nomPro.getText().trim().equals("")) {
            op = 1;
        }
        if (tipPro.getSelectedIndex() == -1) {
            op = 1;
        }
        if (tisPro.getSelectedIndex() == -1) {
            op = 1;
        }
        if (marPro.getText().trim().equals("")) {
            op = 1;
        }
        if (prsPro.getSelectedIndex() == -1) {
            op = 1;
        }
        if (prePro.getText().trim().equals("")) {
            op = 1;
        }
        if (desPro.getText().trim().equals("")) {
            op = 1;
        }
        if (stoPro.getText().trim().equals("")) {
            op = 1;
        }
        if (covPro.getText().trim().equals("")) {
            op = 1;
        }
        if (medPro.getSelectedIndex() == -1) {
            op = 1;
        }
        if (minPro.getText().trim().equals("")) {
            op = 1;
        }
        if (maxPro.getText().trim().equals("")) {
            op = 1;
        }
        if (ubiPro.getText().trim().equals("")) {
            op = 1;
        }
        if (op == 0) {
            ProductoModelo = new ProductosNuevoMd();

            for (ProductosNuevoMd dt : allProductos1) {
                if (dt.getCodigo().equals(codPro.getText())) {
                    op1++;
                }
            }

            if (op1 == 0) {

                //aqui se pone lo que se va a guardar
                ProductoModelo.setDescripcion(nomPro.getText().toUpperCase());
                ProductoModelo.setTipo_pro(tipPro.getSelectedItem().getValue().toString());
                ProductoModelo.setTipo_ser(tisPro.getSelectedItem().getValue().toString());
                ProductoModelo.setMarca(marPro.getText().toUpperCase());
                ProductoModelo.setPresentacion(prsPro.getSelectedItem().getValue().toString());
                ProductoModelo.setPre_venta(prePro.getText().toUpperCase());
                ProductoModelo.setDescuento(desPro.getText().toUpperCase());
                ProductoModelo.setPro_stock(stoPro.getText().toUpperCase());
                ProductoModelo.setPro_conver(covPro.getText().toUpperCase());
                ProductoModelo.setMedi_pro(medPro.getSelectedItem().getValue().toString());
                ProductoModelo.setMinimo(minPro.getText().toUpperCase());
                ProductoModelo.setMaximo(maxPro.getText().toUpperCase());
                ProductoModelo.setUbicacion(ubiPro.getSelectedItem().getValue().toString());
                ProductoModelo = ProductoDal.savePro(ProductoModelo);
                allProductos = ProductoDal.allCL();
                allProductos1 = ProductoDal.RSelect();
                busPro.setModel(new ListModelList(allProductos));

            } else {
                ProductoModelo.setCodigo(codPro.getText().toUpperCase());
                ProductoModelo.setDescripcion(nomPro.getText().toUpperCase());
                ProductoModelo.setTipo_pro(tipPro.getSelectedItem().getValue().toString());
                ProductoModelo.setTipo_ser(tisPro.getSelectedItem().getValue().toString());
                ProductoModelo.setMarca(marPro.getText().toUpperCase());
                ProductoModelo.setPresentacion(prsPro.getSelectedItem().getValue().toString());
                ProductoModelo.setPre_venta(prePro.getText().toUpperCase());
                ProductoModelo.setDescuento(desPro.getText().toUpperCase());
                ProductoModelo.setPro_stock(stoPro.getText().toUpperCase());
                ProductoModelo.setPro_conver(covPro.getText().toUpperCase());
                ProductoModelo.setMedi_pro(medPro.getSelectedItem().getValue().toString());
                ProductoModelo.setMinimo(minPro.getText().toUpperCase());
                ProductoModelo.setMaximo(maxPro.getText().toUpperCase());
                ProductoModelo.setUbicacion(ubiPro.getSelectedItem().getValue().toString());
                ProductoModelo = ProductoDal.updatePro(ProductoModelo);
                allProductos = ProductoDal.allCL();
                allProductos1 = ProductoDal.RSelect();
                busPro.setModel(new ListModelList(allProductos));

                //aqui se pone lo que se va a modificar
            }

            if (ProductoModelo.getResp().equals("1")) {
                clear();
                Clients.showNotification(ProductoModelo.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000);
            } else {
                Clients.showNotification(ProductoModelo.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 3000);
            }
        } else {
            Clients.showNotification("No puede dejar  <br/>  <br/>  Campos Vacios <br/> <br/>Intentelo de Nuevo",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }

    }

    public void BuscaItem(String letra, Combobox cb) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (letra.equals(cb.getItemAtIndex(i).getValue())) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }

    public void onClick$btnNuevo(Event e) throws SQLException {

        clear();

    }

    public void clear() {
        busPro.setText("");
        busPro.setSelectedIndex(-1);
        codPro.setText("");
        nomPro.setText("");
        tipPro.setText("");
        tipPro.setSelectedIndex(-1);
        tisPro.setText("");
        tisPro.setSelectedIndex(-1);
        marPro.setText("");
        prsPro.setText("");
        prsPro.setSelectedIndex(-1);
        prePro.setText("");
        desPro.setText("");
        stoPro.setText("");
        stoPro.setDisabled(false);
        covPro.setText("");
        medPro.setText("");
        medPro.setSelectedIndex(-1);
        minPro.setText("");

        maxPro.setText("");
        ubiPro.setText("");
        ubiPro.setSelectedIndex(-1);
        nomPro.focus();

    }

    public void onClick$btnSalir() {
        rootPagina.setSrc("/Vistas/Principal.zul");
    }

}
