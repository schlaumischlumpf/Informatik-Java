package src;

import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class settings {
    static variables var = new variables();

    public static void startSettings() {
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Einstellungen");

        VBox rootNode = new VBox(10);
        rootNode.setPadding(new Insets(15));
        rootNode.setAlignment(Pos.CENTER);

        // Checkboxen mit aktuellem Zustand initialisieren
        CheckBox debugMode = new CheckBox("Debug-Modus");
        debugMode.setSelected(variables.debugMode);

        CheckBox disableDuplicateLetters = new CheckBox("Doppelte Buchstaben deaktivieren");
        disableDuplicateLetters.setSelected(variables.disableDuplicateLetters);

        Button saveButton = new Button("Speichern");
        saveButton.setOnAction(e -> {
            variables.debugMode = debugMode.isSelected();
            variables.disableDuplicateLetters = disableDuplicateLetters.isSelected();
            start.testButton();
            settingsStage.close();
        });

        rootNode.getChildren().addAll(
                debugMode,
                disableDuplicateLetters,
                saveButton
        );

        Scene scene = new Scene(rootNode, 300, 200);
        settingsStage.setScene(scene);
        settingsStage.show();
    }
}