package src;

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class ui extends Application {
    Label response;
    ToggleGroup tg;
    static variables var = new variables();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Auswahl der Spielmodi");
        VBox rootNode = new VBox(10);
        rootNode.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootNode, 600, 400);
        stage.setScene(scene);

        Label auswahl = new Label("Wähle einen Spielmodus");
        response = new Label("Du hast noch keinen Spielmodus gewählt");
        Button btnConfirm = new Button("Spiel mit diesem Modus starten");
        Button openSettings = new Button("Einstellungen");
        openSettings.setPrefWidth(150);

        RadioButton rbModus1 = new RadioButton("Modus 1");
        RadioButton rbModus2 = new RadioButton("Modus 2");
        RadioButton rbModus3 = new RadioButton("Modus 3");

        tg = new ToggleGroup();
        rbModus1.setToggleGroup(tg);
        rbModus2.setToggleGroup(tg);
        rbModus3.setToggleGroup(tg);
        rbModus1.setSelected(true);

        btnConfirm.setOnAction(event -> {
            try {
                RadioButton rb = (RadioButton) tg.getSelectedToggle();
                switch (rb.getText()) {
                    case "Modus 1":
                        var.gameType = 1;
                        break;
                    case "Modus 2":
                        var.gameType = 2;
                        break;
                    case "Modus 3":
                        var.gameType = 3;
                        break;
                }

                // Import the list when the button is pressed
                // import_list list = new import_list(var);
                // list.import_list();
            } catch (Exception e) {
                e.printStackTrace();
                response.setText("Ein Fehler ist aufgetreten: " + e.getMessage());
            }
        });

        openSettings.setOnAction(_ -> {
            settings.startSettings();
        });

        HBox radioButtons = new HBox(10, rbModus1, rbModus2, rbModus3);
        radioButtons.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(10, openSettings);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        VBox.setMargin(openSettings, new Insets(40, 0, 0, 0));
        rootNode.getChildren().addAll(auswahl, radioButtons, btnConfirm, response, openSettings);
        stage.show();
    }

    public static void noFiveLetterWords() {
        Platform.runLater(() -> {
            Stage insertFilePath = new Stage();
            startFilePathError(insertFilePath);
        });
    }

    public static void startFilePathError(Stage insertFilePath) {
        insertFilePath.setTitle("Fehler bei der Pfadangabe");
        VBox rootNode = new VBox(10);
        rootNode.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootNode, 600, 300);
        insertFilePath.setScene(scene);

        Label error = new Label("Leider konnte keine Wortliste gefunden werden. Bitte überprüfe die Pfadangabe. Aktuell ist " + var.filePath + " eingetragen.");
        TextField filePath = new TextField();
        Button btnConfirmFilePath = new Button("Pfad bestätigen");
        btnConfirmFilePath.setOnAction(_ -> {
            import_list importer = new import_list(var);
            importer.setFilePath(filePath.getText());
            importer.import_list();
            insertFilePath.close();
        });

        rootNode.getChildren().addAll(error, filePath, btnConfirmFilePath);
        insertFilePath.show();
    }
}