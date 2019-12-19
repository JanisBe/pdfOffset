package janis.errorhandling;

import javafx.scene.control.Alert;

public class AlertWindow {
    public static void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        assignAlertContent(alert, "Sth went wrong, try again", e.getMessage());
    }

    public static void showAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        assignAlertContent(alert, title, context);
    }


    public static void showInfo(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        assignAlertContent(alert, title, context);
    }

    private static void assignAlertContent(Alert alert, String s, String message) {
        alert.setTitle(s);
        alert.setContentText(message);
        alert.show();
    }
}
