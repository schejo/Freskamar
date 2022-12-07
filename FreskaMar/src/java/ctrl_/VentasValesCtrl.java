package ctrl_;

import Conexion.*;
import DAL.ClientesDal;
import DAL.ProductosDal;
import DAL.UsuarioDal;
import DAL.VentasValesDal;
import MD.ClientesMd;
import MD.DetalleFacturaMd;
import MD.FacturaMd;
import MD.ProductosMd;
import MD.UsuariosMd;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import Utilitarios.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.InetAddress;

import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.Component;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.WrongValueException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import org.tempuri.ProcesosRemolcadores;
//;     
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Timebox;
import Utilitarios.*;
import java.io.FileOutputStream;
import java.util.Calendar;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

public class VentasValesCtrl extends Commons {

    private Button btnGuardar;
    private Button btnImprimir;

    private static final long serialVersionUID = 1L;
    ClientesDal clientebd = new ClientesDal();

    static List<DetalleFacturaMd> lista = new ArrayList<DetalleFacturaMd>();

    ClientesDal cliente = new ClientesDal();

    Session session = Sessions.getCurrent();

    private Textbox txtEmpleadoId;
    private Textbox txtEmpleadoNombre;
    private Textbox txtEmpleadoUsuario;
    private Textbox txtFacturaEstado;
    private Combobox cbxFacturaTipoPago;
    private Textbox txtFacturaFecha;
    private Combobox cbxDetalleBusqueda;
    private Combobox cbxFacturaSerie;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    Util util = new Util();
    ProductosDal pro = new ProductosDal();

    List<ProductosMd> datosP = new ArrayList<ProductosMd>();

    UsuarioDal user = new UsuarioDal();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
//        session.setAttribute("EmpleadoId", "1");
//        session.setAttribute("EmpleadoUsuario", "JGARCIA");
//        session.setAttribute("EmpleadoNombre", "JOSE GARCIA");

        UsuariosMd mod = user.REGselectUsuario(desktop.getSession().getAttribute("USER").toString());

        txtEmpleadoId.setText(mod.getCodigo());
        txtEmpleadoNombre.setText(mod.getNombre());
        txtEmpleadoUsuario.setText(desktop.getSession().getAttribute("USER").toString());
        txtFacturaEstado.setText("INICIADO");
        cbxFacturaTipoPago.setSelectedIndex(0);
        cbxFacturaSerie.setSelectedIndex(0);
        txtFacturaFecha.setText(String.valueOf(formatter.format(new Date())));

        this.TotalesDoublebox(txtCredito, true);
        this.TotalesDoublebox(txtEfectivo, true);
        this.TotalesDoublebox(txtCambio, true);
        this.TotalesDoublebox(txtTarjeta, true);
        this.TotalesDoublebox(txtRecibido, false);
        LimpiarPagos();

        //datosP = pro.BuscarProductos();
//
//        cbxDetalleBusqueda.setModel(new ListModelList(datos));
        util.cargaCombox("SELECT P.PRO_ID,\n"
                + "       CONCAT( IFNULL(P.PRO_DESCRIPCION,' '),\" \", IFNULL(P.PRO_MARCA,' ') ,\" \",\" \", case \n"
                + "  when P.PRO_CONVERSION IS NULL OR P.PRO_CONVERSION = 0 then ' '  \n"
                + "  else  CONCAT(\"X \",P.PRO_CONVERSION)\n"
                + "end) DESCRIPCION,\n"
                + "       P.PRO_TIPO_SERVICIO,\n"
                + "       IFNULL(FORMAT(P.PRO_PRECIO_VENTA,2),'-'),\n"
                + "       IFNULL(FORMAT(IFNULL(P.PRO_DESCUENTO,0),2),'-'),\n"
                + "       IFNULL(P.PRO_STOCK,0)\n"
                + "FROM almacen.productos P  ORDER BY P.PRO_DESCRIPCION ASC", cbxDetalleBusqueda);

    }

    private Textbox txtClienteNit;
    private Textbox txtClienteNombre;
    private Textbox txtClienteId;
    private Textbox txtClienteDireccion;
    private Textbox txtClienteTelefono;
    private Textbox txtClienteAlta;

    public void onClick$btnClienteAct(Event e) {
//        EventQueues.lookup("myEventQueue", EventQueues.DESKTOP, true)
//                .publish(new Event("onChangeNickname", null, lista));

        //INVOCAR MODAL
        Window window = (Window) Executions.createComponents(
                "/Vistas/RegisClientes.zul", null, null);
        window.doModal();
    }

    public void onChange$txtRecibido(Event evt) {
        Float Cambio = (Float.parseFloat(txtRecibido.getText()) - Float.parseFloat(txtEfectivo.getText()));
        if (Float.parseFloat(txtRecibido.getText()) >= Float.parseFloat(txtEfectivo.getText())) {
            txtCambio.setText(Cambio.toString());
        } else {
            Messagebox.show("NO PUEDE INGRESAR UNA CANTIDAD MENOR AL TOTAL DE EFECTIVO A RECIBIR", "Informacion", Messagebox.OK, Messagebox.ERROR);
        }

    }

    public void onChange$txtClienteNit(Event evt) throws SQLException, SAXException, IOException, ParserConfigurationException {
        ClientesMd cl = cliente.BuscarClientes(txtClienteNit.getText());

        if (cl != null) {
            txtClienteNombre.setText(cl.getNombreComercial());
            txtClienteId.setText(cl.getCodigoCliente());
            txtClienteDireccion.setText(cl.getDireccion());
            txtClienteTelefono.setText(cl.getTelefono());
            txtClienteAlta.setText(cl.getFechaAlta());
        } else {
            limpiarCliente();
            Messagebox.show("NO EXISTE EL CLIENTE INGRESADO, INTENTE DE NUEVO O ", "Informacion", Messagebox.OK, Messagebox.ERROR);
        }

    }

    private void limpiarCliente() {
        txtClienteNombre.setText("");
        txtClienteId.setText("");
        txtClienteDireccion.setText("");
        txtClienteTelefono.setText("");
        txtClienteAlta.setText("");
    }

    private Textbox txtProductoId;
    private Textbox txtProductoTipo;
    private Textbox txtProductoDescripcion;
    private Textbox txtProductoPrecio;
    private Textbox txtProductoDescuento;
    private Textbox txtProductoStock;
    private Intbox txtProductoCantidad;
    //  private Button btnDetalleAgregar;
//
//    public void onChange$cbxDetalleBusqueda(Event evt) throws SQLException {
//
//    //            util.cargaComboxLista(datosP, cbxDetalleBusqueda);
//        
//    }

    public void onSelect$cbxDetalleBusqueda(Event evt) throws SQLException {
        ProductosDal pro = new ProductosDal();
        ProductosMd proMd = pro.BuscarProducto(cbxDetalleBusqueda.getSelectedItem().getValue().toString());

        txtProductoId.setText(proMd.getCodigo());
        txtProductoTipo.setText(proMd.getTipo_servicio());
        txtProductoDescripcion.setText(proMd.getDescripcion());
        txtProductoPrecio.setText(proMd.getPrecio_venta());
        txtProductoDescuento.setText(proMd.getDescuento());
        txtProductoStock.setText(proMd.getStock());

        //cbxDetalleBusqueda.getItems().clear();
    }

    public void BuscaItem(String letra, Combobox cb) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (letra.equals(cb.getItemAtIndex(i).getValue().toString())) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }

    public void onChange$txtProductoId(Event evt) throws SQLException {
        ProductosDal pro = new ProductosDal();
        ProductosMd proMd = pro.BuscarProducto(txtProductoId.getText());

        this.BuscaItem(txtProductoId.getText(), cbxDetalleBusqueda);

        txtProductoId.setText(proMd.getCodigo());
        txtProductoTipo.setText(proMd.getTipo_servicio());
        txtProductoDescripcion.setText(proMd.getDescripcion());
        txtProductoPrecio.setText(proMd.getPrecio_venta());
        txtProductoDescuento.setText(proMd.getDescuento());
        txtProductoStock.setText(proMd.getStock());

        //cbxDetalleBusqueda.getItems().clear();
    }

    private void LimpiarProducto() {
        cbxDetalleBusqueda.setText("");
        txtProductoId.setText("");
        txtProductoTipo.setText("");
        txtProductoDescripcion.setText("");
        txtProductoPrecio.setText("");
        txtProductoDescuento.setText("");
        txtProductoStock.setText("");
        txtProductoCantidad.setText("");

    }

    private Grid dataGrid;
    private Rows rows;
    private Row row;

    private int banderaGrid = 0;

    public List<DetalleFacturaMd> datos = new ArrayList<DetalleFacturaMd>();
    DecimalFormat formato = new DecimalFormat("#.00");

///aqui encontre la opcion de generar de aca para abajo tiene que estar la opcion de desabilitar el campo de tarjeta
    public void onClick$btnDetalleAgregar(Event evt) throws SQLException, ParseException {
        if (!txtProductoId.getText().equals("")) {
            if (txtProductoCantidad.getValue() != null) {
                if (txtProductoCantidad.getValue() != 0) {
                    if (txtProductoCantidad.getValue() > 0) {
                        if (Integer.parseInt(txtProductoCantidad.getText()) <= Integer.parseInt(txtProductoStock.getText())) {
                            if (!ProductoAgregado(txtProductoId.getText())) {
                                DetalleFacturaMd detMod = new DetalleFacturaMd();

//
                                detMod.setDetProductoId(txtProductoId.getText());
                                detMod.setDetProductoDescripcion(txtProductoDescripcion.getText());
                                detMod.setProductoCantidad(String.valueOf(txtProductoCantidad.getValue()));
                                detMod.setDetProductoPrecioVenta(txtProductoPrecio.getText());
                                detMod.setProductoDescuento(txtProductoDescuento.getText());

                                Float PrecioReal = (Float.parseFloat(txtProductoPrecio.getText()) - Float.parseFloat(txtProductoDescuento.getText()));
                                Float SubtotalDetalle = PrecioReal * Float.parseFloat(String.valueOf(txtProductoCantidad.getValue()));
                                detMod.setSubtotal(String.valueOf(formato.format(SubtotalDetalle)));
                                detMod.setDetProductoTipo(txtProductoTipo.getText());
                                detMod.setProductoStock(txtProductoStock.getText());
                                datos.add(detMod);
                                LimpiarProducto();
                            } else {
                                Messagebox.show("EL PRODUCTO YA FUE AGREGADO A LA FACTURA", "Informacion", Messagebox.OK, Messagebox.ERROR);
                            }
                        } else {
                            Messagebox.show("LA EXISTENCIA ES MENOR A LA CANTIDAD INGRESADA", "Informacion", Messagebox.OK, Messagebox.ERROR);
                        }
                    } else {
                        Messagebox.show("NO DEBE INGRESAR UNA CANTIDAD NEGATIVA", "Informacion", Messagebox.OK, Messagebox.ERROR);
                    }
                } else {
                    Messagebox.show("DEBE AGREGAR UNA CANTIDAD DIFERENTE DE 0", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }
            } else {
                Messagebox.show("DEBE AGREGAR LA CANTIDAD DE PRODUCTO QUE DESEA ADQUIRIR", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }
        } else {
            Messagebox.show("DEBE SELECCIONAR UN PRODUCTO PARA AGREGARLO", "Informacion", Messagebox.OK, Messagebox.ERROR);
        }
        ActualizaTotales();
        LlenarGrid();
    }

    private Textbox txtReferencia;
    private Doublebox txtEfectivo;
    private Doublebox txtTarjeta;
    private Doublebox txtCredito;
    private Doublebox txtCambio;
    private Doublebox txtRecibido;

    public void onSelect$cbxFacturaTipoPago(Event evt) {
        String TipoPago = cbxFacturaTipoPago.getSelectedItem().getValue().toString();

        if (datos.size() > 0) {

            if (TipoPago.equals("E")) {
                txtEfectivo.setReadonly(true);
                txtRecibido.setReadonly(false);
                txtTarjeta.setReadonly(true);
                txtCredito.setReadonly(true);
                txtReferencia.setReadonly(true);
            }

            if (TipoPago.equals("T")) {
                txtEfectivo.setReadonly(true);
                txtRecibido.setReadonly(true);
                txtTarjeta.setReadonly(true);
                txtCredito.setReadonly(true);
                txtReferencia.setReadonly(false);
            }

            if (TipoPago.equals("C")) {
                txtEfectivo.setReadonly(true);
                txtRecibido.setReadonly(true);
                txtTarjeta.setReadonly(true);
                txtCredito.setReadonly(true);
                txtReferencia.setReadonly(true);
            }
        }
        LimpiarPagos();

        ActualizaTotales();
    }

    private void LimpiarPagos() {
        txtEfectivo.setText("0.00");
        txtTarjeta.setText("0.00");
        txtReferencia.setText("");
        txtCredito.setText("0.00");
        txtRecibido.setText("");
        txtCambio.setText("");
    }

    VentasValesDal ven = new VentasValesDal();

    public void onClick$btnGuardar(Event evt) throws SQLException, ParseException {

        FacturaMd enc = new FacturaMd();

        enc.setFacturaClienteId(txtClienteId.getText());
        enc.setFacturaEmpleadoId(txtEmpleadoId.getText());
        enc.setFacturaEstado("E");

        enc.setFacturaSubtotal(txtSubtotal.getText());
        enc.setFacturaTotal(txtTotal.getText());
        enc.setFacturaDescuento(txtDescuentos.getText());
        enc.setFacturaUsuarioEmite(txtEmpleadoUsuario.getText());

        enc.setFacturaSerie(cbxFacturaSerie.getText());
        enc.setFacturaTipoPago(cbxFacturaTipoPago.getSelectedItem().getValue().toString());
        enc.setFacturaReferenciaTarjeta(this.txtReferencia.getText());
        enc.setFacturaPagoEfectivo(this.txtEfectivo.getText());
        enc.setFacturaPagoTarjeta(this.txtTarjeta.getText());
        enc.setFacturaEfectivoRecibido(this.txtRecibido.getText());
        enc.setFacturaCredito(this.txtCredito.getText());
        enc.setFacturaCambio(this.txtCambio.getText());

        enc.setFacturaAutorizacion("1D552B99-B610-4916-A1B5-A3CF8001BD7F");
        enc.setFacturaNumeroD("123456789");
        enc.setFacturaSerieDTE("1D552B99");
        enc.setFacturaFechaCertificacion("2021-05-01 14:00:00");
        enc.setFacturaNitCertificador("50510231");
        enc.setFacturaNombreCertificador("MEGAPRINT S.A.");

        if (ven.Crear(enc, datos) > 0) {
            Clients.showNotification("FACTURA CREADA <br/> EXITOSAMENTE <br/>");
            PDF(ven.BuscarEncabezadoFactura(cbxFacturaSerie.getText()));
            Limpiartodo();
        } else {
            Messagebox.show("NO SE CREO FACTURA", "Informacion", Messagebox.OK, Messagebox.ERROR);
        }

    }

    public void onClick$btnLimpiar(Event e) {
        Limpiartodo();

    }

    private void Limpiartodo() {
        cbxDetalleBusqueda.setText("");
        txtProductoId.setText("");
        txtProductoTipo.setText("");
        txtProductoDescripcion.setText("");
        txtProductoPrecio.setText("");
        txtProductoDescuento.setText("");
        txtProductoStock.setText("");
        txtProductoCantidad.setText("");
        txtClienteNit.setText("");

        txtClienteNit.setText("");
        txtClienteNombre.setText("");
        txtClienteId.setText("");
        txtClienteDireccion.setText("");
        txtClienteTelefono.setText("");
        txtClienteAlta.setText("");

        txtSubtotal.setText("");
        txtDescuentos.setText("");
        txtTotal.setText("");

        txtReferencia.setText("");
        txtEfectivo.setText("");
        txtTarjeta.setText("");

        txtCredito.setText("");
        txtCambio.setText("");
        txtRecibido.setText("");

        rows.getChildren().clear();

        datos.removeAll(datos);
    }

    private boolean ProductoAgregado(String ProductoId) {
        boolean resp = false;

        for (int i = 0; i < datos.size(); i++) {
            if (ProductoId.equals(datos.get(i).getDetProductoId())) {
                resp = true;
                break;
            }
        }
        return resp;
    }

    private Textbox txtSubtotal;
    private Textbox txtDescuentos;
    private Textbox txtTotal;

    public void ActualizaTotales() {

        subtotal = Float.parseFloat("0.00");
        descuentos = Float.parseFloat("0.00");

        for (int i = 0; i < datos.size(); i++) {
            subtotal += Float.parseFloat(datos.get(i).getDetProductoPrecioVenta()) * Float.parseFloat(datos.get(i).getProductoCantidad());
            descuentos += Float.parseFloat(datos.get(i).getProductoDescuento()) * Float.parseFloat(datos.get(i).getProductoCantidad());
        }

        total = (subtotal - descuentos);

        txtSubtotal.setText(String.valueOf(formato.format(subtotal)));
        txtDescuentos.setText(String.valueOf(formato.format(descuentos)));
        txtTotal.setText(String.valueOf(formato.format(total)));

        if (cbxFacturaTipoPago.getSelectedItem().getValue().toString().equals("E")) {
            txtEfectivo.setText(String.valueOf(formato.format(total)));
        }

        if (cbxFacturaTipoPago.getSelectedItem().getValue().toString().equals("T")) {
            txtTarjeta.setText(String.valueOf(formato.format(total)));
        }

        if (cbxFacturaTipoPago.getSelectedItem().getValue().toString().equals("C")) {
            txtCredito.setText(String.valueOf(formato.format(total)));
        }
    }

    public void LlenarGrid() throws ParseException {

        if (banderaGrid == 1) {
            row.getChildren().clear();
            rows.getChildren().clear();
            banderaGrid = 0;
        }
        int i = 1;
        for (DetalleFacturaMd mov : datos) {
            banderaGrid = 1;
            Label No = new Label();
            ValoresLabel(No, String.valueOf(i), "");
            Label Codigo = new Label();
            ValoresLabel(Codigo, mov.getDetProductoId(), "");
            Label Descripcion = new Label();
            ValoresLabel(Descripcion, mov.getDetProductoDescripcion(), "");
            Label Stock = new Label();
            ValoresLabel(Stock, mov.getProductoStock(), "");
            Intbox Cantidad = new Intbox();
            ValoresIntbox(Cantidad, mov.getProductoCantidad(), "", false);
            Label PrecioUnidad = new Label();
            ValoresLabel(PrecioUnidad, mov.getDetProductoPrecioVenta(), "");
            Doublebox Descuento = new Doublebox();
            ValoresDoublebox(Descuento, mov.getProductoDescuento(), "", false);
            Label Subtotal = new Label();
            ValoresLabel(Subtotal, mov.getSubtotal(), "");

            Div acciones = new Div();
            acciones.setClass("text-center");

            Button Eliminar = new Button();
            CreateButton(Eliminar, "btn btn-primary btn-md", "z-icon-print", "margin-left:3px;", "", true);
            acciones.appendChild(Eliminar);

            row = new Row();
            row.setStyle("border-style:solid;border-width:1px");
            row.appendChild(No);
            row.appendChild(Codigo);
            row.appendChild(Descripcion);
            row.appendChild(Stock);
            row.appendChild(Cantidad);
            row.appendChild(PrecioUnidad);
            row.appendChild(Descuento);
            row.appendChild(Subtotal);
            row.appendChild(acciones);

            row.setParent(rows);
            row.setValue(mov);
            Eliminar.addEventListener("onClick", EliminarProducto);
            Cantidad.addEventListener("onChange", ModificarCantidad);
            Descuento.addEventListener("onChange", ModificarDescuento);
            i++;
        }
    }

    Float subtotal = Float.parseFloat("0.0"), descuentos = Float.parseFloat("0.0"), total = Float.parseFloat("0.0");

    EventListener EliminarProducto = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws SQLException, IOException, ParseException {
            DetalleFacturaMd modelo = new DetalleFacturaMd();

            Button button = (Button) event.getTarget();
            Div div = (Div) button.getParent();
            Row row = (Row) div.getParent();

            modelo = row.getValue();

            for (int i = 0; i < datos.size(); i++) {
                if (modelo.getDetProductoId().equals(datos.get(i).getDetProductoId())) {
                    datos.remove(i);
                }
            }
            ActualizaTotales();

            LlenarGrid();
        }
    };

    EventListener ModificarCantidad = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws SQLException, IOException, ParseException {
            DetalleFacturaMd modelo = new DetalleFacturaMd();

            Intbox text = (Intbox) event.getTarget();
            Row row = (Row) text.getParent();

            modelo = row.getValue();

            if (text.getValue() != null) {
                if (text.getValue() != 0) {
                    if (text.getValue() > 0) {
                        if (Integer.parseInt(text.getText()) <= Integer.parseInt(modelo.getProductoStock())) {
                            for (int i = 0; i < datos.size(); i++) {
                                if (modelo.getDetProductoId().equals(datos.get(i).getDetProductoId())) {
                                    datos.get(i).setProductoCantidad(text.getValue().toString());
                                    Float PrecioReal = (Float.parseFloat(datos.get(i).getDetProductoPrecioVenta()) - Float.parseFloat(datos.get(i).getProductoDescuento()));
                                    Float SubtotalDetalle = PrecioReal * Float.parseFloat(String.valueOf(datos.get(i).getProductoCantidad()));

                                    datos.get(i).setSubtotal(String.valueOf(formato.format(SubtotalDetalle)));
                                }
                            }

                        } else {
                            Messagebox.show("LA EXISTENCIA ES MENOR A LA CANTIDAD INGRESADA", "Informacion", Messagebox.OK, Messagebox.ERROR);
                        }
                    } else {
                        Messagebox.show("NO DEBE INGRESAR UNA CANTIDAD NEGATIVA", "Informacion", Messagebox.OK, Messagebox.ERROR);
                    }
                } else {
                    Messagebox.show("DEBE AGREGAR UNA CANTIDAD DIFERENTE DE 0", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }
            } else {
                Messagebox.show("DEBE AGREGAR LA CANTIDAD DE PRODUCTO QUE DESEA ADQUIRIR", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }

            ActualizaTotales();
            LlenarGrid();

        }
    };

    EventListener ModificarDescuento = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws SQLException, IOException, ParseException {
            DetalleFacturaMd modelo = new DetalleFacturaMd();

            Doublebox text = (Doublebox) event.getTarget();
            Row row = (Row) text.getParent();

            modelo = row.getValue();

            if (text.getValue() != null) {
                if (text.getValue() >= 0) {
                    if (Float.parseFloat(text.getText()) <= Float.parseFloat(modelo.getDetProductoPrecioVenta())) {
                        for (int i = 0; i < datos.size(); i++) {
                            if (modelo.getDetProductoId().equals(datos.get(i).getDetProductoId())) {
                                datos.get(i).setProductoDescuento(text.getText());
                                Float PrecioReal = (Float.parseFloat(datos.get(i).getDetProductoPrecioVenta()) - Float.parseFloat(datos.get(i).getProductoDescuento()));
                                Float SubtotalDetalle = PrecioReal * Float.parseFloat(String.valueOf(datos.get(i).getProductoCantidad()));

                                datos.get(i).setSubtotal(String.valueOf(formato.format(SubtotalDetalle)));
                            }
                        }

                    } else {
                        Messagebox.show("EL DESCUENTO ES MAYOR AL PRECIO DEL PRODUCTO", "Informacion", Messagebox.OK, Messagebox.ERROR);
                    }
                } else {
                    Messagebox.show("NO DEBE INGRESAR UNA CANTIDAD NEGATIVA", "Informacion", Messagebox.OK, Messagebox.ERROR);
                }

            } else {
                Messagebox.show("DEBE AGREGAR LA CANTIDAD DE PRODUCTO QUE DESEA ADQUIRIR", "Informacion", Messagebox.OK, Messagebox.ERROR);
            }

            ActualizaTotales();
            LlenarGrid();

        }
    };

    public void ValoresLabel(Label label, String valor, String clase) {
        label.setValue(valor);
        label.setClass(clase);
    }

    public void ValoresIntbox(Intbox text, String valor, String clase, boolean read) {
        text.setValue(Integer.parseInt(valor));
        text.setReadonly(read);
        text.setWidth("100%");
        text.setClass(clase);
    }

    public void TotalesDoublebox(Doublebox text, boolean read) {
        text.setReadonly(read);
        text.setFormat("###0.##");
        text.setLocale(Locale.US);

    }

    public void ValoresDoublebox(Doublebox text, String valor, String clase, boolean read) {
        text.setValue(Float.parseFloat(valor));
        text.setReadonly(read);
        text.setFormat("###0.##");
        text.setLocale(Locale.US);
        text.setWidth("100%");
        text.setClass(clase);
    }

    public void CreateButton(Button button, String clase, String icon, String style, String label, boolean visible) {
        button.setClass(clase);
        button.setIconSclass(icon);
        button.setStyle(style);
        button.setVisible(visible);
        button.setLabel(label);
    }

    File f;

    public void PDF(FacturaMd enc/*, java.util.List<DetalleFacturaMd> lista*/) throws SQLException {
        //  String ano_arribo="", num_Arribo="";

        String buque = "";
        DecimalFormat formato = new DecimalFormat("#.00");

        List<DetalleFacturaMd> lista = ven.BuscaDetallesFactura();

        try {
            com.itextpdf.text.Document detalle = new com.itextpdf.text.Document(PageSize.LETTER.rotate());
            ByteArrayOutputStream badetalle = new ByteArrayOutputStream();
            PdfWriter escritura;
            // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(desktop.getWebApp().getRealPath("rpt") + File.separator + "vale.pdf");

// Se asocia el documento al OutputStream y se indica que el espaciado entre
// lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
            PdfWriter.getInstance(detalle, ficheroPdf).setInitialLeading(20);

            //escritura = PdfWriter.getInstance(detalle, badetalle);
            detalle.open();

            Paragraph predetalle = new Paragraph();
            predetalle.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////// ENCABEZADO DE BOLETA ///////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            PdfPTable table2 = new PdfPTable(10);   //tenia 10,90
            table2.setWidthPercentage(100);

            PdfPCell cell2;

            /*cell2 = new PdfPCell(new Phrase("Servicios de ventas Facturadas", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);*/
            cell2 = new PdfPCell(new Phrase("\"TIENDA FRESKAMAR \"", FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("NIT: 441642-2", FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("VALE DE ENTREGA ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////// DATOS FACTURA FACTURA/////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("INTERNO: " + enc.getFacturaSerie() + " - " + enc.getFacturaNumero(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("ATENDIO: " + enc.getFacturaEmpleadoId() + "  " + enc.getFacturaEmpleadoNombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("NIT: " + enc.getFacturaClienteNit(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("CLIENTE: " + enc.getFacturaClienteId() + "  " + enc.getFacturaClienteNombre(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("DIRECCION: " + enc.getFacturaClienteDireccion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

            cell2 = new PdfPCell(new Phrase("FECHA IMP: " + timeStamp, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////// DATOS DETALLE FACTURA/////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("___________________________________________________________________________________________________________"));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("CODIGO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(0);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("PRODUCTO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(6);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("PRECIO UNITARIO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(1);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("TOTAL", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD)));
            cell2.setColspan(3);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("___________________________________________________________________________________________________________"));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            for (int i = 0; i < lista.size(); i++) {
                cell2 = new PdfPCell(new Phrase(lista.get(i).getDetProductoId() + " " + lista.get(i).getDetProductoDescripcion() + " " + lista.get(i).getDetProductoMarca() + " "
                        + lista.get(i).getDetProductoPresentacion() + " " + lista.get(i).getDetProductoConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 13)));
                cell2.setColspan(9);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase(""));
                cell2.setColspan(1);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase(lista.get(i).getProductoCantidad() + " x Q." + lista.get(i).getDetProductoPrecioVenta(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 13)));
                cell2.setColspan(9);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase(String.valueOf(formato.format(Float.parseFloat(lista.get(i).getDetProductoPrecioVenta()) * Float.parseFloat(lista.get(i).getProductoCantidad()))), FontFactory.getFont(FontFactory.TIMES_ROMAN, 13)));
                cell2.setColspan(1);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                table2.addCell(cell2);
            }

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("___________________________________________________________________________________________________________"));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////// DATOS TOTALES FACTURA/////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(3);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("SUBTOTAL: Q." + enc.getFacturaSubtotal(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
            cell2.setColspan(7);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(3);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("(-) DESCUENTO: Q." + enc.getFacturaDescuento(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
            cell2.setColspan(7);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(3);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("TOTAL: Q." + enc.getFacturaTotal(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
            cell2.setColspan(7);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(""));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("MEDIO DE PAGO: " + enc.getFacturaTipoPago(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            if (enc.getFacturaTipoPago().equals("EFECTIVO") || enc.getFacturaTipoPago().equals("MULTIPLE")) {
                cell2 = new PdfPCell(new Phrase("EFECTIVO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                cell2.setColspan(9);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase("Q." + enc.getFacturaPagoEfectivo(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11)));
                cell2.setColspan(1);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                table2.addCell(cell2);

            }

            if (enc.getFacturaTipoPago().equals("TARJETA") || enc.getFacturaTipoPago().equals("MULTIPLE")) {

                cell2 = new PdfPCell(new Phrase("TARJETA D/C", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                cell2.setColspan(9);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase("Q." + enc.getFacturaPagoTarjeta(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11)));
                cell2.setColspan(1);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                table2.addCell(cell2);
            }

            if (enc.getFacturaTipoPago().equals("CREDITO") || enc.getFacturaTipoPago().equals("MULTIPLE")) {

                cell2 = new PdfPCell(new Phrase("CREDITO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                cell2.setColspan(9);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase("Q." + enc.getFacturaCredito(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11)));
                cell2.setColspan(1);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                table2.addCell(cell2);

            }

            if (enc.getFacturaTipoPago().equals("EFECTIVO") || enc.getFacturaTipoPago().equals("MULTIPLE")) {
                cell2 = new PdfPCell(new Phrase("PAGO RECIBIDO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                cell2.setColspan(9);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase("Q." + enc.getFacturaEfectivoRecibido(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11)));
                cell2.setColspan(1);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase("CAMBIO", FontFactory.getFont(FontFactory.TIMES_ROMAN, 10)));
                cell2.setColspan(9);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                cell2.setBorder(Rectangle.NO_BORDER);
                table2.addCell(cell2);

                cell2 = new PdfPCell(new Phrase("Q." + enc.getFacturaCambio(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 11)));
                cell2.setColspan(1);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
                table2.addCell(cell2);

            }

            cell2 = new PdfPCell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("Es un placer el Servirle, Vuelva Pronto!!!", FontFactory.getFont(FontFactory.TIMES_ROMAN, 15)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("___________________________________________________________________________________________________________"));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 11)));
            cell2.setColspan(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            table2.addCell(cell2);

            predetalle.add(table2);

            detalle.add(predetalle);

            detalle.close();
            //escritura.close();

            f = new File(desktop.getWebApp().getRealPath("rpt") + File.separator + "Vale.pdf");
            //Messagebox.show("CREA ARCHIVO");
            byte[] buffer = new byte[(int) f.length()];
            FileInputStream fs = new FileInputStream(f);

            fs.read(buffer);
            fs.close();

            //ByteArrayInputStream is = new ByteArrayInputStream(buffer);
            InputStream is = new ByteArrayInputStream(buffer);
            Filedownload.save(is, "application/pdf", "Vale.pdf");
//            AMedia amedia = new AMedia("archivo.pdf", "pdf", "application/pdf", is);
//            is.close();
//            // f.delete();
//
//            desktop.setAttribute("reporte", amedia);
//            Window w = (Window) execution.createComponents("Vistas/Rpt_Boleta.zul", ventana, null);
//            w.setMaximized(true);
//            w.doHighlighted();

            // Creamos un PDDocument con el arreglo de entrada que creamos        
//////////////////////////            PDDocument document = PDDocument.load(is);
//////////////////////////            PrinterJob job = PrinterJob.getPrinterJob();
//////////////////////////
//////////////////////////            String parm = Executions.getCurrent().getRemoteAddr();
//////////////////////////
//////////////////////////            PrintService myPrintService = this.findPrintService("POS");
//////////////////////////            //PrintService myPrintService = this.findPrintService("\\\\"+parm+"\\EPSON L3110 Series");
//////////////////////////
//////////////////////////            //   Messagebox.show(parm);
//////////////////////////            job.setPageable(new PDFPageable(document));
//////////////////////////
//////////////////////////            job.setPrintService(myPrintService);
//////////////////////////
//////////////////////////            job.print();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}

//    String Codigo_Respuesta;
//    String Descripcion_Respuesta;
//
//    ClientesMd clientes = null;
//
//    Session ses = Sessions.getCurrent();
//    String[] resp;
//    Window ventana;

