package janis.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;

import static java.lang.ClassLoader.getSystemResource;

public class NewPdf {
    private static String FILE = System.getProperty("java.io.tmpdir") + "testTwoSidePdfFile.pdf";

    public static String createNewPdf() throws FileNotFoundException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(FILE));
        Document doc = new Document(pdfDoc);
        drawCircleOnNewPage(pdfDoc);
        PdfCanvas pdfCanvas = new PdfCanvas(pdfDoc.getFirstPage());
        Rectangle pageRectangle = new Rectangle(210, 415, 200, 15);
        Rectangle noteRectangle = new Rectangle(150, 100, 400, 200);
        Canvas pageCanvas = new Canvas(pdfCanvas, pdfDoc, pageRectangle);
        Canvas noteCanvas = new Canvas(pdfCanvas, pdfDoc, noteRectangle);
        pageCanvas.add(new Paragraph("Pierwsza strona / First page"));

        noteCanvas.add(new Paragraph("Measure distance in x and y between dots on first page and put it in."));
        noteCanvas.add(new Paragraph("Zmierz odleglosc po x i y miedzy punktami na pierwszej i drugiej stronie"));
        noteCanvas.add(new Paragraph("Jezeli punkt na 2 stronie jest na prawo to wartosc X jest dodatnia"));
        noteCanvas.add(new Paragraph("Jezeli punkt na 2 stronie jest wyzej to wartosc Y jest dodatnia"));
        noteCanvas.add(new Paragraph("If point on 2nd page is to the right, X value is positive"));
        noteCanvas.add(new Paragraph("If point on 2nd page is higher, Y value is positive"));

        drawCircleOnNewPage(pdfDoc);
        pdfCanvas = new PdfCanvas(pdfDoc.getLastPage());
        pageCanvas = new Canvas(pdfCanvas, pdfDoc, pageRectangle);
        pageCanvas.add(new Paragraph("Druga strona / Second page"));
        Image img = new Image(ImageDataFactory.create(getSystemResource("legenda.gif")));
        img.setFixedPosition(150, 500);
        doc.add(img);
        doc.close();
        pdfDoc.close();
        return FILE;
    }

    private static void drawCircleOnNewPage(PdfDocument pdfDoc) {
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.setColor(DeviceCmyk.BLACK, true);
        canvas.circle(100, 100, 2);  // lewy dół
        canvas.circle(495, 100, 2);  // prawy dół
        canvas.circle(100, 742, 2);  // lewa góra
        canvas.circle(495, 742, 2); // prawa góra
        canvas.fill();
        canvas.rectangle(new Rectangle(198, 413, 200, 15))

                .rectangle(new Rectangle(0, 0, 100, 100))
                .rectangle(new Rectangle(495, 0, 100, 100))
                .rectangle(new Rectangle(0, 742, 100, 100))
                .rectangle(new Rectangle(495, 742, 100, 100));
        canvas.moveTo(0, 0).lineTo(595, 841);
        canvas.moveTo(595, 0).lineTo(0, 841);
        canvas.moveTo(198, 413).lineTo(398, 428);
        canvas.moveTo(398, 413).lineTo(198, 428);
        canvas.moveTo(100, 100).lineTo(495, 742);
        canvas.moveTo(495, 100).lineTo(100, 742);
        canvas.stroke();
    }
}
