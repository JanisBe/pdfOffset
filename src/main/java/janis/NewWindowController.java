package janis;

import janis.service.NewPdf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;

import static janis.errorhandling.AlertWindow.showAlert;
import static janis.utils.FileOpener.openPdf;

public class NewWindowController {

    @FXML
    private TextArea info;

    @FXML
    private Hyperlink pdfPath;

    private String pathToTempPdf;

    @FXML
    public void initialize() {
        info.setWrapText(true);
        info.setEditable(false);
    }

    public void createNewPdf() {
        try {
            pathToTempPdf = NewPdf.createNewPdf();
            pdfPath.setText(pathToTempPdf);
            openPdf(pathToTempPdf);
        } catch (FileNotFoundException e) {
            showAlert(e);
        }
    }

    public void close(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void pdfClick(ActionEvent actionEvent) {
        if (pdfPath != null) {
            openPdf(pathToTempPdf);
        }
    }

}
