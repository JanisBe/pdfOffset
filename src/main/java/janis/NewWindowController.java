package janis;

import janis.service.NewPdf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.io.File;

public class NewWindowController {

    @FXML
    private TextArea info;

    @FXML
    public void initialize() {
        info.setWrapText(true);
        info.setEditable(false);
    }

    public void createNewPdf(ActionEvent actionEvent) {
        try {


            String newPdf = NewPdf.createNewPdf();
            if ((new File(newPdf)).exists()) {

                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + newPdf);
                p.waitFor();

            } else {

                System.out.println("File is not exists");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

}
