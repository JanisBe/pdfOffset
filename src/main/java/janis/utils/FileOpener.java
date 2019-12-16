package janis.utils;

import java.io.File;

import static janis.errorhandling.AlertWindow.showAlert;

public class FileOpener {

    public static void openPdf(String pathToTempPdf) {
        try {
            if ((new File(pathToTempPdf)).exists()) {
                Process p = null;
                p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + pathToTempPdf);
                p.waitFor();
            } else {
                System.out.println("File is not exists");
            }
        } catch (Exception e) {
            showAlert(e);
        }
    }
}
