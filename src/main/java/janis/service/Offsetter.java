package janis.service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

import java.io.File;
import java.io.IOException;

public class Offsetter {

    public static void offset(File input, String outputFilePath, float xOffset, float yOffset) {

        try {
            String name = "test";
            String pathname = "d:/pdf/" + name;
            File file = new File(pathname + ".pdf");
            File file2 = new File(pathname + "2.pdf");

            PdfDocument srcDoc = new PdfDocument(new PdfReader(file));
            Rectangle rect = srcDoc.getFirstPage().getPageSize();

            Rectangle pageSize = new Rectangle(rect.getWidth(), rect.getHeight());

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file2));
            pdfDoc.setDefaultPageSize(new PageSize(pageSize));

            PdfCanvas content = new PdfCanvas(pdfDoc.addNewPage());
            int n = 0;
            for (int i = 1; i <= srcDoc.getNumberOfPages(); i++) {
                PdfFormXObject page = srcDoc.getPage(i).copyAsFormXObject(pdfDoc);

                content.clip();
                content.endPath();
                if (n % 2 == 0)
                    content.addXObject(page, 0, 0);// here margin which is installed in DEST1
                else if (!(n % 2 == 0))
                    content.addXObject(page, 100, -100);
                // y dodatni przesuwa w górę, x dodatni przesuwa w lewo
                content = new PdfCanvas(pdfDoc.addNewPage());
                n++;

            }
            srcDoc.close();
            pdfDoc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
