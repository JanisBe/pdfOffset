package janis;

import janis.objects.NumberTextField;
import janis.service.Offsetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {
    @FXML
    private NumberTextField xOffset;

    @FXML
    private NumberTextField yOffset;

    @FXML
    private TextField inputLocation;

    @FXML
    private TextField outputLocation;

    @FXML
    private Button convert;

    @FXML
    private Label status;

    private File inputFile;

    public void browseOutput(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            outputLocation.setText(selectedDirectory.getAbsolutePath() + "_offset.pdf");
        }

    }

    public void convert(ActionEvent actionEvent) {
        if (inputFile != null && !outputLocation.getText().isEmpty()) {
            Float xOffsetText = toFloat(xOffset);
            Float yOffsetText = toFloat(yOffset);
            if (xOffsetText != 9999999999991F && yOffsetText != 9999999999991F) {
                convert.setDisable(true);
                status.setText("Converting...");
                boolean result = false;
                try {
                    Offsetter.offset(inputFile, outputLocation.getText(), xOffsetText, yOffsetText);
                    status.setText("Converted!");
                } catch (IOException | com.itextpdf.io.IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sth went wrong, try again");
                    alert.setContentText(e.getMessage());
                    alert.show();
                    status.setText("Error, not converted");
                }
                convert.setDisable(false);

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
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("testWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Test pdf sheet");
            stage.setScene(new Scene(root, 470, 250));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File browse() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Open File");
        return chooser.showOpenDialog(new Stage());
    }
}
