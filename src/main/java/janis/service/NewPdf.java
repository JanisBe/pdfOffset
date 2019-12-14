package janis.service;

import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

import java.io.FileNotFoundException;

public class NewPdf {
    private static String FILE = System.getProperty("java.io.tmpdir") + "testTwoSidePdfFile.pdf";

    public static String createNewPdf() throws FileNotFoundException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(FILE));
        Document doc = new Document(pdfDoc);
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.setColor(DeviceCmyk.BLACK, true);
        canvas.circle(100, 100, 2);
        canvas.circle(500, 100, 2);
        canvas.circle(100, 750, 2);
        canvas.circle(500, 750, 2);
        canvas.fill();
        doc.close();
        pdfDoc.close();
        return FILE;
    }
}
