package janis.service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

public class NewPdf {
    private static String FILE = System.getProperty("java.io.tmpdir") + "testTwoSidePdfFile.pdf";

    public static String createNewPdf() throws FileNotFoundException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(FILE));
        Document doc = new Document(pdfDoc);
        drawCircleOnNewPage(pdfDoc);
        PdfCanvas pdfCanvas = new PdfCanvas(pdfDoc.getFirstPage());
        Rectangle rectangle = new Rectangle(250, 420, 200, 15);
        Rectangle rectangle2 = new Rectangle(150, 100, 400, 200);
        Canvas canvas = new Canvas(pdfCanvas, pdfDoc, rectangle);
        Canvas noteCanvas = new Canvas(pdfCanvas, pdfDoc, rectangle2);
        canvas.add(new Paragraph("Pierwsza strona / First page"));
        noteCanvas.add(new Paragraph("Measure distance in x and y between dots on first page and put it in."));
        noteCanvas.add(new Paragraph("Zmierz odleglosc po x i y miedzy punktami na pierwszej i drugiej stronie"));
        noteCanvas.add(new Paragraph("Jezeli punkt na 2 stronie jest wyzej to wartosc X jest dodatnia"));
        noteCanvas.add(new Paragraph("Jezeli punkt na 2 stronie jest na prawo to wartosc Y jest dodatnia"));
        drawCircleOnNewPage(pdfDoc);
        pdfCanvas = new PdfCanvas(pdfDoc.getLastPage());
        canvas = new Canvas(pdfCanvas, pdfDoc, rectangle);
        canvas.add(new Paragraph("Druga strona / Second page"));
        doc.close();
        pdfDoc.close();
        return FILE;
    }

    private static void drawCircleOnNewPage(PdfDocument pdfDoc) {
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.setColor(DeviceCmyk.BLACK, true);
        canvas.circle(100, 100, 2);
        canvas.circle(500, 100, 2);
        canvas.circle(100, 750, 2);
        canvas.circle(500, 750, 2);
        canvas.fill();
        Rectangle rectangle = new Rectangle(240, 415, 180, 20);
        canvas.rectangle(rectangle);
        canvas.stroke();
    }
}
