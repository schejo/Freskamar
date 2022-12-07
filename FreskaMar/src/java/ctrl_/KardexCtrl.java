package ctrl_;

import DAL.KardexDal;
import MD.KardexMd;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;

public class KardexCtrl extends GenericForwardComposer {

    private Datebox fechaG;

    public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void onClick$btnDescargar(Event e) throws SQLException, ClassNotFoundException {
        String usuario = (String) desktop.getSession().getAttribute("USER");
        if (!fechaG.getText().isEmpty()) {
            GeneraPDF(fechaG.getText(), usuario);
        } else {
            Clients.showNotification("INGRESE UNA FECHA POR FAVOR PARA PODER GENERAR EL PDF");
        }
    }

    public void GeneraPDF(String anio, String user) throws SQLException, ClassNotFoundException {
        System.out.println("Generar PDF");
        Document document;
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        List<KardexMd> alldata = new ArrayList<KardexMd>();
        KardexDal rd = new KardexDal();
        alldata = rd.REGselect(anio);

        Float totalEfectivo = Float.parseFloat("0.00");
        Float totalCantidad = Float.parseFloat("0.00");
        Float totalPrecioxCanti = Float.parseFloat("0.00");

        for (int i = 0; i < alldata.size(); i++) {
            totalEfectivo += Float.parseFloat(alldata.get(i).getPrecio_ing());
            totalCantidad += Float.parseFloat(alldata.get(i).getExistencia());
            totalPrecioxCanti += Float.parseFloat(alldata.get(i).getPrecioXcantidad());
        }

        if (alldata.isEmpty()) {
            Clients.showNotification("NO TIENE DATOS..!");
        }
        try {

            document = new Document();
            document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);
            System.out.println("Despues del PDFWriter" + anio);

            String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/img/reportar.png";
            Image im = Image.getInstance(dirImagen);
            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
            im.setAbsolutePosition(25, 700);
            im.scalePercent(10);
            ParrafoHoja.add(im);

            ParrafoHoja.add(new Paragraph("              FRESKAMAR"));
            ParrafoHoja.add(new Paragraph("              Sistema Control de Inventario y Facturacion"));
            ParrafoHoja.add(new Paragraph("              Kardex de Productos"));
            ParrafoHoja.add(new Paragraph("              Fecha de Reporte.: " + anio));
            ParrafoHoja.add(new Paragraph("              USUARIO.: " + user));

            //System.out.println("CREAR TABLA");
            float anchosFilas[] = {0.3f, 0.8f, 0.4f, 0.3f, 0.4f, 0.2f, 0.3f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"CODIGO", "NOMBRE", "MARCA", "PRECIO",
                "UBICACION", "SALDO", "TOTAL"};//Rotulos de las columnas 

            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i]));
                tabla.addCell(cell);
            }

            int i = 0;
            for (KardexMd a : alldata) {
                i++;
                cell = new PdfPCell(new Paragraph(a.getCodigo()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getNombre()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getMarca()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getPrecio_ing()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getPrecio_sal()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getExistencia()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getPrecioXcantidad()));
                tabla.addCell(cell);
            }

            cell = new PdfPCell(new Paragraph("TOTAL"));
            cell.setColspan(5);
            cell.setBorder(0);
            cell.setHorizontalAlignment(1);
            tabla.addCell(cell);
            
            
            cell = new PdfPCell(new Paragraph(String.valueOf("Q"+totalPrecioxCanti)));
            cell.setColspan(2);
            cell.setBorder(0);
            tabla.addCell(cell);


//            cell = new PdfPCell(new Paragraph(String.valueOf(totalCantidad)));
//            cell.setColspan(i);
//            cell.setBorder(0);
//            tabla.addCell(cell);
//            //FIN DE SUMA DE COLUMNAS
// ParrafoHoja.add(new Paragraph("               Kardex: "));
            ParrafoHoja.add(tabla);
            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("Kardex.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
