package janis.errorhandling;

import javafx.scene.control.Alert;

public class AlertWindow {
    public static void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Sth went wrong, try again");
        alert.setContentText(e.getMessage());
        alert.show();
    }

    public static void showAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.show();
    }
}
