package janis;

import janis.objects.NumberTextField;
import janis.service.Offsetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    private NumberTextField xOffset;

    @FXML
    private NumberTextField yOffset;

    @FXML
    private TextField inputLocation;

    @FXML
    private TextField outputLocation;

    private File inputFile;
    private File outputFile;

    public void browseOutput(ActionEvent actionEvent) {
        outputFile = browse();
    }

    public void convert(ActionEvent actionEvent) {
        if (inputFile != null && !outputLocation.getText().isEmpty()) {
            Float xOffsetText = toFloat(xOffset);
            Float yOffsetText = toFloat(yOffset);
            if (xOffsetText != 9999999999991F && yOffsetText != 9999999999991F) {
                Offsetter.offset(inputFile, outputLocation.getText(), xOffsetText, yOffsetText);
            }
        }
    }

    private Float toFloat(NumberTextField field) {
        try {
            return Float.parseFloat(field.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No value for " + field.getId());
            alert.setContentText("No value for " + field.getId() + "\n Continue with 0 ?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            final Float[] result = {0F};
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    field.setText("0");
                    result[0] = 0F;
                } else {
                    result[0] = 9999999999991F;
                }
            });
            return result[0];
        }
    }

    public void browseInput(ActionEvent actionEvent) {
        inputFile = browse();
        if (inputFile != null) {
            String absolutePath = inputFile.getAbsolutePath();
            inputLocation.setText(absolutePath);
            outputLocation.setText(absolutePath.substring(0, absolutePath.length() - 4) + "_offset.pdf");
        }
    }

    public void close(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void createTestFile(ActionEvent actionEvent) {
    }

    public void validateNumber(InputMethodEvent inputMethodEvent) {
        System.out.println(inputMethodEvent.getEventType());
        System.out.println(xOffset.getText());
    }

    private File browse() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Open File");
        return chooser.showOpenDialog(new Stage());
    }
}
